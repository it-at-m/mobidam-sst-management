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

import de.muenchen.mobidam.domain.dtos.SchnittstelleCreateDTO;
import de.muenchen.mobidam.domain.dtos.SchnittstelleDTO;
import de.muenchen.mobidam.domain.dtos.SchnittstelleGetStatusDTO;
import de.muenchen.mobidam.service.SchnittstelleService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/schnittstelle")
public class SchnittstelleController {

    private final SchnittstelleService schnittstelleService;

    @Operation(summary = "Creates schnittstelle")
    @PostMapping
    @PreAuthorize("hasRole(T(de.muenchen.mobidam.security.AuthoritiesEnum).ADMIN.name())")
    public ResponseEntity<SchnittstelleDTO> create(@Valid @RequestBody SchnittstelleCreateDTO schnittstelleCreateDTO) {
        return new ResponseEntity<>(schnittstelleService.create(schnittstelleCreateDTO), HttpStatus.OK);
    }

    @Operation(summary = "Get first schnittstelle") // TODO: remove
    @GetMapping("1")
    @PreAuthorize("hasRole(T(de.muenchen.mobidam.security.AuthoritiesEnum).ADMIN.name())")
    public ResponseEntity<SchnittstelleDTO> getFirst() {
        return new ResponseEntity<>(schnittstelleService.getAll().iterator().next(), HttpStatus.OK);
    }

    @Operation(summary = "Get all schnittstelle")
    @GetMapping
    @PreAuthorize("hasRole(T(de.muenchen.mobidam.security.AuthoritiesEnum).ADMIN.name())")
    public ResponseEntity<Iterable<SchnittstelleDTO>> getAll() {
        return new ResponseEntity<>(schnittstelleService.getAll(), HttpStatus.OK);
    }

    @Operation(summary = "Get status of schnittstelle")
    @GetMapping("{id}/status")
    public ResponseEntity<?> getStatus(@PathVariable final String id) {
        Optional<SchnittstelleGetStatusDTO> schnittstelleDTO = schnittstelleService.getStatus(id);
        if (schnittstelleDTO.isPresent())
            return new ResponseEntity<>(schnittstelleDTO.get(), HttpStatus.OK);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(summary = "Deletes the schnittstelle with the given ID")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole(T(de.muenchen.mobidam.security.AuthoritiesEnum).ADMIN.name())")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        return this.schnittstelleService.deleteById(id) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Updates the schnittstelle")
    @PutMapping("/update")
    @PreAuthorize("hasRole(T(de.muenchen.mobidam.security.AuthoritiesEnum).ADMIN.name())")
    public ResponseEntity<?> update(@Valid @RequestBody SchnittstelleDTO schnittstelleDTO) {
        Optional<SchnittstelleDTO> updatedSchnittstelleDTO = schnittstelleService.update(schnittstelleDTO);
        if (updatedSchnittstelleDTO.isPresent())
            return new ResponseEntity<>(updatedSchnittstelleDTO.get(), HttpStatus.OK);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
