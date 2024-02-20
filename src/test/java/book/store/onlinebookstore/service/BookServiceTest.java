package book.store.onlinebookstore.service;

import book.store.onlinebookstore.dto.book.BookDto;
import book.store.onlinebookstore.dto.book.BookDtoWithoutCategoryIds;
import book.store.onlinebookstore.dto.book.CreateBookRequestDto;
import book.store.onlinebookstore.dto.book.UpdateBookRequestDto;
import book.store.onlinebookstore.exception.EntityNotFoundException;
import book.store.onlinebookstore.mapper.BookMapper;
import book.store.onlinebookstore.model.Book;
import book.store.onlinebookstore.repository.book.BookRepository;
import book.store.onlinebookstore.repository.category.CategoryRepository;
import book.store.onlinebookstore.service.book.BookServiceImpl;
import java.math.BigDecimal;
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
public class BookServiceTest {
    private static final Long ID_VALUE = 1L;
    private static final BigDecimal PRICE_VALUE = BigDecimal.valueOf(1.00);
    @InjectMocks
    private BookServiceImpl bookService;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private BookMapper bookMapper;
    private Book book;

    @BeforeEach
    public void setup() {
        book = new Book();
        book.setId(ID_VALUE);
        book.setTitle("Title");
        book.setAuthor("Author");
        book.setIsbn("ISBN");
        book.setPrice(PRICE_VALUE);
    }

    @Test
    @DisplayName("Saving VALID Book DTO and receiving a VALID response")
    public void saveBook_ValidCreateBookRequestDto_ReturnsValidBookDto() {
        // Given
        CreateBookRequestDto dto = new CreateBookRequestDto();
        dto.setTitle("Title");
        dto.setAuthor("Author");
        dto.setIsbn("Isbn");
        dto.setPrice(PRICE_VALUE);

        BookDto expectedSavedDto = formBookDto();

        Mockito.when(bookRepository.save(book)).thenReturn(book);
        Mockito.when(bookMapper.toDto(book)).thenReturn(expectedSavedDto);
        Mockito.when(bookMapper.toModel(dto)).thenReturn(book);

        // When
        BookDto actualSavedBookDto = bookService.save(dto);

        // Then
        Assertions.assertEquals(expectedSavedDto, actualSavedBookDto);
    }

    @Test
    @DisplayName("Get all Book DTOs")
    public void findAll_ValidPageable_ReturnsAllValidBooks() {
        // Given
        BookDto dto = formBookDto();

        Pageable pageable = PageRequest.of(0, 10);
        List<BookDto> expected = List.of(dto);
        List<Book> books = List.of(book);
        Page<Book> bookPage = new PageImpl<>(books, pageable, books.size());

        Mockito.when(bookRepository.findAll(pageable)).thenReturn(bookPage);
        Mockito.when(bookMapper.toDto(book)).thenReturn(dto);

        // When
        List<BookDto> actual = bookService.findAll(pageable);

        // Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Get all Book DTOs with certain category ID")
    public void findAllByCategoryId_ValidID_ReturnsValidBooks() {
        // Given
        BookDtoWithoutCategoryIds dto = new BookDtoWithoutCategoryIds();
        dto.setTitle("Title");
        dto.setAuthor("Author");
        dto.setIsbn("ISBN");
        dto.setPrice(PRICE_VALUE);
        dto.setDescription("");
        dto.setCoverImage("");

        Long categoryId = ID_VALUE;

        Mockito.when(bookRepository.findAllByCategoryId(categoryId)).thenReturn(List.of(book));
        Mockito.when(bookMapper.toDtoWithoutCategories(book)).thenReturn(dto);
        Mockito.when(categoryRepository.existsById(categoryId)).thenReturn(true);

        // When
        List<BookDtoWithoutCategoryIds> actual = bookService.findAllByCategoryId(categoryId);

        // Then
        Assertions.assertEquals(List.of(dto), actual);
    }

    @Test
    @DisplayName("Pass invalid Category ID and checking of VALID exception is being thrown")
    public void findAllByCategoryId_InvalidID_ThrowsEntityNotFoundException() {
        // Given
        Long categoryId = -100L;

        // When
        Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> bookService.findAllByCategoryId(categoryId)
        );

        String expected = "Category with id: " + categoryId + " does not exist!";
        String actual = exception.getMessage();

        // Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Get Book DTO by VALID ID")
    public void findBookById_ValidID_ReturnsValidBookDto() {
        // Given
        Long bookId = ID_VALUE;

        BookDto expected = formBookDto();

        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.ofNullable(book));
        Mockito.when(bookMapper.toDto(book)).thenReturn(expected);

        // When
        BookDto actual = bookService.findById(bookId);

        // Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Pass invalid Book ID and check if VALID exception is being thrown")
    public void findBookById_InvalidID_ThrowsEntityNotFoundException() {
        // Given
        Long bookId = -100L;

        // When
        Exception exception = Assertions.assertThrows(EntityNotFoundException.class,
                () -> bookService.findById(bookId));

        String expected = "Could not get a book with id: " + bookId;
        String actual = exception.getMessage();

        // Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Update Book by VALID ID and receiving a VALID response")
    public void updateBookById_ValidId_ReturnsUpdatedBookDto() {
        // Given
        UpdateBookRequestDto dto = new UpdateBookRequestDto();
        dto.setTitle("Title");
        dto.setAuthor("Author");
        dto.setIsbn("ISBN");
        dto.setPrice(PRICE_VALUE);
        dto.setDescription("");
        dto.setCoverImage("");

        Mockito.when(bookMapper.toModel(dto)).thenReturn(book);

        Long bookId = ID_VALUE;

        // When
        bookService.updateById(bookId, dto);
        Mockito.verify(bookRepository, Mockito.times(1)).save(book);
    }

    @Test
    @DisplayName("Delete Book by VALID ID and receiving a VALID response")
    public void deleteBookById_ValidID_ReturnsNothingIfSuccessful() {
        // Given
        Long bookId = ID_VALUE;

        // When
        bookService.deleteById(bookId);
        Mockito.verify(bookRepository, Mockito.times(1)).deleteById(bookId);
    }

    private BookDto formBookDto() {
        BookDto dto = new BookDto();
        dto.setId(ID_VALUE);
        dto.setTitle("Title");
        dto.setAuthor("Author");
        dto.setIsbn("ISBN");
        dto.setPrice(PRICE_VALUE);
        return dto;
    }
}
