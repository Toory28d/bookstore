package com.bookstore.service;

import com.bookstore.dto.request.BookRequestDto;
import com.bookstore.dto.response.BookResponseDto;
import com.bookstore.entity.Author;
import com.bookstore.entity.Book;
import com.bookstore.entity.Category;
import com.bookstore.exception.DuplicateResourceException;
import com.bookstore.exception.ResourceNotFoundException;
import com.bookstore.mapper.BookMapper;
import com.bookstore.repository.AuthorRepository;
import com.bookstore.repository.BookRepository;
import com.bookstore.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookService bookService;

    private Book book;
    private Author author;
    private Category category;
    private BookRequestDto bookRequestDto;
    private BookResponseDto bookResponseDto;

    @BeforeEach
    void setUp() {
        author = new Author();
        author.setId(1L);
        author.setFirstName("Isaac");
        author.setLastName("Asimov");

        category = new Category();
        category.setId(1L);
        category.setName("Science Fiction");

        book = new Book();
        book.setId(1L);
        book.setTitle("Foundation");
        book.setIsbn("978-0553293357");
        book.setPrice(new BigDecimal("15.99"));
        book.setStockQuantity(50);
        book.setAuthor(author);
        book.setCategory(category);

        bookRequestDto = new BookRequestDto();
        bookRequestDto.setTitle("Foundation");
        bookRequestDto.setIsbn("978-0553293357");
        bookRequestDto.setPrice(new BigDecimal("15.99"));
        bookRequestDto.setStockQuantity(50);
        bookRequestDto.setAuthorId(1L);
        bookRequestDto.setCategoryId(1L);

        bookResponseDto = new BookResponseDto();
        bookResponseDto.setId(1L);
        bookResponseDto.setTitle("Foundation");
        bookResponseDto.setIsbn("978-0553293357");
        bookResponseDto.setPrice(new BigDecimal("15.99"));
        bookResponseDto.setStockQuantity(50);
    }

    @Test
    void testGetAllBooks() {
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book));
        when(bookMapper.toResponseDto(any(Book.class))).thenReturn(bookResponseDto);

        List<BookResponseDto> result = bookService.getAllBooks();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Foundation", result.get(0).getTitle());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void testGetBookById_Success() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(bookMapper.toResponseDto(any(Book.class))).thenReturn(bookResponseDto);

        BookResponseDto result = bookService.getBookById(1L);

        assertNotNull(result);
        assertEquals("Foundation", result.getTitle());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void testGetBookById_NotFound() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookService.getBookById(1L));
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateBook_Success() {
        when(bookRepository.existsByIsbn(anyString())).thenReturn(false);
        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(author));
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        when(bookMapper.toEntity(any(BookRequestDto.class))).thenReturn(book);
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(bookMapper.toResponseDto(any(Book.class))).thenReturn(bookResponseDto);

        BookResponseDto result = bookService.createBook(bookRequestDto);

        assertNotNull(result);
        assertEquals("Foundation", result.getTitle());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void testCreateBook_DuplicateIsbn() {
        when(bookRepository.existsByIsbn(anyString())).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> bookService.createBook(bookRequestDto));
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void testCreateBook_AuthorNotFound() {
        when(bookRepository.existsByIsbn(anyString())).thenReturn(false);
        when(authorRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookService.createBook(bookRequestDto));
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void testUpdateBook_Success() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(author));
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(bookMapper.toResponseDto(any(Book.class))).thenReturn(bookResponseDto);

        BookResponseDto result = bookService.updateBook(1L, bookRequestDto);

        assertNotNull(result);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void testDeleteBook_Success() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));

        bookService.deleteBook(1L);

        verify(bookRepository, times(1)).delete(any(Book.class));
    }

    @Test
    void testGetBooksByCategory() {
        when(bookRepository.findByCategoryId(anyLong())).thenReturn(Arrays.asList(book));
        when(bookMapper.toResponseDto(any(Book.class))).thenReturn(bookResponseDto);

        List<BookResponseDto> result = bookService.getBooksByCategory(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(bookRepository, times(1)).findByCategoryId(1L);
    }

    @Test
    void testGetBooksByAuthor() {
        when(bookRepository.findByAuthorId(anyLong())).thenReturn(Arrays.asList(book));
        when(bookMapper.toResponseDto(any(Book.class))).thenReturn(bookResponseDto);

        List<BookResponseDto> result = bookService.getBooksByAuthor(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(bookRepository, times(1)).findByAuthorId(1L);
    }
}