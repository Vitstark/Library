package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Person {

    private Long id;

    @NotEmpty(message = "Email should not be empty")
    @Size(min = 2, max = 40, message = "Age should be between 2 and 40 character")
    private String name;

    @Pattern(regexp = "\\d{4}.\\d{2}.\\d{2}", message = "Date should be like YYYY.MM.DD")
    private String dateOfBirth;
}
