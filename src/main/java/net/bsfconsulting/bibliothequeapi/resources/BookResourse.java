package net.bsfconsulting.bibliothequeapi.resources;

import net.bsfconsulting.bibliothequeapi.entity.Book;
import net.bsfconsulting.bibliothequeapi.services.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/books")

public class BookResourse {
    private final BookService bookService;

    public BookResourse(BookService bookService) {
        this.bookService = bookService;
    }
    @PostMapping("/add")
    public Book addBook(@RequestBody Book book){
        return bookService.saveBook(book);
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks(){
        List<Book> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }
    @GetMapping("/available")
    public ResponseEntity<List<Book>> getAvailableBooks(){
        List<Book> availableBooks = bookService.getAvailableBooks();
        return ResponseEntity.ok(availableBooks);
    }
    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooksByTitle(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author
    ){
        if (title != null){
            return ResponseEntity.ok(bookService.searchBooksByTitle(title));
        } else if (author != null) {
            return ResponseEntity.ok(bookService.searchBooksByAuthor(author));
        }
        return ResponseEntity.badRequest().build();
    }
}
