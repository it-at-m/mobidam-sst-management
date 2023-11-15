package de.muenchen.mobidam.repository;

import de.muenchen.mobidam.domain.Zuordnung;

import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZuordnungRepository extends CrudRepository<Zuordnung, UUID> {
    List<Zuordnung> findZuordnungsBySchnittstelle(String schnittstelle);
}
