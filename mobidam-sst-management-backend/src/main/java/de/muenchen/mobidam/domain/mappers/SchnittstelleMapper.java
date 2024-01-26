package de.muenchen.mobidam.domain.mappers;

import de.muenchen.mobidam.domain.Schnittstelle;
import de.muenchen.mobidam.domain.dtos.SchnittstelleCreateDTO;
import de.muenchen.mobidam.domain.dtos.SchnittstelleDTO;
import java.time.LocalDate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface SchnittstelleMapper {

    SchnittstelleDTO toDTO(Schnittstelle schnittstelle);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "zuordnungen", ignore = true)
    @Mapping(target = "datentransfers", ignore = true)
    @Mapping(target = "creationDate", source = "creationDate")
    @Mapping(target = "editDate", ignore = true)
    Schnittstelle toEntity(SchnittstelleCreateDTO schnittstelleCreateDTO, LocalDate creationDate);

    @Mapping(target = "zuordnungen", ignore = true)
    @Mapping(target = "datentransfers", ignore = true)
    Schnittstelle toEntityWithId(SchnittstelleDTO schnittstelleDTO);

}
