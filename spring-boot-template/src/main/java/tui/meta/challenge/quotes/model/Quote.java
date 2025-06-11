package tui.meta.challenge.quotes.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("quotes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Quote {
    @Id
    @Field("_id")
    private String id;

    private String quoteText;

    private String quoteAuthor;

    @Version
    @Field("__v")
    private Integer version;
}
