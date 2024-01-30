/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2024
 */
package de.muenchen.mobidam.domain.dtos;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
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
