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
