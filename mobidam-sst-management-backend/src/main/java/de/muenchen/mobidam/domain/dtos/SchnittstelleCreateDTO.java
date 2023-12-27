package de.muenchen.mobidam.domain.dtos;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class SchnittstelleCreateDTO {

    @NotNull
    private String name;

    @NotNull
    private LocalDate creationDate;

    private LocalDate editDate;

    private String status;
    private String explanation;

}
