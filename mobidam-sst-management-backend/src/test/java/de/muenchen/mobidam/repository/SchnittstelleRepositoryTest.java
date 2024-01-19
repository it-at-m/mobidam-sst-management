/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2023
 */
package de.muenchen.mobidam.repository;

import de.muenchen.mobidam.domain.Schnittstelle;
import de.muenchen.mobidam.domain.enums.SchnittstellenStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

@SpringBootTest(
        classes = { MicroServiceApplication.class },
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "spring.datasource.url=jdbc:h2:mem:mobidam-sst-management;DB_CLOSE_ON_EXIT=FALSE",
        }
)
@ActiveProfiles(profiles = { SPRING_TEST_PROFILE, SPRING_NO_SECURITY_PROFILE })
class SchnittstelleRepositoryTest {

    @Autowired
    private SchnittstelleRepository repository;

    @Test
    @Transactional(propagation = Propagation.REQUIRED, noRollbackFor = Exception.class)
    void testSave() {

        // initialize
        Schnittstelle schnittstelle = new Schnittstelle();
        schnittstelle.setName("test");
        schnittstelle.setCreationDate(LocalDate.now());
        schnittstelle.setStatus(SchnittstellenStatus.AKTIVIERT);
        schnittstelle.setId(UUID.randomUUID());

        // persist
        schnittstelle = repository.save(schnittstelle);

        // check
        Schnittstelle persisted = repository.findById(schnittstelle.getId()).orElse(null);
        assertNotNull(persisted);
        assertEquals(schnittstelle, persisted);
    }

}
