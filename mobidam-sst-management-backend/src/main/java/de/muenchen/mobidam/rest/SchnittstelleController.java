/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2024
 */
package de.muenchen.mobidam.rest;

import de.muenchen.mobidam.domain.dtos.SchnittstelleCreateDTO;
import de.muenchen.mobidam.domain.dtos.SchnittstelleDTO;
import de.muenchen.mobidam.service.SchnittstelleService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/schnittstelle")
public class SchnittstelleController {

    private final SchnittstelleService schnittstelleService;

    @Operation(summary = "Creates schnittstelle")
    @PostMapping
    public ResponseEntity<SchnittstelleDTO> create(@Valid @RequestBody SchnittstelleCreateDTO schnittstelleCreateDTO) {
        return new ResponseEntity<>(schnittstelleService.create(schnittstelleCreateDTO), HttpStatus.OK);
    }

    @Operation(summary = "Get all schnittstelle")
    @GetMapping
    public ResponseEntity<Iterable<SchnittstelleDTO>> getAll() {
        return new ResponseEntity<>(schnittstelleService.getAll(), HttpStatus.OK);
    }

    @Operation(summary = "Deletes the schnittstelle with the given ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        return this.schnittstelleService.deleteById(id) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Updates the schnittstelle")
    @PutMapping("/update")
    public ResponseEntity<?> update(@Valid @RequestBody SchnittstelleDTO schnittstelleDTO) {
        Optional<SchnittstelleDTO> updatedSchnittstelleDTO = schnittstelleService.update(schnittstelleDTO);
        if (updatedSchnittstelleDTO.isPresent())
            return new ResponseEntity<>(updatedSchnittstelleDTO.get(), HttpStatus.OK);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
