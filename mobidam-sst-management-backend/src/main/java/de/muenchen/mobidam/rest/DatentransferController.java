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
package de.muenchen.mobidam.rest;

import de.muenchen.mobidam.domain.dtos.DatentransferCreateDTO;
import de.muenchen.mobidam.domain.dtos.DatentransferDTO;
import de.muenchen.mobidam.service.DatentransferService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/datentransfer")
@Slf4j
public class DatentransferController {

    private final DatentransferService datentransferService;

    @Operation(summary = "Get all Datentransfers for Schnittstelle")
    @GetMapping("/{schnittstelleId}/{page}")
    @PreAuthorize("hasRole(T(de.muenchen.mobidam.security.AuthoritiesEnum).ADMIN.name())")
    public ResponseEntity<Iterable<DatentransferDTO>> getBySchnittstelle(@PathVariable String schnittstelleId, @PathVariable int page) {
        return new ResponseEntity<>(datentransferService.getBySchnittstelle(schnittstelleId, page), HttpStatus.OK);
    }

    @Operation(summary = "Get first Datentransfer result state for Schnittstelle")
    @GetMapping("/latestResultState/{schnittstelleId}")
    @PreAuthorize("hasRole(T(de.muenchen.mobidam.security.AuthoritiesEnum).ADMIN.name())")
    public ResponseEntity<?> getLatestResultStateBySchnittstelle(@PathVariable String schnittstelleId) {
        Optional<DatentransferDTO> datentransferDTO = datentransferService.getLatestResultStateBySchnittstelle(schnittstelleId);
        if (datentransferDTO.isPresent())
            return new ResponseEntity<>(datentransferDTO.get(), HttpStatus.OK);
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Creating a Datentransfer for an existing Schnittstelle")
    @PostMapping
    public ResponseEntity<?> createDatentransfer(@Valid @RequestBody DatentransferCreateDTO datentransferCreateDTO) {
        log.info("Creating Datentransfer: {}", datentransferCreateDTO);
        Optional<DatentransferDTO> datentransferDTO = datentransferService.createDatentransfer(datentransferCreateDTO);
        if (datentransferDTO.isPresent())
            return new ResponseEntity<>(datentransferDTO.get(), HttpStatus.OK);
        return ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Getting the total number of Datentransfers for an existing Schnittstelle")
    @GetMapping("/{schnittstelleId}")
    public ResponseEntity<Integer> getNumberOfDatentransfers(@PathVariable String schnittstelleId) {
        Optional<Integer> datentransferNumber = datentransferService.getDatentransferNumber(schnittstelleId);
        return datentransferNumber.map(integer -> new ResponseEntity<>(integer, HttpStatus.OK)).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
