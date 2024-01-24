/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2023
 */
package de.muenchen.mobidam.repository;

import de.muenchen.mobidam.MicroServiceApplication;
import de.muenchen.mobidam.domain.Datenstransfer;
import de.muenchen.mobidam.domain.Schnittstelle;
import de.muenchen.mobidam.domain.enums.EreignisTyp;
import de.muenchen.mobidam.domain.enums.SchnittstellenStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static de.muenchen.mobidam.TestConstants.SPRING_NO_SECURITY_PROFILE;
import static de.muenchen.mobidam.TestConstants.SPRING_TEST_PROFILE;
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
class DatentransferRepositoryTest {

    @Autowired
    private DatentransferRepository datentransferRepository;

    @Autowired
    private SchnittstelleRepository schnittstelleRepository;

    @Test
    @Transactional(propagation = Propagation.REQUIRED, noRollbackFor = Exception.class)
    void testSave() {

        // initialize
        Datenstransfer original = new Datenstransfer();
        Schnittstelle schnittstelle = new Schnittstelle();
        schnittstelle.setName("test");
        schnittstelle.setCreationDate(LocalDate.now());
        schnittstelle.setStatus(SchnittstellenStatus.AKTIVIERT);
        schnittstelle = schnittstelleRepository.save(schnittstelle);
        original.setSchnittstelle(schnittstelle);
        original.setEreignis(EreignisTyp.BEGINN);
        original.setInfo("Test");
        original.setZeitstempel(LocalDateTime.now());
        original.setProzessId("process-id");

        // persist
        original = datentransferRepository.save(original);

        // check
        Datenstransfer persisted = datentransferRepository.findById(original.getId()).orElse(null);
        assertNotNull(persisted);
        assertEquals(original, persisted);

        List<Datenstransfer> allDatentransfer = datentransferRepository.findDatenstransfersBySchnittstelleId(schnittstelle.getId());
        assertEquals(1, allDatentransfer.size());
        assertEquals("Test", allDatentransfer.get(0).getInfo());
    }

}
