package de.muenchen.mobidam.service;

import de.muenchen.mobidam.domain.dtos.DatentransferDTO;
import de.muenchen.mobidam.domain.mappers.DatentransferMapper;
import de.muenchen.mobidam.repository.DatentransferRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class DatentransferService {

    private static final int PAGE_SIZE = 10;

    private final DatentransferRepository datentransferRepository;
    private final DatentransferMapper datentransferMapper;

    public Optional<Iterable<DatentransferDTO>> getBySchnittstelle(String schnittstelleId, int page) {
        List<DatentransferDTO> dtos = new ArrayList<>();
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        UUID schnittstelleUUID;
        try {
            schnittstelleUUID = UUID.fromString(schnittstelleId);
        } catch (IllegalArgumentException ex) {
            return Optional.empty();
        }
        datentransferRepository.findDatenstransfersBySchnittstelleId(schnittstelleUUID, pageable)
                .forEach(datentransfer -> dtos.add(datentransferMapper.toDTO(datentransfer)));

        return Optional.of(dtos);
    }
}
