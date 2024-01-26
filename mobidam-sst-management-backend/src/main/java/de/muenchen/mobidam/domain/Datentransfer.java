package de.muenchen.mobidam.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.muenchen.mobidam.domain.enums.EreignisTyp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Datentransfer extends BaseEntity {

    @Column
    @NotEmpty
    private String prozessId;

    @Column
    @NotNull
    private LocalDateTime zeitstempel;

    @Column
    @NotNull
    @Enumerated(EnumType.STRING)
    private EreignisTyp ereignis;

    @Column
    private String info;

    @ManyToOne
    @JoinColumn(name = "schnittstelle_id")
    @NotNull
    @JsonIgnoreProperties({ "datentransfers" })
    private Schnittstelle schnittstelle;
}
