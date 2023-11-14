package de.muenchen.mobidam.service;

import de.muenchen.mobidam.domain.Zuordnung;
import de.muenchen.mobidam.domain.dtos.ZuordnungCreateDTO;
import de.muenchen.mobidam.domain.dtos.ZuordnungDTO;
import de.muenchen.mobidam.domain.mappers.ZuordnungMapper;
import de.muenchen.mobidam.repository.ZuordnungRepository;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ZuordnungService {
    private final ZuordnungRepository zuordnungRepository;
    private final ZuordnungMapper zuordnungMapper;

    public ZuordnungDTO create(ZuordnungCreateDTO zuordnungCreateDTO) {
        return zuordnungMapper.toDTO(zuordnungRepository.save(zuordnungMapper.toEntity(zuordnungCreateDTO)));
    }

    public Iterable<Zuordnung> findAll() {
        return this.zuordnungRepository.findAll();
    }

    public boolean deleteById(String zuordnungId) {
        if (this.zuordnungRepository.existsById(UUID.fromString(zuordnungId))) {
            this.zuordnungRepository.deleteById(UUID.fromString(zuordnungId));
            return true;
        }
        return false;
    }
}
