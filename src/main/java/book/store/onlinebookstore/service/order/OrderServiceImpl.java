package book.store.onlinebookstore.service.order;

import book.store.onlinebookstore.dto.order.OrderDto;
import book.store.onlinebookstore.dto.order.PlaceOrderRequestDto;
import book.store.onlinebookstore.dto.order.UpdateOrderStatusRequestDto;
import book.store.onlinebookstore.dto.orderitem.OrderItemDto;
import book.store.onlinebookstore.mapper.OrderItemMapper;
import book.store.onlinebookstore.mapper.OrderMapper;
import book.store.onlinebookstore.model.CartItem;
import book.store.onlinebookstore.model.Order;
import book.store.onlinebookstore.model.OrderItem;
import book.store.onlinebookstore.model.ShoppingCart;
import book.store.onlinebookstore.model.User;
import book.store.onlinebookstore.repository.order.OrderRepository;
import book.store.onlinebookstore.repository.orderitem.OrderItemRepository;
import book.store.onlinebookstore.repository.shoppingcart.ShoppingCartRepository;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final ShoppingCartRepository shoppingCartRepository;

    @Override
    @Transactional
    public OrderDto placeOrder(PlaceOrderRequestDto placeOrderRequestDto) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Order order = formOrder(user, placeOrderRequestDto.shippingAddress());
        ShoppingCart shoppingCart = shoppingCartRepository.getShoppingCartByUserId(user.getId());

        setOrderTotalAmount(order, shoppingCart);

        orderRepository.save(order);

        setOrderItemsToOrder(order, shoppingCart);

        return orderMapper.toDto(order);
    }

    @Override
    public List<OrderDto> getOrdersHistory() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return orderRepository.getOrdersByUserId(user.getId()).stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    public OrderDto updateOrderStatus(Long orderId,
                                      UpdateOrderStatusRequestDto updateOrderStatusRequestDto) {
        Order order = orderRepository.getReferenceById(orderId);
        order.setStatus(Order.Status.valueOf(updateOrderStatusRequestDto.status()));
        return orderMapper.toDto(order);
    }

    @Override
    public List<OrderItemDto> getOrderItemsByOrderId(Long orderId) {
        return orderItemRepository.getOrderItemsByOrderId(orderId).stream()
                .map(orderItemMapper::toDto)
                .toList();
    }

    @Override
    public OrderItemDto getOrderItemByOrderIdAndItemId(Long orderId, Long itemId) {
        return orderItemMapper.toDto(
                orderItemRepository.getOrderItemByOrderIdAndItemId(orderId, itemId));
    }

    private Order formOrder(User user, String shippingAddress) {
        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setUser(user);
        order.setStatus(Order.Status.PENDING);
        order.setShippingAddress(shippingAddress);
        return order;
    }

    private void setOrderTotalAmount(Order order, ShoppingCart shoppingCart) {
        BigDecimal total = BigDecimal.valueOf(0);
        for (CartItem item : shoppingCart.getCartItems()) {
            total = total.add(item.getBook().getPrice());
        }
        order.setTotal(total);
    }

    @Transactional
    private void setOrderItemsToOrder(Order order, ShoppingCart shoppingCart) {
        Set<OrderItem> orderItems = new HashSet<>();
        for (CartItem item: shoppingCart.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setBook(item.getBook());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(item.getBook().getPrice());

            orderItemRepository.save(orderItem);
            orderItems.add(orderItem);
        }
        order.setOrderItems(orderItems);
    }
}
