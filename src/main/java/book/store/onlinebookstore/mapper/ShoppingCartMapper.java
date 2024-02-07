package book.store.onlinebookstore.mapper;

import book.store.onlinebookstore.config.MapperConfig;
import book.store.onlinebookstore.dto.cartitem.CartItemDto;
import book.store.onlinebookstore.dto.shoppingcart.PrintShoppingCartDto;
import book.store.onlinebookstore.dto.shoppingcart.ShoppingCartDto;
import book.store.onlinebookstore.model.CartItem;
import book.store.onlinebookstore.model.ShoppingCart;
import book.store.onlinebookstore.model.User;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class, componentModel = "spring")
public interface ShoppingCartMapper {
    ShoppingCartDto toDto(ShoppingCart shoppingCart);

    @Mapping(target = "userId", source = "user")
    @Mapping(target = "cartItemDtos", source = "cartItems")
    PrintShoppingCartDto toPrintDto(ShoppingCart shoppingCart);

    @Mapping(target = "cartItems", source = "cartItemIds")
    @Mapping(target = "user", source = "userId")
    ShoppingCart toModel(ShoppingCartDto shoppingCartDto);

    @Mapping(target = "user", source = "userId")
    @Mapping(target = "cartItems", source = "cartItemDtos")
    ShoppingCart toModel(PrintShoppingCartDto printShoppingCartDto);

    @AfterMapping
    default void setCartAndUserIds(
            @MappingTarget ShoppingCartDto shoppingCartDto,
            ShoppingCart shoppingCart) {
        shoppingCartDto.setCartItemIds(shoppingCart.getCartItems().stream()
                .map(CartItem::getId)
                .collect(Collectors.toSet()));
        shoppingCartDto.setUserId(shoppingCart.getUser().getId());
    }

    default Set<CartItem> map(Set<Long> ids) {
        return ids.stream().map(id -> {
            CartItem cartItem = new CartItem();
            cartItem.setId(id);
            return cartItem;
        }).collect(Collectors.toSet());
    }

    default User map(Long id) {
        User user = new User();
        user.setId(id);
        return user;
    }

    default Long map(User user) {
        return user.getId();
    }

    default Set<CartItemDto> mapDto(Set<CartItem> items) {
        return items.stream().map(i -> {
            CartItemDto dto = new CartItemDto();
            dto.setId(i.getId());
            dto.setBookId(i.getBook().getId());
            dto.setBookTitle(i.getBook().getTitle());
            dto.setQuantity(i.getQuantity());
            return dto;
        }).collect(Collectors.toSet());
    }

    default Set<CartItem> mapModel(Set<CartItemDto> items) {
        return items.stream().map(i -> {
            CartItem item = new CartItem();
            item.setId(i.getId());
            return item;
        }).collect(Collectors.toSet());
    }
}
