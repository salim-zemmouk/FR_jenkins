package net.bsfconsulting.bibliothequeapi.resources;

import net.bsfconsulting.bibliothequeapi.entity.Loan;
import net.bsfconsulting.bibliothequeapi.services.LoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/loans")
public class LoanResource {
    public final LoanService loanService;

    public LoanResource(LoanService loanService) {
        this.loanService = loanService;
    }
    @PostMapping("/borrow")
    public ResponseEntity<Loan> borrowBook(@RequestParam Long userId , @RequestParam Long bookId){
        Loan loan = loanService.borrowBook(userId, bookId);
        return ResponseEntity.ok(loan);
    }
    @PostMapping("/return")
    public ResponseEntity<Loan> returnBool(@RequestParam Long loanId){
        Loan loan = loanService.returnBook(loanId);
        return ResponseEntity.ok(loan);
    }
    @GetMapping
    public ResponseEntity<List<Loan>> getLoans(){
        List<Loan> loans = loanService.getLoans();
        return ResponseEntity.ok(loans);
    }

}
