package de.muenchen.mobidam.domain.dtos;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public class SchnittstelleDTO {

    @NotNull
    private UUID id;

    @NotNull
    private String name;

    @NotNull
    private LocalDate creationDate;

    private LocalDate editDate;

    private String status;
    private String explanation;

}
