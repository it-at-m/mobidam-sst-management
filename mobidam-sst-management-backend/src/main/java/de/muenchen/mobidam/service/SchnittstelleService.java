/*
 * The MIT License
 *  Copyright © 2023 Landeshauptstadt München | it@M
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */
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
