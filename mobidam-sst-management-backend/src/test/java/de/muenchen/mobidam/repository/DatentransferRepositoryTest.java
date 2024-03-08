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
package de.muenchen.mobidam.repository;

import de.muenchen.mobidam.MicroServiceApplication;
import de.muenchen.mobidam.domain.Datentransfer;
import de.muenchen.mobidam.domain.Schnittstelle;
import de.muenchen.mobidam.domain.enums.EreignisTyp;
import de.muenchen.mobidam.domain.enums.SchnittstellenStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static de.muenchen.mobidam.TestConstants.SPRING_NO_SECURITY_PROFILE;
import static de.muenchen.mobidam.TestConstants.SPRING_TEST_PROFILE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        Datentransfer original = new Datentransfer();
        Schnittstelle schnittstelle = new Schnittstelle();
        schnittstelle.setName("test");
        schnittstelle.setAnlagedatum(LocalDate.now());
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
        Datentransfer persisted = datentransferRepository.findById(original.getId()).orElse(null);
        assertNotNull(persisted);
        assertEquals(original, persisted);

        List<Datentransfer> allDatentransfer = datentransferRepository.findDatenstransfersBySchnittstelleIdOrderByZeitstempelDesc(schnittstelle.getId(),
                Pageable.ofSize(10));
        assertEquals(1, allDatentransfer.size());
        assertEquals("Test", allDatentransfer.get(0).getInfo());
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRED, noRollbackFor = Exception.class)
    void test_findFirstBySchnittstelleIdWithSingleResultState() {

        // initialize
        Datentransfer originalBeginn = new Datentransfer();
        Schnittstelle schnittstelle = new Schnittstelle();
        schnittstelle.setName("test");
        schnittstelle.setAnlagedatum(LocalDate.now());
        schnittstelle.setStatus(SchnittstellenStatus.AKTIVIERT);
        schnittstelle = schnittstelleRepository.save(schnittstelle);
        originalBeginn.setSchnittstelle(schnittstelle);
        originalBeginn.setEreignis(EreignisTyp.BEGINN);
        originalBeginn.setInfo("Test");
        originalBeginn.setZeitstempel(LocalDateTime.now());
        originalBeginn.setProzessId("process-id");

        // persist
        datentransferRepository.save(originalBeginn);

        Datentransfer originalFehler = new Datentransfer();
        originalFehler.setSchnittstelle(schnittstelle);
        originalFehler.setEreignis(EreignisTyp.FEHLER);
        originalFehler.setInfo("Test");
        originalFehler.setZeitstempel(LocalDateTime.now());
        originalFehler.setProzessId("process-id");

        // persist
        datentransferRepository.save(originalFehler);

        // check
        Optional<Datentransfer> datentransfer = datentransferRepository.findFirstBySchnittstelleIdAndEreignisIsNotInOrderByZeitstempelDesc(
                schnittstelle.getId(),
                List.of(EreignisTyp.BEGINN, EreignisTyp.ENDE));
        assertTrue(datentransfer.isPresent());
        assertEquals(EreignisTyp.FEHLER, datentransfer.get().getEreignis());
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRED, noRollbackFor = Exception.class)
    void test_findFirstBySchnittstelleIdWithMultipleResultStates() {

        // initialize
        Datentransfer originalWarnung = new Datentransfer();
        Schnittstelle schnittstelle = new Schnittstelle();
        schnittstelle.setName("test");
        schnittstelle.setAnlagedatum(LocalDate.now());
        schnittstelle.setStatus(SchnittstellenStatus.AKTIVIERT);
        schnittstelle = schnittstelleRepository.save(schnittstelle);
        originalWarnung.setSchnittstelle(schnittstelle);
        originalWarnung.setEreignis(EreignisTyp.WARNUNGEN);
        originalWarnung.setInfo("Test");
        originalWarnung.setZeitstempel(LocalDateTime.now());
        originalWarnung.setProzessId("process-id");

        // persist
        datentransferRepository.save(originalWarnung);

        Datentransfer originalFehler = new Datentransfer();
        originalFehler.setSchnittstelle(schnittstelle);
        originalFehler.setEreignis(EreignisTyp.FEHLER);
        originalFehler.setInfo("Test");
        originalFehler.setZeitstempel(LocalDateTime.now().minusHours(10));
        originalFehler.setProzessId("process-id");

        // persist
        datentransferRepository.save(originalFehler);

        // check
        Optional<Datentransfer> datentransfer = datentransferRepository.findFirstBySchnittstelleIdAndEreignisIsNotInOrderByZeitstempelDesc(
                schnittstelle.getId(),
                List.of(EreignisTyp.BEGINN, EreignisTyp.ENDE));
        assertTrue(datentransfer.isPresent());
        assertEquals(EreignisTyp.WARNUNGEN, datentransfer.get().getEreignis());
    }

}
