package book.store.onlinebookstore.dto.cartitem;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartItemDto {
    private Long id;

    private Long bookId;

    @NotNull
    private String bookTitle;

    @NotNull
    @Min(1)
    private int quantity;
}
