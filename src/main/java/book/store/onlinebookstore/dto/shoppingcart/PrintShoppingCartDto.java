package book.store.onlinebookstore.dto.shoppingcart;

import book.store.onlinebookstore.dto.cartitem.CartItemDto;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import lombok.Data;

@Data
public class PrintShoppingCartDto {
    private Long id;
    @NotNull
    private Long userId;
    @NotNull
    private Set<CartItemDto> cartItemDtos;
}
