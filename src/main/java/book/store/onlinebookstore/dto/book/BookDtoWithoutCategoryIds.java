package book.store.onlinebookstore.dto.book;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import lombok.Data;
import org.hibernate.validator.constraints.ISBN;

@Data
public class BookDtoWithoutCategoryIds {
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String author;

    @NotBlank
    @ISBN(type = ISBN.Type.ANY)
    private String isbn;

    @NotBlank
    @Min(0)
    private BigDecimal price;

    @NotBlank
    private String description;

    @NotBlank
    private String coverImage;
}
