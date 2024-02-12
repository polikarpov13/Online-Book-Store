package book.store.onlinebookstore.dto.orderitem;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderItemDto {
    @NotNull
    private Long id;
    @NotNull
    private Long bookId;
    @NotNull
    @Min(0)
    private int quantity;
}
