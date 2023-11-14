package de.muenchen.mobidam.service;

import de.muenchen.mobidam.domain.Zuordnung;
import de.muenchen.mobidam.domain.dtos.ZuordnungCreateDTO;
import de.muenchen.mobidam.domain.dtos.ZuordnungDTO;
import de.muenchen.mobidam.domain.mappers.ZuordnungMapper;
import de.muenchen.mobidam.exceptions.SavingException;
import de.muenchen.mobidam.repository.ZuordnungRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ZuordnungService {
    private final ZuordnungRepository zuordnungRepository;
    private final ZuordnungMapper zuordnungMapper;

    public ZuordnungDTO create(ZuordnungCreateDTO zuordnungCreateDTO) {
        try {
            Zuordnung zuordnungEntity = zuordnungMapper.toEntity(zuordnungCreateDTO);
            Zuordnung savedZuordnung = zuordnungRepository.save(zuordnungEntity);
            return zuordnungMapper.toDTO(savedZuordnung);
        } catch (Exception exception) {
            throw new SavingException("Fehler beim Anlegen der Zuordnung!");
        }
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
