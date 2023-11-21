package de.muenchen.mobidam.domain.dtos;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Data Transfare Object for zuordnungen
 */
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class ZuordnungDTO {

    @NotNull
    private UUID id;
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
