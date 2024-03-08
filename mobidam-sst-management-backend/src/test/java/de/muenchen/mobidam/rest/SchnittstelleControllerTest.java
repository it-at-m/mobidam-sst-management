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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.muenchen.mobidam.MicroServiceApplication;

import java.time.LocalDate;
import java.util.UUID;

import static de.muenchen.mobidam.TestConstants.SPRING_TEST_PROFILE;
import static de.muenchen.mobidam.TestConstants.SPRING_NO_SECURITY_PROFILE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

@SpringBootTest(
        classes = { MicroServiceApplication.class },
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "spring.datasource.url=jdbc:h2:mem:mobidam-sst-management;DB_CLOSE_ON_EXIT=FALSE",
        }
)
@ActiveProfiles(profiles = { SPRING_TEST_PROFILE, SPRING_NO_SECURITY_PROFILE })
class SchnittstelleControllerTest {

    @Autowired
    private SchnittstelleController schnittstelleController;

    @Test
    @Transactional(propagation = Propagation.REQUIRED, noRollbackFor = Exception.class)
    void testCreate() {

        SchnittstelleCreateDTO schnittstelleCreateDTO = new SchnittstelleCreateDTO();
        schnittstelleCreateDTO.setBegruendung("Created test.");
        schnittstelleCreateDTO.setStatus("AKTIVIERT");
        schnittstelleCreateDTO.setName("test");

        ResponseEntity<SchnittstelleDTO> schnittstelleDTO = schnittstelleController.create(schnittstelleCreateDTO);

        assertEquals(HttpStatus.OK, schnittstelleDTO.getStatusCode());
        assertNotNull(schnittstelleDTO.getBody());
        assertNotNull(schnittstelleDTO.getBody().getId());
        assertEquals(LocalDate.now(), schnittstelleDTO.getBody().getAnlagedatum());
        assertEquals("test", schnittstelleDTO.getBody().getName());
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRED, noRollbackFor = Exception.class)
    void testUpdateSuccess() {

        SchnittstelleCreateDTO schnittstelleCreateDTO = new SchnittstelleCreateDTO();
        schnittstelleCreateDTO.setBegruendung("Created test.");
        schnittstelleCreateDTO.setStatus("AKTIVIERT");
        schnittstelleCreateDTO.setName("test");

        ResponseEntity<SchnittstelleDTO> schnittstelleDTO = schnittstelleController.create(schnittstelleCreateDTO);

        assertEquals(HttpStatus.OK, schnittstelleDTO.getStatusCode());
        assertNotNull(schnittstelleDTO.getBody());

        schnittstelleDTO.getBody().setAenderungsdatum(LocalDate.now());

        ResponseEntity<?> updatedSchnittstelleDTO = schnittstelleController.update(schnittstelleDTO.getBody());

        assertEquals(HttpStatus.OK, updatedSchnittstelleDTO.getStatusCode());
        assertNotNull(updatedSchnittstelleDTO.getBody());
        assertSame(updatedSchnittstelleDTO.getBody().getClass(), SchnittstelleDTO.class);

        SchnittstelleDTO updatedSchnittstelleDTOBody = (SchnittstelleDTO) updatedSchnittstelleDTO.getBody();
        assertEquals(LocalDate.now(), updatedSchnittstelleDTOBody.getAenderungsdatum());
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRED, noRollbackFor = Exception.class)
    void testUpdateFail() {

        SchnittstelleCreateDTO schnittstelleCreateDTO = new SchnittstelleCreateDTO();
        schnittstelleCreateDTO.setBegruendung("Created test.");
        schnittstelleCreateDTO.setStatus("AKTIVIERT");
        schnittstelleCreateDTO.setName("test");

        ResponseEntity<SchnittstelleDTO> schnittstelleDTO = schnittstelleController.create(schnittstelleCreateDTO);

        assertEquals(HttpStatus.OK, schnittstelleDTO.getStatusCode());
        assertNotNull(schnittstelleDTO.getBody());

        schnittstelleDTO.getBody().setId(UUID.randomUUID());

        ResponseEntity<?> updatedSchnittstelleDTO = schnittstelleController.update(schnittstelleDTO.getBody());

        assertEquals(HttpStatus.NOT_FOUND, updatedSchnittstelleDTO.getStatusCode());
        assertNull(updatedSchnittstelleDTO.getBody());
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRED, noRollbackFor = Exception.class)
    void testGetStatusSuccess() {

        SchnittstelleCreateDTO schnittstelleCreateDTO = new SchnittstelleCreateDTO();
        schnittstelleCreateDTO.setBegruendung("Created test.");
        schnittstelleCreateDTO.setStatus("AKTIVIERT");
        schnittstelleCreateDTO.setName("test");

        ResponseEntity<SchnittstelleDTO> schnittstelleDTO = schnittstelleController.create(schnittstelleCreateDTO);

        assertEquals(HttpStatus.OK, schnittstelleDTO.getStatusCode());
        assertNotNull(schnittstelleDTO.getBody());

        ResponseEntity<?> result = schnittstelleController.getStatus(schnittstelleDTO.getBody().getId().toString());

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(schnittstelleCreateDTO.getStatus(), ((SchnittstelleGetStatusDTO) result.getBody()).getStatus());
    }

}
