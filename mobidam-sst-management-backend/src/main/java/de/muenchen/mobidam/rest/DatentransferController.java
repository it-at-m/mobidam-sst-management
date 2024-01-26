package de.muenchen.mobidam.rest;

import de.muenchen.mobidam.domain.dtos.DatentransferDTO;
import de.muenchen.mobidam.service.DatentransferService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/datentransfer")
public class DatentransferController {

    private final DatentransferService datentransferService;

    @Operation(summary = "Get all Datentransfers for Schnittstelle")
    @GetMapping("/{schnittstelleId}/{page}")
    public ResponseEntity<Iterable<DatentransferDTO>> getBySchnittstelle(@PathVariable String schnittstelleId, @PathVariable int page) {
        return new ResponseEntity<>(datentransferService.getBySchnittstelle(schnittstelleId, page), HttpStatus.OK);
    }
}
