package de.muenchen.mobidam.repository;

import de.muenchen.mobidam.domain.Zuordnung;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZuordnungRepository extends CrudRepository<Zuordnung, UUID> {

}
