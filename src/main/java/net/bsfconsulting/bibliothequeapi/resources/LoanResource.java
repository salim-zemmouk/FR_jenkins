package net.bsfconsulting.bibliothequeapi.resources;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import net.bsfconsulting.bibliothequeapi.dto.LoanDto;
import net.bsfconsulting.bibliothequeapi.entity.Loan;
import net.bsfconsulting.bibliothequeapi.mapper.LoanMapper;
import net.bsfconsulting.bibliothequeapi.services.LoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/loans")
public class LoanResource {

    public final LoanService loanService;

    public LoanResource(LoanService loanService) {
        this.loanService = loanService;
    }

    @Operation(summary = "Emprunter un livre", description = "Permet à un utilisateur d'emprunter un livre en fournissant son ID et l'ID du livre.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Livre emprunté avec succès",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoanDto.class))),
            @ApiResponse(responseCode = "400", description = "Requête invalide")
    })
    @PostMapping("/borrow")
    public ResponseEntity<LoanDto> borrowBook(@RequestParam Long userId, @RequestParam Long bookId) {
        Loan loan = loanService.borrowBook(userId, bookId);
        LoanDto loanDto = LoanMapper.INSTANCE.loanToLoanDto(loan);
        return ResponseEntity.ok(loanDto);
    }

    @Operation(summary = "Retourner un livre", description = "Permet à un utilisateur de retourner un livre en fournissant l'ID du prêt.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Livre retourné avec succès",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoanDto.class))),
            @ApiResponse(responseCode = "404", description = "Prêt introuvable")
    })
    @PostMapping("/return")
    public ResponseEntity<LoanDto> returnBook(@RequestParam Long loanId) {
        Loan loan = loanService.returnBook(loanId);
        LoanDto loanDto = LoanMapper.INSTANCE.loanToLoanDto(loan);
        return ResponseEntity.ok(loanDto);
    }

    @Operation(summary = "Liste des prêts", description = "Récupère la liste de tous les prêts dans le système.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des prêts récupérée avec succès",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoanDto.class))),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping
    public ResponseEntity<List<LoanDto>> getLoans() {
        List<Loan> loans = loanService.getLoans();
        List<LoanDto> loanDtos = loans.stream()
                .map(LoanMapper.INSTANCE::loanToLoanDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(loanDtos);
    }
}
