package de.muenchen.mobidam.domain;
import de.muenchen.mobidam.annotations.ValidFromBeforeValidUntil;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serial;
import java.time.LocalDate;

/**
 * This class represents a TheEntity.
 * <p>
 * The entity's content will be loaded according to the reference variable.
 * </p>
 */
@Entity
// Definition of getter, setter, ...
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ValidFromBeforeValidUntil(startDate = "getValidFrom", endDate = "getValidUntil", message = "Startdatum muss vor dem Enddatum liegen.")
public class Zuordnung extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @Column(nullable = false)
    @NotEmpty
    private String schnittstelle;

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
    private LocalDate validFrom;

    @Column(nullable = false)
    @NotNull
    private LocalDate validUntil;

}