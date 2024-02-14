package book.store.onlinebookstore.service.order;

import book.store.onlinebookstore.dto.order.OrderDto;
import book.store.onlinebookstore.dto.order.PlaceOrderRequestDto;
import book.store.onlinebookstore.dto.order.UpdateOrderStatusRequestDto;
import book.store.onlinebookstore.dto.orderitem.OrderItemDto;
import java.util.List;

public interface OrderService {
    OrderDto placeOrder(PlaceOrderRequestDto placeOrderRequestDto);

    List<OrderDto> getOrdersHistory();

    OrderDto updateOrderStatus(Long orderId,
                               UpdateOrderStatusRequestDto updateOrderStatusRequestDto);

    List<OrderItemDto> getOrderItemsByOrderId(Long orderId);

    OrderItemDto getOrderItemByOrderIdAndItemId(Long orderId, Long itemId);
}
