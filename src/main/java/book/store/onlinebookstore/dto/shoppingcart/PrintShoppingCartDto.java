package book.store.onlinebookstore.dto.shoppingcart;

import book.store.onlinebookstore.model.CartItem;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import lombok.Data;

@Data
public class PrintShoppingCartDto {
    private Long id;

    @NotNull
    private Long userId;

    @NotNull
    private Set<CartItem> cartItems;
}
