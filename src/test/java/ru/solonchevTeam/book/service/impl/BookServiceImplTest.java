package ru.solonchevTeam.book.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.solonchevTeam.book.TestData;
import ru.solonchevTeam.book.domain.Book;
import ru.solonchevTeam.book.domain.BookEntity;
import ru.solonchevTeam.book.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private BookServiceImpl underTest;

    @Test
    public void testThatBookIsSaved() {
        final Book book = TestData.testBook();

        final BookEntity bookEntity = TestData.testBookEntity();

        Mockito.when(bookRepository.save(Mockito.eq(bookEntity))).thenReturn(bookEntity);
        final Book result = underTest.save(book);

        Assertions.assertEquals(book, result);
    }

    @Test
    public void testThatFindByIdReturnsEmptyWhenNoBook() {
        final String isbn = "02945371";

        Mockito.when(bookRepository.findById(Mockito.eq(isbn))).thenReturn(Optional.empty());

        final Optional<Book> result = underTest.findById(isbn);
        Assertions.assertEquals(Optional.empty(), result);

    }

    @Test
    public void testThatFindByReturnsBookWhenExists() {
        final Book book = TestData.testBook();
        final BookEntity bookEntity = TestData.testBookEntity();

        Mockito.when(bookRepository.findById(Mockito.eq(book.getIsbn()))).thenReturn(Optional.of(bookEntity));

        final Optional<Book> result = underTest.findById(book.getIsbn());
        Assertions.assertEquals(Optional.of(book), result);
    }

    @Test
    public void testListBooksReturnsEmptyListWhenNoBooksExists() {
        Mockito.when(bookRepository.findAll()).thenReturn(new ArrayList<>());
        final List<Book> result = underTest.listBooks();
        Assertions.assertEquals(0, result.size());
    }

    @Test
    public void testListBooksReturnBooksWhenExist() {
        final BookEntity bookEntity = TestData.testBookEntity();
        Mockito.when(bookRepository.findAll()).thenReturn(List.of(bookEntity));
        final List<Book> result = underTest.listBooks();
        Assertions.assertEquals(1, result.size());
    }

    @Test
    public void testBookExistsReturnsFalseWhenBookDoesntExist() {
        Mockito.when(bookRepository.existsById(Mockito.any())).thenReturn(false);
        final Book book = TestData.testBook();
        boolean result = underTest.isBookExists(book);
        Assertions.assertFalse(result);
    }

    @Test
    public void testDeleteBookDeletesBook() {
        final String isbn = "13123123";
        underTest.deleteBookById(isbn);
        Mockito.verify(bookRepository, Mockito.times(1)).deleteById(Mockito.eq(isbn));
    }
}
