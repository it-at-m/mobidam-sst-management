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
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller der Operationen auf Zuordnungen
 */
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/zuordnung")
public class ZuordnungController {
    private final ZuordnungService zuordnungService;
    private final ZuordnungMapper zuordnungMapper;

    @Operation(summary = "Erstellt eine Zuordnung")
    @PostMapping
    public ResponseEntity<ZuordnungDTO> create(@Valid @RequestBody ZuordnungCreateDTO zuordnungCreateDTO) {
        return new ResponseEntity<>(zuordnungService.create(zuordnungCreateDTO), HttpStatus.OK);
    }

    @Operation(summary = "Returned die Liste der aller Zuprdnungen einer Schnittstelle")
    @GetMapping("/bySchnittstelle/{id}")
    public ResponseEntity<Iterable<ZuordnungDTO>> getAllById(@PathVariable String id) {
        List<ZuordnungDTO> personDTOList = new ArrayList<>();
        zuordnungService.getAllById(id)
                .forEach(task -> personDTOList.add(zuordnungMapper.toDTO(task)));
        personDTOList.sort(Comparator.comparing(ZuordnungDTO::getValidUntil));
        return new ResponseEntity<>(personDTOList, HttpStatus.OK);
    }

    @Operation(summary = "Entfernt die Zuordnung mit der gegebenen ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        return this.zuordnungService.deleteById(id) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
