package book.store.onlinebookstore.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import book.store.onlinebookstore.dto.book.BookDto;
import book.store.onlinebookstore.dto.book.BookDtoWithoutCategoryIds;
import book.store.onlinebookstore.dto.category.CategoryDto;
import book.store.onlinebookstore.dto.category.CreateCategoryRequestDto;
import book.store.onlinebookstore.dto.category.UpdateCategoryRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryControllerTest {
    protected static MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void setup(@Autowired WebApplicationContext webApplicationContext,
                      @Autowired DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(connection,
                    new ClassPathResource("database/book/remove-all-books-and-categories.sql"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser(username = "user", roles = {"USER"})
    @Test
    @DisplayName("Get all Category entities")
    @Sql(scripts = "classpath:database/book/insert-category.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/book/remove-category.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllCategories_Successful() throws Exception {
        CategoryDto expected = formCategoryDto();

        MvcResult result = mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andReturn();

        CategoryDto[] actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), CategoryDto[].class);
        Assertions.assertNotNull(actual);
        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @WithMockUser(username = "user", roles = {"USER"})
    @Test
    @DisplayName("Get Category entity by VALID ID")
    @Sql(scripts = "classpath:database/book/insert-category.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/book/remove-category.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findCategoryById_ValidId_Successful() throws Exception {
        CategoryDto expected = formCategoryDto();

        MvcResult result = mockMvc.perform(get("/categories/1"))
                .andExpect(status().isOk())
                .andReturn();

        CategoryDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), CategoryDto.class);
        Assertions.assertNotNull(actual);

        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @WithMockUser(username = "user", roles = {"USER"})
    @Test
    @DisplayName("Get all Book entities by VALID Category ID")
    @Sql(scripts = {
            "classpath:database/book/insert-book.sql",
            "classpath:database/book/insert-category.sql",
            "classpath:database/book/insert-books-categories.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/book/remove-books-categories.sql",
            "classpath:database/book/remove-book.sql",
            "classpath:database/book/remove-category.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllBooksByCategoryId_ValidId_Successful() throws Exception {
        BookDtoWithoutCategoryIds expected = new BookDtoWithoutCategoryIds();
        expected.setId(1L);
        expected.setTitle("Title");
        expected.setAuthor("Author");
        expected.setIsbn("ISBN");
        expected.setPrice(BigDecimal.valueOf(13));
        expected.setDescription("Description");
        expected.setCoverImage("Image");

        MvcResult result = mockMvc.perform(get("/categories/1/books"))
                .andExpect(status().isOk())
                .andReturn();

        BookDto[] actual =
                objectMapper.readValue(result.getResponse().getContentAsString(), BookDto[].class);
        Assertions.assertNotNull(actual);

        List<BookDtoWithoutCategoryIds> expectedList = List.of(expected);
        List<BookDto> actualList = List.of(actual);

        Assertions.assertEquals(expectedList.get(0).getTitle(), actualList.get(0).getTitle());
    }

    @WithMockUser(username = "user", roles = {"ADMIN"})
    @Test
    @DisplayName("Create a Category entity and receive a VALID response")
    @Sql(scripts = "classpath:database/book/remove-category.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void saveCategoryByValidCreateCategoryRequestDto_Successful() throws Exception {
        CreateCategoryRequestDto dto = new CreateCategoryRequestDto();
        dto.setName("Category1");
        dto.setDescription("Description1");

        CategoryDto expected = formCategoryDto();

        String jsonRequest = objectMapper.writeValueAsString(dto);

        MvcResult result = mockMvc.perform(post("/categories")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        CategoryDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), CategoryDto.class);
        Assertions.assertNotNull(actual);

        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Update Category entity by VALID ID")
    @Sql(scripts = "classpath:database/book/insert-category.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/book/remove-category.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateCategoryById_ValidId_Successful() throws Exception {
        UpdateCategoryRequestDto dto = new UpdateCategoryRequestDto();
        dto.setName("Updated Name");
        dto.setDescription("Updated Description");

        CategoryDto expected = new CategoryDto();
        expected.setName("Updated Name");
        expected.setDescription("Updated Description");

        String jsonRequest = objectMapper.writeValueAsString(dto);

        MvcResult result = mockMvc.perform(put("/categories/1")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        CategoryDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), CategoryDto.class);
        Assertions.assertNotNull(actual);

        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @WithMockUser(username = "user", roles = {"ADMIN"})
    @Test
    @DisplayName("Delete Category entity by VALID ID")
    @Sql(scripts = "classpath:database/book/insert-category.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void deleteCategoryById_ValidId_Successful() throws Exception {
        mockMvc.perform(delete("/categories/1"))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    private static CategoryDto formCategoryDto() {
        CategoryDto dto = new CategoryDto();
        dto.setId(1L);
        dto.setName("Category");
        dto.setDescription("Description");
        return dto;
    }
}
