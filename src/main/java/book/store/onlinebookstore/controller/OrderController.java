package book.store.onlinebookstore.controller;

import book.store.onlinebookstore.dto.order.OrderDto;
import book.store.onlinebookstore.dto.order.PlaceOrderRequestDto;
import book.store.onlinebookstore.dto.order.UpdateOrderStatusRequestDto;
import book.store.onlinebookstore.dto.orderitem.OrderItemDto;
import book.store.onlinebookstore.service.order.OrderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public OrderDto placeOrder(@RequestBody PlaceOrderRequestDto dto) {
        return orderService.placeOrder(dto);
    }

    @GetMapping
    public List<OrderDto> getUserOrders() {
        return orderService.getOrdersHistory();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping("/{orderId}")
    public OrderDto updateOrderStatus(@PathVariable Long orderId,
                                      @RequestBody UpdateOrderStatusRequestDto dto) {
        return orderService.updateOrderStatus(orderId, dto);
    }

    @GetMapping("/{orderId}/items")
    public List<OrderItemDto> getOrderItemsByOrderId(@PathVariable Long orderId) {
        return orderService.getOrderItemsByOrderId(orderId);
    }

    @GetMapping("/{orderId}/items/{itemId}")
    public OrderItemDto getOrderItemByOrderIdAndItemId(@PathVariable Long orderId,
                                                       @PathVariable Long itemId) {
        return orderService.getOrderItemByOrderIdAndItemId(orderId, itemId);
    }
}
