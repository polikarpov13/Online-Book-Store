package book.store.onlinebookstore.mapper;

import book.store.onlinebookstore.config.MapperConfig;
import book.store.onlinebookstore.dto.cartitem.CartItemDto;
import book.store.onlinebookstore.dto.cartitem.UpdateCartItemRequestDto;
import book.store.onlinebookstore.model.CartItem;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class, componentModel = "spring")
public interface CartItemMapper {
    CartItemDto toDto(CartItem cartItem);

    CartItem toModel(CartItemDto cartItemDto);

    CartItem toModel(UpdateCartItemRequestDto updateCartItemRequestDto);
}
