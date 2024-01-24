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

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/datentransfer")
public class DatentransferController {

    private final DatentransferService datentransferService;

    @Operation(summary = "Get all Datentransfer LOGs for Schnittstelle")
    @GetMapping("/{schnittstelleId}")
    public ResponseEntity<?> getBySchnittstelle(@PathVariable String schnittstelleId) {
        Optional<Iterable<DatentransferDTO>> datentransferDTOS = datentransferService.getBySchnittstelle(schnittstelleId);
        if(datentransferDTOS.isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return new ResponseEntity<>(datentransferDTOS.get(), HttpStatus.OK);
    }
}
