package de.muenchen.mobidam.domain;

import de.muenchen.mobidam.domain.enums.SchnittstellenStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true, exclude = {"zuordnungen"})
@NoArgsConstructor
public class Schnittstelle extends BaseEntity {

    @Column(nullable = false)
    @NotEmpty
    private String name;

    @Column(nullable = false)
    @NotNull
    @FutureOrPresent
    private LocalDate creationDate;

    @Column
    @FutureOrPresent
    private LocalDate editDate;

    @Column(nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    private SchnittstellenStatus status;

    @Column
    private String explanation;

    @OneToMany(orphanRemoval = true, mappedBy = "schnittstelle")
    @ToString.Exclude
    private Set<Zuordnung> zuordnungen = new HashSet<>();

}
