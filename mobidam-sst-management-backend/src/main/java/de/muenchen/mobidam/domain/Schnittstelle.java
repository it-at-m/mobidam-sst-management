package de.muenchen.mobidam.domain;

import de.muenchen.mobidam.domain.enums.SchnittstellenStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true, exclude = { "zuordnungen", "datentransfers" })
@NoArgsConstructor
@Table(name = "Management_Schnittstelle_Schnittstelle_Tab")
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

    @OneToMany(orphanRemoval = true, mappedBy = "schnittstelle")
    @ToString.Exclude
    private Set<Datentransfer> datentransfers = new HashSet<>();

}
