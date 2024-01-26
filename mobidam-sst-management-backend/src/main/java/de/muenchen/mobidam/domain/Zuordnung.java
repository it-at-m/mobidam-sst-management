package de.muenchen.mobidam.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.muenchen.mobidam.annotations.StartDateNotAfterEndDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Zuordnung entity
 */
@Entity
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Table(name = "SST_Zuordnung")
@StartDateNotAfterEndDate(startDate = "getValidFrom", endDate = "getValidUntil", message = "Das Startdatum muss vor dem Enddatum liegen.")
public class Zuordnung extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "schnittstelle_id")
    @NotNull
    @JsonIgnoreProperties({ "zuordnungen" })
    private Schnittstelle schnittstelle;

    @Column(nullable = false)
    @NotEmpty
    private String userID;

    @Column(nullable = false)
    @NotEmpty
    private String department;

    @Column(nullable = false)
    @NotEmpty
    private String functionAddress;

    @Column(nullable = false)
    @NotNull
    @FutureOrPresent
    private LocalDate validFrom;

    @Column(nullable = false)
    @NotNull
    @FutureOrPresent
    private LocalDate validUntil;

}
