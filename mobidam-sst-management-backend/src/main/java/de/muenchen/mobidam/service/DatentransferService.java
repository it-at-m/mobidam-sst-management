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

import de.muenchen.mobidam.domain.Datentransfer;
import de.muenchen.mobidam.domain.Schnittstelle;
import de.muenchen.mobidam.domain.dtos.DatentransferCreateDTO;
import de.muenchen.mobidam.domain.dtos.DatentransferDTO;
import de.muenchen.mobidam.domain.enums.EreignisTyp;
import de.muenchen.mobidam.domain.mappers.DatentransferMapper;
import de.muenchen.mobidam.repository.DatentransferRepository;
import de.muenchen.mobidam.repository.SchnittstelleRepository;
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
    private static final int INFO_LENGTH = 512;

    private final DatentransferRepository datentransferRepository;
    private final SchnittstelleRepository schnittstelleRepository;
    private final DatentransferMapper datentransferMapper;

    public Iterable<DatentransferDTO> getBySchnittstelle(String schnittstelleId, int page) {
        List<DatentransferDTO> dtos = new ArrayList<>();
        int offset = page - 1;
        Pageable pageable = PageRequest.of(offset, PAGE_SIZE);
        UUID schnittstelleUUID = UUID.fromString(schnittstelleId);

        datentransferRepository.findDatenstransfersBySchnittstelleIdOrderByZeitstempelDesc(schnittstelleUUID, pageable)
                .forEach(datentransfer -> dtos.add(datentransferMapper.toDTO(datentransfer)));

        return dtos;
    }

    public Optional<DatentransferDTO> getLatestResultStateBySchnittstelle(String schnittstelleId) {
        List<EreignisTyp> notResultStateEreignisTypes = List.of(EreignisTyp.BEGINN, EreignisTyp.ENDE);
        Optional<Datentransfer> datentransfer = datentransferRepository.findFirstBySchnittstelleIdAndEreignisIsNotInOrderByZeitstempelDesc(
                UUID.fromString(schnittstelleId), notResultStateEreignisTypes);
        return datentransfer.map(datentransferMapper::toDTO);
    }

    public Optional<DatentransferDTO> createDatentransfer(DatentransferCreateDTO datentransferDTO) {
        Optional<Schnittstelle> schnittstelle = schnittstelleRepository.findById(datentransferDTO.getSchnittstelle());
        if (schnittstelle.isEmpty())
            return Optional.empty();

        datentransferDTO.setInfo(transformDatentransferInfo(datentransferDTO.getInfo()));
        Datentransfer datentransfer = datentransferMapper.toEntity(datentransferDTO, EreignisTyp.valueOf(datentransferDTO.getEreignis()));
        return Optional.of(datentransferMapper.toDTO(datentransferRepository.save(datentransfer)));
    }

    public Optional<Integer> getDatentransferNumber(String schnittstelleId) {
        Optional<Schnittstelle> schnittstelle = schnittstelleRepository.findById(UUID.fromString(schnittstelleId));
        if (schnittstelle.isEmpty())
            return Optional.empty();

        return datentransferRepository.countBySchnittstelleId(UUID.fromString(schnittstelleId));
    }

    public String transformDatentransferInfo(String info) {
        if (info != null && info.length() > INFO_LENGTH)
            return info.substring(0, INFO_LENGTH);
        return info;
    }
}
