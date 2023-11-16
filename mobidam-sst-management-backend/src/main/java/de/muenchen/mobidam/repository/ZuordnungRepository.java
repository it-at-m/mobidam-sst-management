package de.muenchen.mobidam.repository;

import de.muenchen.mobidam.domain.Zuordnung;

import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository für entity Zuordnung
 */
public interface ZuordnungRepository extends CrudRepository<Zuordnung, UUID> {

    /**
     * Zur Anforderung aller Zuordnungen, die zu einer Schnittstelle gehören
     * @param schnittstelle Identifier der konkreten Schnittstelle
     * @return Liste aller zugehörigen Zuordnungen
     */
    List<Zuordnung> findZuordnungsBySchnittstelle(String schnittstelle);
}
