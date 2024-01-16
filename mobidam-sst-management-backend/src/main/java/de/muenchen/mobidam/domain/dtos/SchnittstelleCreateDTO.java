package de.muenchen.mobidam.domain.dtos;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
