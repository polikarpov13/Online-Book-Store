package book.store.onlinebookstore.mapper;

import book.store.onlinebookstore.config.MapperConfig;
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
    PrintShoppingCartDto toPrintDto(ShoppingCart shoppingCart);

    @Mapping(target = "cartItems", source = "cartItemIds")
    @Mapping(target = "user", source = "userId")
    ShoppingCart toModel(ShoppingCartDto shoppingCartDto);

    //@Mapping(target = "cartItems", source = "cartItemIds")
    //@Mapping(target = "user", source = "userId")
    @Mapping(target = "user", source = "userId")
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
}
