package de.muenchen.mobidam.domain;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

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
public class Zuordnung extends BaseEntity {

    private static final long serialVersionUID = 1L;

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
    @NotEmpty
    private LocalDate validFrom;

    @Column(nullable = false)
    @NotEmpty
    private LocalDate validUntil;

}