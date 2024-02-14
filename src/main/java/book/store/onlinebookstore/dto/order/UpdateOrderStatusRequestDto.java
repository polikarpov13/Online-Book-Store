package book.store.onlinebookstore.dto.order;

import jakarta.validation.constraints.NotNull;

public record UpdateOrderStatusRequestDto(@NotNull String status) {
}
