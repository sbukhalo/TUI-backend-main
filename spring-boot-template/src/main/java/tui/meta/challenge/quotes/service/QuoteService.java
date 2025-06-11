package tui.meta.challenge.quotes.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tui.meta.challenge.quotes.model.Quote;
import tui.meta.challenge.quotes.repository.QuoteRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuoteService {

    private final QuoteRepository quoteRepository;

    public Quote getById(String id) {
        log.debug("Retrieving quote (id: {})", id);
        return quoteRepository.findById(id)
                .orElseThrow(() -> {
                    log.debug("Quote not found (id: {})", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Quote not found");
                });
    }

    public List<Quote> getAll(int limit) {
        log.debug("Retrieving quotes with limit: {}", limit);
        if (limit == 0) {
            return quoteRepository.findAll();
        }
        return quoteRepository.findAll(PageRequest.of(0, limit)).getContent();
    }

    public List<Quote> getByAuthor(String author) {
        log.debug("Searching quotes by author: {}", author);
        return quoteRepository.getByQuoteAuthor(author);
    }
}
