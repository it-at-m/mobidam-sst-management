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

import de.muenchen.mobidam.domain.dtos.DatentransferDTO;
import de.muenchen.mobidam.service.DatentransferService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Optional;
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

    @Operation(summary = "Get first Datentransfer for Schnittstelle, where type is not BEGINN or ENDE")
    @GetMapping("/notBeginnEnde/{schnittstelleId}")
    public ResponseEntity<?> getBySchnittstelleWithoutBeginnAndEnde(@PathVariable String schnittstelleId) {
        Optional<DatentransferDTO> datentransferDTO = datentransferService.getFirstBySchnittstelleWithoutBeginnOrEnde(schnittstelleId);
        if (datentransferDTO.isPresent())
            return new ResponseEntity<>(datentransferDTO.get(), HttpStatus.OK);
        return ResponseEntity.notFound().build();
    }
}
