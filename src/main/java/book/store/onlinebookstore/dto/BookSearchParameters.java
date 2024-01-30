package book.store.onlinebookstore.dto;

public record BookSearchParameters(String[] authors, String[] titles,
                                   String[] isbnS) {
}
