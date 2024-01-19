package de.muenchen.mobidam.domain.mappers;

import de.muenchen.mobidam.domain.Schnittstelle;
import de.muenchen.mobidam.domain.Zuordnung;
import de.muenchen.mobidam.domain.dtos.ZuordnungCreateDTO;
import de.muenchen.mobidam.domain.dtos.ZuordnungDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper DTO<->Entity for Zuordnungen
 */
@Mapper
public interface ZuordnungMapper {

    @Mapping(target = "schnittstelle", source = "schnittstelle.id")
    ZuordnungDTO toDTO(Zuordnung zuordnung);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "schnittstelle", target = "schnittstelle")
    Zuordnung toEntity(ZuordnungCreateDTO zuordnungCreateDTO, Schnittstelle schnittstelle);
}
