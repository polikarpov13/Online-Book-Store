package book.store.onlinebookstore.mapper;

import book.store.onlinebookstore.config.MapperConfig;
import book.store.onlinebookstore.dto.cartitem.CartItemDto;
import book.store.onlinebookstore.dto.cartitem.UpdateCartItemRequestDto;
import book.store.onlinebookstore.model.CartItem;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class, componentModel = "spring")
public interface CartItemMapper {
    CartItemDto toDto(CartItem cartItem);

    CartItem toModel(CartItemDto cartItemDto);

    CartItem toModel(UpdateCartItemRequestDto updateCartItemRequestDto);

    default Set<CartItem> map(Set<Long> ids) {
        return ids.stream().map(id -> {
            CartItem cartItem = new CartItem();
            cartItem.setId(id);
            return cartItem;
        }).collect(Collectors.toSet());
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
