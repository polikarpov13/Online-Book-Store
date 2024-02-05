package book.store.onlinebookstore.dto.category;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCategoryRequestDto {
    @NotNull
    private String name;

    @NotNull
    private String description;
}
