package book.store.onlinebookstore.service;

import book.store.onlinebookstore.dto.category.CategoryDto;
import book.store.onlinebookstore.dto.category.CreateCategoryRequestDto;
import book.store.onlinebookstore.dto.category.UpdateCategoryRequestDto;
import book.store.onlinebookstore.exception.EntityNotFoundException;
import book.store.onlinebookstore.mapper.CategoryMapper;
import book.store.onlinebookstore.model.Category;
import book.store.onlinebookstore.repository.category.CategoryRepository;
import book.store.onlinebookstore.service.category.CategoryServiceImpl;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    private static final Long ID_VALUE = 1L;
    @InjectMocks
    private CategoryServiceImpl categoryService;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryMapper categoryMapper;
    private Category category;

    @BeforeEach
    public void setup() {
        category = new Category();
        category.setId(ID_VALUE);
        category.setName("Category");
        category.setDescription("Description");
    }

    @Test
    @DisplayName("Get all Category DTOs")
    public void findAll_ValidPageable_ReturnsValidCategories() {
        // Given
        CategoryDto dto = formCategoryDto();

        Pageable pageable = PageRequest.of(0, 10);
        List<CategoryDto> expected = List.of(dto);
        List<Category> categories = List.of(category);
        Page<Category> categoryPage = new PageImpl<>(categories, pageable, categories.size());

        Mockito.when(categoryRepository.findAll(pageable)).thenReturn(categoryPage);
        Mockito.when(categoryMapper.toDto(category)).thenReturn(dto);

        // When
        List<CategoryDto> actual = categoryService.findAll(pageable);

        // Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Get Category DTO by VALID ID")
    public void getCategoryById_ValidID_ReturnsValidCategoryDto() {
        // Given
        Long categoryId = ID_VALUE;

        CategoryDto expected = formCategoryDto();

        Mockito.when(categoryRepository.findById(categoryId))
                .thenReturn(Optional.ofNullable(category));
        Mockito.when(categoryMapper.toDto(category)).thenReturn(expected);

        // When
        CategoryDto actual = categoryService.getById(categoryId);

        // Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Pass invalid Category ID and check if VALID exception is being thrown")
    public void getCategoryById_InvalidID_ThrowsEntityNotFoundException() {
        // Given
        Long categoryId = -100L;

        // When
        Exception exception = Assertions.assertThrows(EntityNotFoundException.class,
                () -> categoryService.getById(categoryId));

        String expected = "Could not find category by ID: " + categoryId;
        String actual = exception.getMessage();

        // Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Saving VALID Category DTO and receiving a VALID response")
    public void saveCategory_ValidCreateCategoryRequestDto_ReturnValidCategoryDto() {
        // Given
        CreateCategoryRequestDto dto = new CreateCategoryRequestDto();
        dto.setName("Category");
        dto.setDescription("Description");

        CategoryDto expected = formCategoryDto();

        Mockito.when(categoryRepository.save(category)).thenReturn(category);
        Mockito.when(categoryMapper.toDto(category)).thenReturn(expected);
        Mockito.when(categoryMapper.toModel(dto)).thenReturn(category);

        // When
        CategoryDto actual = categoryService.save(dto);

        // Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Update Category by VALID ID and receiving a VALID response")
    public void updateCategoryById_ValidId_ReturnsUpdatedCategoryDto() {
        // Given
        UpdateCategoryRequestDto dto = new UpdateCategoryRequestDto();
        dto.setName("Category");
        dto.setDescription("Description");

        Mockito.when(categoryMapper.toModel(dto)).thenReturn(category);

        // When
        categoryService.updateById(ID_VALUE, dto);
        Mockito.verify(categoryRepository, Mockito.times(1)).save(category);
    }

    @Test
    @DisplayName("Delete Category by VALID ID and receiving a VALID response")
    public void deleteCategoryById_ValidId_ReturnsNothingIfSuccessful() {
        // Given
        Long categoryId = ID_VALUE;

        // When
        categoryService.deleteById(categoryId);
        Mockito.verify(categoryRepository, Mockito.times(1)).deleteById(categoryId);
    }

    private CategoryDto formCategoryDto() {
        CategoryDto dto = new CategoryDto();
        dto.setId(ID_VALUE);
        dto.setName("Category");
        dto.setDescription("Description");
        return dto;
    }
}
