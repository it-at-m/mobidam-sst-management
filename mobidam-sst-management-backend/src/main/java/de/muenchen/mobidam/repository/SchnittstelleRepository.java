package de.muenchen.mobidam.repository;

import de.muenchen.mobidam.domain.Schnittstelle;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface SchnittstelleRepository extends CrudRepository<Schnittstelle, UUID> {

}
