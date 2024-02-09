package book.store.onlinebookstore.dto.shoppingcart;

import jakarta.validation.constraints.NotNull;
import java.util.Set;
import lombok.Data;

@Data
public class ShoppingCartDto {
    private Long id;
    @NotNull
    private Long userId;
    @NotNull
    private Set<Long> cartItemIds;
}
