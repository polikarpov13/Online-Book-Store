package book.store.onlinebookstore.service.shoppingcart;

import book.store.onlinebookstore.dto.cartitem.CartItemDto;
import book.store.onlinebookstore.dto.cartitem.UpdateCartItemRequestDto;
import book.store.onlinebookstore.dto.shoppingcart.PrintShoppingCartDto;
import book.store.onlinebookstore.dto.shoppingcart.ShoppingCartDto;

public interface ShoppingCartService {
    ShoppingCartDto addItemToCart(CartItemDto cartItemDto);

    PrintShoppingCartDto getShoppingCart();

    PrintShoppingCartDto updateItemQuantityById(Long itemId,
                                           UpdateCartItemRequestDto updateCartItemRequestDto);

    void deleteItemById(Long itemId);
}
