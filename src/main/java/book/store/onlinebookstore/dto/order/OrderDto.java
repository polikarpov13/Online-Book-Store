package book.store.onlinebookstore.dto.order;

import book.store.onlinebookstore.dto.orderitem.OrderItemDto;
import book.store.onlinebookstore.enums.Status;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;

@Data
public class OrderDto {
    @NotNull
    private Long id;
    @NotNull
    private Long userId;
    private Set<OrderItemDto> orderItemDtos;
    @NotNull
    private LocalDateTime orderDate;
    @NotNull
    @Min(0)
    private BigDecimal total;
    @NotNull
    private Status status;
}
