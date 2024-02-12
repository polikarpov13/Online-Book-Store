package book.store.onlinebookstore.mapper;

import book.store.onlinebookstore.config.MapperConfig;
import book.store.onlinebookstore.dto.orderitem.OrderItemDto;
import book.store.onlinebookstore.model.OrderItem;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class, componentModel = "spring")
public interface OrderItemMapper {

    OrderItemDto toDto(OrderItem orderItem);

    @AfterMapping
    default void setBookId(@MappingTarget OrderItemDto dto, OrderItem item) {
        if (item.getBook() != null) {
            dto.setBookId(item.getBook().getId());
        }
    }
}
