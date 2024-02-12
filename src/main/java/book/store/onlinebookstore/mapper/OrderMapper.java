package book.store.onlinebookstore.mapper;

import book.store.onlinebookstore.config.MapperConfig;
import book.store.onlinebookstore.dto.order.OrderDto;
import book.store.onlinebookstore.model.Order;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class, componentModel = "spring", uses = OrderItemMapper.class)
public interface OrderMapper {
    @Mapping(target = "orderItemDtos", source = "orderItems")
    OrderDto toDto(Order order);

    @AfterMapping
    default void setUserId(@MappingTarget OrderDto dto, Order order) {
        if (order.getUser() != null) {
            dto.setUserId(order.getUser().getId());
        }
    }
}
