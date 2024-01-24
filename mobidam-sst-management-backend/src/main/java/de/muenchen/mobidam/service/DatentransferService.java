package de.muenchen.mobidam.service;

import de.muenchen.mobidam.domain.dtos.DatentransferDTO;
import de.muenchen.mobidam.domain.mappers.DatentransferMapper;
import de.muenchen.mobidam.repository.DatentransferRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class DatentransferService {

    private final DatentransferRepository datentransferRepository;
    private final DatentransferMapper datentransferMapper;

    public Iterable<DatentransferDTO> getBySchnittstelle(String schnittstelleId) {
        List<DatentransferDTO> dtos = new ArrayList<>();
        datentransferRepository.findDatenstransfersBySchnittstelleId(UUID.fromString(schnittstelleId))
                .forEach(datenstransfer -> dtos.add(datentransferMapper.toDTO(datenstransfer)));

        return dtos;
    }
}
