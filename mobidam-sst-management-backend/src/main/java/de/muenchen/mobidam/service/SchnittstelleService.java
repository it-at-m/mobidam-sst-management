package de.muenchen.mobidam.service;

import de.muenchen.mobidam.domain.dtos.SchnittstelleCreateDTO;
import de.muenchen.mobidam.domain.dtos.SchnittstelleDTO;
import de.muenchen.mobidam.domain.mappers.SchnittstelleMapper;
import de.muenchen.mobidam.repository.SchnittstelleRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class SchnittstelleService {

    private final SchnittstelleRepository schnittstelleRepository;
    private final SchnittstelleMapper schnittstelleMapper;

    public SchnittstelleDTO create(SchnittstelleCreateDTO schnittstelleCreateDTO) {
        return schnittstelleMapper.toDTO(schnittstelleRepository.save(schnittstelleMapper.toEntity(schnittstelleCreateDTO, LocalDate.now())));
    }

    private boolean exists(SchnittstelleDTO schnittstelleDTO) {
        return schnittstelleRepository.existsById(schnittstelleDTO.getId());
    }

    public Iterable<SchnittstelleDTO> getAll() {
        List<SchnittstelleDTO> dtos = new ArrayList<>();
        schnittstelleRepository.findAll().forEach(schnittstelle -> dtos.add(schnittstelleMapper.toDTO(schnittstelle)));

        return dtos;
    }

    public boolean deleteById(String id) {
        UUID uuid = UUID.fromString(id);
        if (schnittstelleRepository.existsById(uuid)) {
            schnittstelleRepository.deleteById(UUID.fromString(id));
            return true;
        } else {
            return false;
        }
    }

    public Optional<SchnittstelleDTO> update(SchnittstelleDTO schnittstelleDTO) {
        if (this.exists(schnittstelleDTO)) {
            schnittstelleDTO.setEditDate(LocalDate.now());
            return Optional.of(schnittstelleMapper.toDTO(schnittstelleRepository.save(schnittstelleMapper.toEntityWithId(schnittstelleDTO))));
        }
        return Optional.empty();
    }

}
