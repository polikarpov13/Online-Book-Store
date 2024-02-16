package book.store.onlinebookstore.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import book.store.onlinebookstore.dto.book.BookDto;
import book.store.onlinebookstore.dto.book.CreateBookRequestDto;
import book.store.onlinebookstore.dto.book.UpdateBookRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.List;
import javax.sql.DataSource;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerTest {
    protected static MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void setup(@Autowired WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser(username = "user", roles = {"USER"})
    @Test
    @DisplayName("Get all Book entities")
    @Sql(scripts = "classpath:database/book/insert-controller-book.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/book/remove-controller-book.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllBooks_Successful() throws Exception {
        List<BookDto> expected = List.of(formBookDto());

        MvcResult result = mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andReturn();

        BookDto[] actual = objectMapper
                .readValue(result.getResponse().getContentAsString(), BookDto[].class);

        Assertions.assertNotNull(actual);

        EqualsBuilder.reflectionEquals(
                expected,
                actual,
                "id", "author", "title");
    }

    @WithMockUser(username = "user", roles = {"USER"})
    @Test
    @DisplayName("Get Book entity by VALID ID")
    @Sql(scripts = "classpath:database/book/insert-controller-book.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/book/remove-controller-book.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findBookById_ValidId_Successful() throws Exception {
        MvcResult result = mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andReturn();

        BookDto actual = objectMapper
                .readValue(result.getResponse().getContentAsString(), BookDto.class);

        Assertions.assertNotNull(actual);
        Assertions.assertEquals("Title", actual.getTitle());
    }

    @WithMockUser(username = "user", authorities = {"ADMIN"})
    @Test
    @DisplayName("Update Book entity by VALID ID")
    @Sql(scripts = "classpath:database/book/insert-controller-book.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/book/remove-controller-book.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateBookById_ValidId_Successful() throws Exception {
        UpdateBookRequestDto requestDto = new UpdateBookRequestDto();
        requestDto.setTitle("Title2");
        requestDto.setAuthor("Author2");
        requestDto.setPrice(BigDecimal.valueOf(26.00));
        requestDto.setIsbn("1234");
        requestDto.setDescription("Description");
        requestDto.setCoverImage("Image");

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        mockMvc.perform(put("/books/1")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        MvcResult result = mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andReturn();

        BookDto actual =
                objectMapper.readValue(result.getResponse().getContentAsString(), BookDto.class);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals("Title2", actual.getTitle());
    }

    @WithMockUser(username = "user", roles = {"ADMIN"})
    @Test
    @DisplayName("Create a Book entity and receive a VALID response")
    public void saveBook_ValidCreateBookRequestDto_Successful()
            throws Exception {
        CreateBookRequestDto dto = new CreateBookRequestDto();
        dto.setTitle("Title");
        dto.setAuthor("Author");
        dto.setIsbn("ISBN");
        dto.setPrice(BigDecimal.valueOf(13.00));
        dto.setDescription("Description");
        dto.setCoverImage("Image");

        BookDto expected = formBookDto();

        String jsonRequest = objectMapper.writeValueAsString(dto);

        MvcResult result = mockMvc.perform(post("/books")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        BookDto actual =
                objectMapper.readValue(result.getResponse().getContentAsString(), BookDto.class);

        Assertions.assertNotNull(actual);

        EqualsBuilder
                .reflectionEquals(expected, actual, "id", "author", "title");
    }

    @WithMockUser(username = "user", roles = {"ADMIN"})
    @Test
    @DisplayName("Delete Book entity by VALID ID")
    @Sql(scripts = "classpath:database/book/insert-controller-book.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void deleteBookById_ValidId_Successful() throws Exception {
        mockMvc.perform(delete("/books/1"))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    private static BookDto formBookDto() {
        BookDto dto = new BookDto();
        dto.setId(1L);
        dto.setAuthor("Author");
        dto.setTitle("Title");
        dto.setIsbn("ISBN");
        dto.setPrice(BigDecimal.valueOf(13.00));
        dto.setDescription("Description");
        dto.setCoverImage("Image");
        return dto;
    }
}
