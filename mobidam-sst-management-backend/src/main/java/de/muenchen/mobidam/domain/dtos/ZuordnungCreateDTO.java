package de.muenchen.mobidam.domain.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class ZuordnungCreateDTO {

    @NotNull
    private String userID;
    @NotNull
    private String department;
    @NotNull
    private String functionAddress;
    @NotNull
    private LocalDate validFrom;
    @NotNull
    private LocalDate validUntil;
}
