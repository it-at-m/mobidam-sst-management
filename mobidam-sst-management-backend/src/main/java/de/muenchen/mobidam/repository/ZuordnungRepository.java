/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2024
 */
package de.muenchen.mobidam.repository;

import de.muenchen.mobidam.domain.Zuordnung;
import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository for entity zuordnung
 */
public interface ZuordnungRepository extends CrudRepository<Zuordnung, UUID> {

    /**
     * To request all zuordnungen that belong to a schnittstelle
     *
     * @param schnittstelle Identifier of the specific schnittstelle
     * @return list of zuordnungen
     */
    List<Zuordnung> findZuordnungsBySchnittstelleId(UUID schnittstelle);
}
