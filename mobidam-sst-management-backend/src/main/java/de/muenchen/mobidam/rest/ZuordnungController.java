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
    public ResponseEntity<ZuordnungDTO> create(@Valid @RequestBody ZuordnungCreateDTO zuordnungCreateDTO) {
        System.out.println("hi!!!");
        System.out.println(zuordnungCreateDTO.getDepartment());
        return new ResponseEntity<>(zuordnungService.create(zuordnungCreateDTO), HttpStatus.OK);
    }

    @Operation(summary = "Returns the list of all zuordnungen of a schnittstelle")
    @GetMapping("/bySchnittstelle/{id}")
    public ResponseEntity<Iterable<ZuordnungDTO>> getAllById(@PathVariable String id) {
        List<ZuordnungDTO> personDTOList = new ArrayList<>();
        zuordnungService.getAllById(id)
                .forEach(task -> personDTOList.add(zuordnungMapper.toDTO(task)));
        personDTOList.sort(Comparator.comparing(ZuordnungDTO::getValidUntil));
        return new ResponseEntity<>(personDTOList, HttpStatus.OK);
    }

    @Operation(summary = "Deletes the zuordnung with the given ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        return this.zuordnungService.deleteById(id) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
