/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2023
 */
package de.muenchen.mobidam.rest;

import de.muenchen.mobidam.MicroServiceApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static de.muenchen.mobidam.TestConstants.SPRING_NO_SECURITY_PROFILE;
import static de.muenchen.mobidam.TestConstants.SPRING_TEST_PROFILE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(
        classes = { MicroServiceApplication.class },
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "spring.datasource.url=jdbc:h2:mem:mobidam-sst-management;DB_CLOSE_ON_EXIT=FALSE",
        }
)
@ActiveProfiles(profiles = { SPRING_TEST_PROFILE, SPRING_NO_SECURITY_PROFILE })
class DatentransferControllerTest {

    @Autowired
    private DatentransferController datentransferController;

    @Test
    @Transactional(propagation = Propagation.REQUIRED, noRollbackFor = Exception.class)
    void testSuccess() {

        ResponseEntity<?> datentransferDTOs = datentransferController.getBySchnittstelle(UUID.randomUUID().toString(), 0);

        assertEquals(HttpStatus.OK, datentransferDTOs.getStatusCode());
        assertNotNull(datentransferDTOs.getBody());
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRED, noRollbackFor = Exception.class)
    void testException() {

        ResponseEntity<?> datentransferDTOs = datentransferController.getBySchnittstelle("1", 0);

        assertEquals(HttpStatus.BAD_REQUEST, datentransferDTOs.getStatusCode());
        assertNull(datentransferDTOs.getBody());
    }
}
