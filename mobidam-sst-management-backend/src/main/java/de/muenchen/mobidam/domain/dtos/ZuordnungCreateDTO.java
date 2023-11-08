package de.muenchen.mobidam.domain.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@Builder
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
