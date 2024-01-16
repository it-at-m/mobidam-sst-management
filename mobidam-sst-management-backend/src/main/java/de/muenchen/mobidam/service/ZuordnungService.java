package de.muenchen.mobidam.service;

import de.muenchen.mobidam.domain.Schnittstelle;
import de.muenchen.mobidam.domain.Zuordnung;
import de.muenchen.mobidam.domain.dtos.ZuordnungCreateDTO;
import de.muenchen.mobidam.domain.dtos.ZuordnungDTO;
import de.muenchen.mobidam.domain.mappers.ZuordnungMapper;
import de.muenchen.mobidam.repository.SchnittstelleRepository;
import de.muenchen.mobidam.repository.ZuordnungRepository;

import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service Controller of operations on zuordnungen
 */
@AllArgsConstructor
@Service
public class ZuordnungService {
    private final ZuordnungRepository zuordnungRepository;
    private final ZuordnungMapper zuordnungMapper;
    private final SchnittstelleRepository schnittstelleRepository;

    public Optional<ZuordnungDTO> create(ZuordnungCreateDTO zuordnungCreateDTO) {
        Optional<Schnittstelle> schnittstelle = schnittstelleRepository.findById(zuordnungCreateDTO.getSchnittstelle());
        return schnittstelle.map(value -> zuordnungMapper.toDTO(zuordnungRepository.save(zuordnungMapper.toEntity(zuordnungCreateDTO, value))));
    }

    public Iterable<Zuordnung> getAllById(String id) {
        return this.zuordnungRepository.findZuordnungsBySchnittstelleId(UUID.fromString(id));
    }

    public boolean deleteById(String zuordnungId) {
        if (this.zuordnungRepository.existsById(UUID.fromString(zuordnungId))) {
            this.zuordnungRepository.deleteById(UUID.fromString(zuordnungId));
            return true;
        }
        return false;
    }
}
