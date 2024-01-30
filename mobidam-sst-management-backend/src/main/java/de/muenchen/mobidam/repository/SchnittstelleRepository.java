/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2024
 */
package de.muenchen.mobidam.repository;

import de.muenchen.mobidam.domain.Schnittstelle;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface SchnittstelleRepository extends CrudRepository<Schnittstelle, UUID> {

}
