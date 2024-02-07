package book.store.onlinebookstore.service.shoppingcart;

import book.store.onlinebookstore.dto.cartitem.CartItemDto;
import book.store.onlinebookstore.dto.cartitem.UpdateCartItemRequestDto;
import book.store.onlinebookstore.dto.shoppingcart.PrintShoppingCartDto;
import book.store.onlinebookstore.dto.shoppingcart.ShoppingCartDto;
import book.store.onlinebookstore.exception.EntityNotFoundException;
import book.store.onlinebookstore.mapper.CartItemMapper;
import book.store.onlinebookstore.mapper.ShoppingCartMapper;
import book.store.onlinebookstore.model.Book;
import book.store.onlinebookstore.model.CartItem;
import book.store.onlinebookstore.model.ShoppingCart;
import book.store.onlinebookstore.model.User;
import book.store.onlinebookstore.repository.book.BookRepository;
import book.store.onlinebookstore.repository.cartitem.CartItemRepository;
import book.store.onlinebookstore.repository.shoppingcart.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;
    private final BookRepository bookRepository;

    @Override
    public ShoppingCartDto addItemToCart(CartItemDto cartItemDto) {
        CartItem cartItem = cartItemMapper.toModel(cartItemDto);
        ShoppingCart shoppingCart = shoppingCartMapper.toModel(getShoppingCart());
        cartItem.setShoppingCart(shoppingCart);
        Book book = bookRepository.findById(cartItemDto.getBookId()).orElseThrow(()
                -> new EntityNotFoundException("Could not add book with ID: "
                + cartItemDto.getBookId()));
        cartItem.setBook(book);
        cartItemRepository.save(cartItem);
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public PrintShoppingCartDto getShoppingCart() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return shoppingCartMapper.toPrintDto(
                shoppingCartRepository.getShoppingCartByUserId(user.getId()));
    }

    @Override
    public PrintShoppingCartDto updateItemQuantityById(
            Long itemId, UpdateCartItemRequestDto updateCartItemRequestDto) {
        CartItem cartItem = cartItemRepository.getReferenceById(itemId);
        cartItem.setQuantity(updateCartItemRequestDto.getQuantity());
        cartItemRepository.save(cartItem);
        return getShoppingCart();
    }

    @Override
    public void deleteItemById(Long itemId) {
        cartItemRepository.deleteById(itemId);
    }
}
