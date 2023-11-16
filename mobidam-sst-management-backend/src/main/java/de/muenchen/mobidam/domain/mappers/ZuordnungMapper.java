package de.muenchen.mobidam.domain.mappers;

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
    ZuordnungDTO toDTO(Zuordnung zuordnung);

    @Mapping(target = "id", ignore = true)
    Zuordnung toEntity(ZuordnungCreateDTO zuordnungCreateDTO);
}
