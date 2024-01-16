package de.muenchen.mobidam.domain.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class SchnittstelleCreateDTO {

    @NotNull
    private String name;

    @NotNull
    private LocalDate creationDate;

    private LocalDate editDate;

    private String status;
    private String explanation;

}
