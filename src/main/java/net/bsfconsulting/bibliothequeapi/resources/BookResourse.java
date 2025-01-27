package net.bsfconsulting.bibliothequeapi.resources;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import net.bsfconsulting.bibliothequeapi.dto.BookDto;
import net.bsfconsulting.bibliothequeapi.entity.Book;
import net.bsfconsulting.bibliothequeapi.mapper.BookMapper;
import net.bsfconsulting.bibliothequeapi.services.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("api/v1/books")

public class BookResourse {
    private final BookService bookService;

    public BookResourse(BookService bookService) {
        this.bookService = bookService;
    }
    @Operation(summary = "Ajouter un nouveau livre", description = "Ajoute un livre à la bibliothèque.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Livre ajouté avec succès",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Book.class))}),
            @ApiResponse(responseCode = "400", description = "Requête invalide")
    })
    @PostMapping("/add")
    public ResponseEntity<BookDto> addBook(@RequestBody BookDto bookDto){
        Book book = BookMapper.INSTANCE.bookDtoToBook(bookDto);
        Book savedBook = bookService.saveBook(book);
        BookDto savedBookDto = BookMapper.INSTANCE.bookToBookDto(savedBook);
        return ResponseEntity.ok(savedBookDto);
    }
    @Operation(summary = "Obtenir tous les livres", description = "Récupère la liste complète des livres disponibles dans la bibliothèque.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des livres",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BookDto.class))})
    })
    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBooks(){
        List<Book> books = bookService.getAllBooks();
        List<BookDto> bookDtos = books.stream()
                .map(BookMapper.INSTANCE::bookToBookDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(bookDtos);
    }
    @Operation(summary = "Obtenir les livres disponibles", description = "Récupère uniquement les livres qui sont marqués comme disponibles.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des livres disponibles",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BookDto.class))})
    })
    @GetMapping("/available")
    public ResponseEntity<List<BookDto>> getAvailableBooks(){
        List<Book> availableBooks = bookService.getAvailableBooks();
        List<BookDto> availableBookDtos = availableBooks.stream()
                .map(BookMapper.INSTANCE::bookToBookDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(availableBookDtos);
    }
    @Operation(summary = "Rechercher des livres", description = "Recherche les livres par titre ou par auteur.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des livres trouvés",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BookDto.class))}),
            @ApiResponse(responseCode = "400", description = "Requête invalide")
    })
    @GetMapping("/search")
    public ResponseEntity<List<BookDto>> searchBooksByTitleOrAuthor(
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
