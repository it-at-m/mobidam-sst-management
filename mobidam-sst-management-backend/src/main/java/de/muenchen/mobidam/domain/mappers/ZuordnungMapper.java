package de.muenchen.mobidam.domain.mappers;

import de.muenchen.mobidam.domain.Zuordnung;
import de.muenchen.mobidam.domain.dtos.ZuordnungCreateDTO;
import de.muenchen.mobidam.domain.dtos.ZuordnungDTO;
import org.mapstruct.Mapping;
import org.mapstruct.Mapper;

@Mapper
public interface ZuordnungMapper {
    ZuordnungDTO toDTO(Zuordnung zuordnung);

    @Mapping(target = "id", ignore = true)
    Zuordnung toEntity(ZuordnungCreateDTO zuordnungCreateDTO);
}
