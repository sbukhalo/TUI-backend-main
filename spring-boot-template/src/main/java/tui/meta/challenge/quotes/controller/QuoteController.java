package tui.meta.challenge.quotes.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import tui.meta.challenge.quotes.model.Quote;
import tui.meta.challenge.quotes.service.QuoteService;

import java.util.List;

@RestController
@RequestMapping(value = "quotes", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class QuoteController {
    private final QuoteService quoteService;

    @GetMapping("/{id}")
    public Quote get(@PathVariable String id) {
      return quoteService.getById(id);
    }

    @GetMapping
    public List<Quote> filter(@RequestParam(required = false) String author) {
        return author == null ? quoteService.getAll(0) : quoteService.getByAuthor(author);
    }
}