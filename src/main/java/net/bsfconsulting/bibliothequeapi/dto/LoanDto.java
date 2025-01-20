package net.bsfconsulting.bibliothequeapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class LoanDto {
    private Long id;
    private LocalDate loanDate;
    private LocalDate returnDate;
}
