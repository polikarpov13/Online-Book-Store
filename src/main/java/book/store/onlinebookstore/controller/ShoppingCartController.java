package book.store.onlinebookstore.controller;

import book.store.onlinebookstore.dto.cartitem.CartItemDto;
import book.store.onlinebookstore.dto.cartitem.UpdateCartItemRequestDto;
import book.store.onlinebookstore.dto.shoppingcart.PrintShoppingCartDto;
import book.store.onlinebookstore.dto.shoppingcart.ShoppingCartDto;
import book.store.onlinebookstore.service.shoppingcart.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Shopping cart management",
        description = "Endpoint for managing shopping cart entities")
@RequiredArgsConstructor
@RestController
@RequestMapping("/cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @GetMapping
    @Operation(summary = "Get user's shopping cart from the DB", description = "Get shopping cart")
    public PrintShoppingCartDto getUserShoppingCart() {
        return shoppingCartService.getShoppingCart();
    }

    @PostMapping
    @Operation(summary = "Add item to user's cart", description = "Add item to the shopping cart")
    public ShoppingCartDto addItemToTheCart(@RequestBody @Valid CartItemDto cartItemDto) {
        return shoppingCartService.addItemToCart(cartItemDto);
    }

    @PutMapping("/cart-items/{itemId}")
    @Operation(summary = "Update item's quantity in user's cart by ID",
            description = "Update item's quantity")
    public PrintShoppingCartDto updateItemById(
            @PathVariable Long itemId,
            @RequestBody @Valid UpdateCartItemRequestDto updateCartItemRequestDto) {
        return shoppingCartService.updateItemQuantityById(itemId, updateCartItemRequestDto);
    }

    @DeleteMapping("/cart-items/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete item from the user's cart by ID",
            description = "Delete item from the cart")
    public void deleteItemById(@PathVariable Long itemId) {
        shoppingCartService.deleteItemById(itemId);
    }
}
