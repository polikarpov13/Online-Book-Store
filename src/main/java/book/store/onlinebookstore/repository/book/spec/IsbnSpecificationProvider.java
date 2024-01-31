package book.store.onlinebookstore.repository.book.spec;

import book.store.onlinebookstore.model.Book;
import book.store.onlinebookstore.repository.SpecificationProvider;
import java.util.Arrays;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class IsbnSpecificationProvider implements SpecificationProvider<Book> {
    private static final String KEY = "isbn";

    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public Specification<Book> getSpecification(String[] params) {
        return (root, query, criteriaBuilder) ->
                root.get(KEY).in(Arrays.stream(params).toArray());
    }
}
