/*
 * The MIT License
 * Copyright © 2023 Landeshauptstadt München | it@M
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package de.muenchen.mobidam.service;

import de.muenchen.mobidam.domain.Schnittstelle;
import de.muenchen.mobidam.domain.dtos.SchnittstelleCreateDTO;
import de.muenchen.mobidam.domain.dtos.SchnittstelleDTO;
import de.muenchen.mobidam.domain.dtos.SchnittstelleGetStatusDTO;
import de.muenchen.mobidam.domain.mappers.SchnittstelleMapper;
import de.muenchen.mobidam.repository.SchnittstelleRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@Slf4j
public class SchnittstelleService {

    private final SchnittstelleRepository schnittstelleRepository;
    private final SchnittstelleMapper schnittstelleMapper;

    public SchnittstelleDTO create(SchnittstelleCreateDTO schnittstelleCreateDTO) {
        log.debug("SchnittstelleService - Creating Schnittstelle: {}", schnittstelleCreateDTO);
        return schnittstelleMapper.toDTO(schnittstelleRepository.save(schnittstelleMapper.toEntity(schnittstelleCreateDTO, LocalDate.now())));
    }

    private boolean exists(SchnittstelleDTO schnittstelleDTO) {
        log.debug("SchnittstelleService - Finding Schnittstelle: {}", schnittstelleDTO);
        return schnittstelleRepository.existsById(schnittstelleDTO.getId());
    }

    public Iterable<SchnittstelleDTO> getAll() {
        List<SchnittstelleDTO> dtos = new ArrayList<>();
        log.debug("SchnittstelleService - Getting all Schnittstelle");
        schnittstelleRepository.findAll().forEach(schnittstelle -> dtos.add(schnittstelleMapper.toDTO(schnittstelle)));
        log.debug("SchnittstelleService - Got all Schnittstelle, count: {}", dtos.size());
        return dtos;
    }

    public boolean deleteById(String id) {
        UUID uuid = UUID.fromString(id);
        if (schnittstelleRepository.existsById(uuid)) {
            log.debug("SchnittstelleService - Found Schnittstelle, now deleting: {}", uuid);
            schnittstelleRepository.deleteById(UUID.fromString(id));
            log.debug("SchnittstelleService - Deleted Schnittstelle: {}", uuid);
            return true;
        } else {
            log.debug("SchnittstelleService - Couldn't find Schnittstelle: {}", uuid);
            return false;
        }
    }

    public Optional<SchnittstelleDTO> update(SchnittstelleDTO schnittstelleDTO) {
        if (this.exists(schnittstelleDTO)) {
            log.debug("SchnittstelleService - Found Schnittstelle, trying to update: {}", schnittstelleDTO.getId());
            schnittstelleDTO.setAenderungsdatum(LocalDate.now());
            log.debug("SchnittstelleService - Starting Mapping to Entity: {}", schnittstelleDTO.getId());
            Schnittstelle entity = schnittstelleMapper.toEntityWithId(schnittstelleDTO);
            log.debug("SchnittstelleService - Ended Mapping to Entity: {}", schnittstelleDTO.getId());
            log.debug("SchnittstelleService - Starting saving to DB: {}", schnittstelleDTO.getId());
            SchnittstelleDTO schnittstelleUpdated = schnittstelleMapper
                    .toDTO(schnittstelleRepository.save(entity));
            log.debug("SchnittstelleService - Ended saving to DB: {}", schnittstelleDTO.getId());
            log.debug("SchnittstelleService - Updated Schnittstelle: {}", schnittstelleDTO.getId());
            return Optional.of(schnittstelleUpdated);
        }
        log.debug("SchnittstelleService - Couldn't find Schnittstelle: {}", schnittstelleDTO.getId());
        return Optional.empty();
    }

    public Optional<SchnittstelleGetStatusDTO> getStatus(String id) {
        UUID uuid = UUID.fromString(id);
        return schnittstelleRepository.findById(uuid).map(schnittstelle -> new SchnittstelleGetStatusDTO(schnittstelle.getStatus().name()));
    }

    public Optional<SchnittstelleDTO> getById(String id) {
        UUID uuid = UUID.fromString(id);
        Optional<Schnittstelle> schnittstelle = schnittstelleRepository.findById(uuid);
        log.debug("SchnittstelleService - Found Schnittstelle: {}", id);
        return schnittstelle.map(schnittstelleMapper::toDTO);
    }
}
