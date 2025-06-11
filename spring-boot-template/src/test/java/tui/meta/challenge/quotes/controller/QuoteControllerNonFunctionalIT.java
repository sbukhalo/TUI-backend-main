package tui.meta.challenge.quotes.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import tui.meta.challenge.quotes.model.Quote;
import tui.meta.challenge.quotes.service.QuoteService;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class QuoteControllerNonFunctionalIT {

    private static final int REQUEST_CNT = 50;
    private static final int MAX_DURATION_MS = 200;
    private static final int TOTAL_DURATION_MS = 1000;
    private static final int ALL_QUOTES_DURATION_MS = 30000;

    @LocalServerPort
    private int port;

    HttpClient httpClient;

    @Autowired
    QuoteService quoteService;

    @BeforeEach
    void init() {
        httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
    }

    Callable<Void> queryTask(String uri) {
        return () -> {
            long start = System.currentTimeMillis();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uri))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            long duration = System.currentTimeMillis() - start;

            System.out.println("query duration, ms: " + duration);
            assertThat(response.statusCode()).isEqualTo(200); //OK
            assertThat(duration).isLessThanOrEqualTo(MAX_DURATION_MS);

            return null;
        };
    }

    @SneakyThrows
    void runPerformanceTestFor(String uri, List<String> params) {
        var pool = Executors.newScheduledThreadPool(REQUEST_CNT);

        //calculate delay to distribute N calls within required total time (1 sec)
        long delayBetweenMs = (long) (TOTAL_DURATION_MS * 0.9 / params.size());

        var cnt = new AtomicInteger(0);

        long totalStart = System.currentTimeMillis();
        // create and scheduling task distributed within 1 sec
        var scheduledTasks = params.stream()
                .map(p -> {
                    String taskUri = uri + URLEncoder.encode(p, StandardCharsets.UTF_8);
                    return pool.schedule(queryTask(taskUri), cnt.getAndIncrement() * delayBetweenMs, TimeUnit.MILLISECONDS);
                }).toList();

        for (var future : scheduledTasks) {
            //checking each task execution
            future.get();
        }

        long totalDuration = System.currentTimeMillis() - totalStart;
        System.out.println("total duration, ms: " + totalDuration);
        assertThat(totalDuration).isLessThanOrEqualTo(TOTAL_DURATION_MS);

        pool.shutdown();
        if (!pool.awaitTermination(5, TimeUnit.SECONDS)) {
            pool.shutdownNow();
        }
    }

    //The app must be able to support 50 requests per second in up to 200 milliseconds for the search functionality by ID
    @Test
    void getByIdPerformance() {
        var ids = quoteService.getAll(REQUEST_CNT).stream()
                .map(Quote::getId)
                .toList();

        runPerformanceTestFor("http://localhost:" + port + "/quotes/", ids);
    }

    //The app must be able to support 50 requests per second in up to 200 milliseconds for the search functionality by author
    @Test
    void getByAuthorPerformance() {
        var authors = quoteService.getAll(0)
                .stream()
                .map(Quote::getQuoteAuthor)
                .distinct()
                .limit(REQUEST_CNT)
                .toList();

        runPerformanceTestFor("http://localhost:" + port + "/quotes?author=", authors);
    }

    //The app must be able to answer in less than 30 seconds for the functionality to search all items in this collection.
    @Test
    @SneakyThrows
    void getAllPerformance() {
        long start = System.currentTimeMillis();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/quotes"))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        long duration = System.currentTimeMillis() - start;

        System.out.println("all quotes duration, ms: " + duration);
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(duration).isLessThanOrEqualTo(ALL_QUOTES_DURATION_MS);
    }
}