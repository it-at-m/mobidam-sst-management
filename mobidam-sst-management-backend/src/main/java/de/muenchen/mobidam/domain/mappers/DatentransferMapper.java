package de.muenchen.mobidam.domain.mappers;

import de.muenchen.mobidam.domain.Datenstransfer;
import de.muenchen.mobidam.domain.dtos.DatentransferDTO;
import org.mapstruct.Mapping;

public interface DatentransferMapper {

    @Mapping(target = "schnittstelle", source = "schnittstelle.id")
    DatentransferDTO toDTO(Datenstransfer datenstransfer);

}
