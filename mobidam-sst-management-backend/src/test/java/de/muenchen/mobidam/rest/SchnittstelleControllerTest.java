/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2024
 */
package de.muenchen.mobidam.rest;

import de.muenchen.mobidam.domain.dtos.SchnittstelleCreateDTO;
import de.muenchen.mobidam.domain.dtos.SchnittstelleDTO;
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
        schnittstelleCreateDTO.setExplanation("Created test.");
        schnittstelleCreateDTO.setStatus("AKTIVIERT");
        schnittstelleCreateDTO.setName("test");

        ResponseEntity<SchnittstelleDTO> schnittstelleDTO = schnittstelleController.create(schnittstelleCreateDTO);

        assertEquals(HttpStatus.OK, schnittstelleDTO.getStatusCode());
        assertNotNull(schnittstelleDTO.getBody());
        assertNotNull(schnittstelleDTO.getBody().getId());
        assertEquals(LocalDate.now(), schnittstelleDTO.getBody().getCreationDate());
        assertEquals("test", schnittstelleDTO.getBody().getName());
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRED, noRollbackFor = Exception.class)
    void testUpdateSuccess() {

        SchnittstelleCreateDTO schnittstelleCreateDTO = new SchnittstelleCreateDTO();
        schnittstelleCreateDTO.setExplanation("Created test.");
        schnittstelleCreateDTO.setStatus("AKTIVIERT");
        schnittstelleCreateDTO.setName("test");

        ResponseEntity<SchnittstelleDTO> schnittstelleDTO = schnittstelleController.create(schnittstelleCreateDTO);

        assertEquals(HttpStatus.OK, schnittstelleDTO.getStatusCode());
        assertNotNull(schnittstelleDTO.getBody());

        schnittstelleDTO.getBody().setEditDate(LocalDate.now());

        ResponseEntity<?> updatedSchnittstelleDTO = schnittstelleController.update(schnittstelleDTO.getBody());

        assertEquals(HttpStatus.OK, updatedSchnittstelleDTO.getStatusCode());
        assertNotNull(updatedSchnittstelleDTO.getBody());
        assertSame(updatedSchnittstelleDTO.getBody().getClass(), SchnittstelleDTO.class);

        SchnittstelleDTO updatedSchnittstelleDTOBody = (SchnittstelleDTO) updatedSchnittstelleDTO.getBody();
        assertEquals(LocalDate.now(), updatedSchnittstelleDTOBody.getEditDate());
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRED, noRollbackFor = Exception.class)
    void testUpdateFail() {

        SchnittstelleCreateDTO schnittstelleCreateDTO = new SchnittstelleCreateDTO();
        schnittstelleCreateDTO.setExplanation("Created test.");
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

}
