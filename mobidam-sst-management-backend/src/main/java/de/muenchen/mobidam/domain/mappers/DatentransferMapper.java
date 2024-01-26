package de.muenchen.mobidam.domain.mappers;

import de.muenchen.mobidam.domain.Datentransfer;
import de.muenchen.mobidam.domain.dtos.DatentransferDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface DatentransferMapper {

    @Mapping(target = "schnittstelle", source = "schnittstelle.id")
    DatentransferDTO toDTO(Datentransfer datentransfer);

}