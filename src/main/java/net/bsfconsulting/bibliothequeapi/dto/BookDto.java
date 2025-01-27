package net.bsfconsulting.bibliothequeapi.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDto {
//    private Long id;
    private String title;
    private String author;
    private Integer publicationYear;
//    private Boolean available;
}
