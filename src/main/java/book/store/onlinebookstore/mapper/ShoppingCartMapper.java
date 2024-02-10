package book.store.onlinebookstore.mapper;

import book.store.onlinebookstore.config.MapperConfig;
import book.store.onlinebookstore.dto.shoppingcart.PrintShoppingCartDto;
import book.store.onlinebookstore.dto.shoppingcart.ShoppingCartDto;
import book.store.onlinebookstore.model.CartItem;
import book.store.onlinebookstore.model.ShoppingCart;
import java.util.stream.Collectors;
import org.mapstruct.AfterMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class,
        uses = {CartItemMapper.class, UserMapper.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        componentModel = "spring")
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
}
