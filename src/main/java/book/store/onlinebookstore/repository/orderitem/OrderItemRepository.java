package book.store.onlinebookstore.repository.orderitem;

import book.store.onlinebookstore.model.OrderItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Query("SELECT oi FROM OrderItem oi "
            + "LEFT JOIN FETCH oi.book "
            + "WHERE oi.order.id = (:id)")
    List<OrderItem> getOrderItemsByOrderId(Long id);

    @Query("SELECT oi FROM OrderItem oi "
            + "LEFT JOIN FETCH oi.book "
            + "WHERE oi.order.id = (:orderId) "
            + "AND oi.id = (:itemId)")
    OrderItem getOrderItemByOrderIdAndItemId(Long orderId, Long itemId);
}
