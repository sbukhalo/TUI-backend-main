package tui.meta.challenge.quotes.repository;

import org.springframework.data.mongodb.core.annotation.Collation;
import org.springframework.data.mongodb.repository.MongoRepository;
import tui.meta.challenge.quotes.model.Quote;

import java.util.List;

public interface QuoteRepository extends MongoRepository<Quote, String> {

    @Collation("{'locale': 'en_US', 'strength': 2}") //makes it case-insensitive
    List<Quote> getByQuoteAuthor(String author);
}
