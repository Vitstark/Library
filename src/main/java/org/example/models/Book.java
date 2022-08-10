package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {
    private Long id;

    @NotEmpty(message = "Name of book should not be empty")
    @Size(min = 1, max = 20, message = "Name size should be between 1 and 20")
    private String name;

    @NotEmpty(message = "Author should not be empty")
    @Size(min = 2, max = 40, message = "Name size of author should be between 2 and 40")
    private String author;

    @NotEmpty(message = "Date should not be empty")
    private Integer date;

    private Long personId;
}
