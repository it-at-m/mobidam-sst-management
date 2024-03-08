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
package de.muenchen.mobidam.rest;

import de.muenchen.mobidam.domain.dtos.ZuordnungCreateDTO;
import de.muenchen.mobidam.domain.dtos.ZuordnungDTO;
import de.muenchen.mobidam.domain.mappers.ZuordnungMapper;
import de.muenchen.mobidam.service.ZuordnungService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller of operations on zuordnungen
 */
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/zuordnung")
public class ZuordnungController {
    private final ZuordnungService zuordnungService;
    private final ZuordnungMapper zuordnungMapper;

    @Operation(summary = "Generates a zuordnung")
    @PostMapping
    @PreAuthorize("hasRole(T(de.muenchen.mobidam.security.AuthoritiesEnum).ADMIN.name())")
    public ResponseEntity<?> create(@Valid @RequestBody ZuordnungCreateDTO zuordnungCreateDTO) {
        Optional<ZuordnungDTO> zuordnungDTO = zuordnungService.create(zuordnungCreateDTO);
        if (zuordnungDTO.isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Given Schnittstelle for this Zuordnung doesn't exist.");
        return new ResponseEntity<>(zuordnungDTO.get(), HttpStatus.OK);
    }

    @Operation(summary = "Returns the list of all zuordnungen of a schnittstelle")
    @GetMapping("/bySchnittstelle/{id}")
    @PreAuthorize("hasRole(T(de.muenchen.mobidam.security.AuthoritiesEnum).ADMIN.name())")
    public ResponseEntity<Iterable<ZuordnungDTO>> getAllById(@PathVariable String id) {
        List<ZuordnungDTO> personDTOList = new ArrayList<>();
        zuordnungService.getAllById(id)
                .forEach(task -> personDTOList.add(zuordnungMapper.toDTO(task)));
        personDTOList.sort(Comparator.comparing(ZuordnungDTO::getValidUntil));
        return new ResponseEntity<>(personDTOList, HttpStatus.OK);
    }

    @Operation(summary = "Deletes the zuordnung with the given ID")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole(T(de.muenchen.mobidam.security.AuthoritiesEnum).ADMIN.name())")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        return this.zuordnungService.deleteById(id) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
