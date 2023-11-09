package de.muenchen.mobidam.domain.dtos;

import de.muenchen.mobidam.annotations.ValidFromBeforeValidUntil;
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
@ValidFromBeforeValidUntil(startDate = "getValidFrom", endDate = "getValidUntil", message = "Enddatum muss nach dem Startdatum liegen.")
public class ZuordnungCreateDTO {

    @NotNull
    private String schnittstelle;
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
