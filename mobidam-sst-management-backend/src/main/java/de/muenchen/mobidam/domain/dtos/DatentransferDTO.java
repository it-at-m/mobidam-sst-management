package de.muenchen.mobidam.domain.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class DatentransferDTO {

    @NotNull
    private UUID id;

    @NotEmpty
    private String prozessId;

    @NotNull
    private LocalDateTime zeitstempel;

    @NotEmpty
    private String ereignis;

    private String info;

    @NotNull
    private UUID schnittstelle;

}
