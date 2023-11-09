package de.muenchen.mobidam.rest;

import de.muenchen.mobidam.domain.dtos.ZuordnungCreateDTO;
import de.muenchen.mobidam.domain.dtos.ZuordnungDTO;
import de.muenchen.mobidam.domain.mappers.ZuordnungMapper;
import de.muenchen.mobidam.exceptions.SavingException;
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

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/zuordnung")
public class ZuordnungController {
    private final ZuordnungService zuordnungService;
    private final ZuordnungMapper zuordnungMapper;

    @Operation(summary = "Create an zuordnung")
    @PostMapping
    public ResponseEntity<ZuordnungDTO> create(@Valid @RequestBody ZuordnungCreateDTO zuordnungCreateDTO) {
        try {
            ZuordnungDTO savedZuordnung = zuordnungService.create(zuordnungCreateDTO);
            return new ResponseEntity<>(savedZuordnung, HttpStatus.OK);
        } catch (SavingException savingException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Get a list of zuordnung")
    @GetMapping
    public ResponseEntity<Iterable<ZuordnungDTO>> getAll() {
        List<ZuordnungDTO> personDTOList = new ArrayList<>();
        zuordnungService.findAll()
                .forEach(task -> personDTOList.add(zuordnungMapper.toDTO(task)));
        personDTOList.sort(Comparator.comparing(ZuordnungDTO::getValidUntil));
        return new ResponseEntity<>(personDTOList, HttpStatus.OK);
    }

    @Operation(summary = "Delete an zuordnung by its id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        return this.zuordnungService.deleteById(id) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
