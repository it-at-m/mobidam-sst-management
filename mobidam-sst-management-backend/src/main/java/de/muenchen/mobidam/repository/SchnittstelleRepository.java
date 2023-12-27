package de.muenchen.mobidam.repository;

import de.muenchen.mobidam.domain.Schnittstelle;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface SchnittstelleRepository extends CrudRepository<Schnittstelle, UUID> {

}
