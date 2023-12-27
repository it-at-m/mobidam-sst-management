package de.muenchen.mobidam.domain.mappers;

import de.muenchen.mobidam.domain.Schnittstelle;
import de.muenchen.mobidam.domain.dtos.SchnittstelleCreateDTO;
import de.muenchen.mobidam.domain.dtos.SchnittstelleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface SchnittstelleMapper {

    SchnittstelleDTO toDTO(Schnittstelle schnittstelle);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", source = "status.text")
    Schnittstelle toEntity(SchnittstelleCreateDTO schnittstelleCreateDTO);

    Schnittstelle toEntityWithId(SchnittstelleDTO schnittstelleDTO);

}
