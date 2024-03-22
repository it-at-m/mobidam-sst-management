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

import com.fasterxml.jackson.databind.ObjectMapper;
import de.muenchen.mobidam.MicroServiceApplication;
import de.muenchen.mobidam.domain.Schnittstelle;
import de.muenchen.mobidam.domain.dtos.DatentransferCreateDTO;
import de.muenchen.mobidam.domain.enums.SchnittstellenStatus;
import de.muenchen.mobidam.repository.SchnittstelleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static de.muenchen.mobidam.TestConstants.SPRING_NO_SECURITY_PROFILE;
import static de.muenchen.mobidam.TestConstants.SPRING_TEST_PROFILE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(
        classes = { MicroServiceApplication.class },
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "spring.datasource.url=jdbc:h2:mem:mobidam-sst-management;DB_CLOSE_ON_EXIT=FALSE",
        }
)
@ActiveProfiles(profiles = { SPRING_TEST_PROFILE, SPRING_NO_SECURITY_PROFILE })
@AutoConfigureMockMvc
class DatentransferControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DatentransferController datentransferController;

    @Autowired
    private SchnittstelleRepository schnittstelleRepository;

    @Test
    @Transactional(propagation = Propagation.REQUIRED, noRollbackFor = Exception.class)
    void test_getBySchnittstelle() {

        ResponseEntity<?> datentransferDTOs = datentransferController.getBySchnittstelle(UUID.randomUUID().toString(), 0);

        assertEquals(HttpStatus.OK, datentransferDTOs.getStatusCode());
        assertNotNull(datentransferDTOs.getBody());
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRED, noRollbackFor = Exception.class)
    void test_createDatentransfer() {

        Schnittstelle schnittstelle = new Schnittstelle();
        schnittstelle.setName("test");
        schnittstelle.setAnlagedatum(LocalDate.now());
        schnittstelle.setStatus(SchnittstellenStatus.AKTIVIERT);
        schnittstelle = schnittstelleRepository.save(schnittstelle);

        DatentransferCreateDTO datentransferCreateDTO = new DatentransferCreateDTO();
        datentransferCreateDTO.setEreignis("FEHLER");
        datentransferCreateDTO.setInfo("test");
        datentransferCreateDTO.setZeitstempel(LocalDateTime.now());
        datentransferCreateDTO.setProzessId("process");
        datentransferCreateDTO.setSchnittstelle(schnittstelle.getId());
        ResponseEntity<?> datentransferDTO = datentransferController.createDatentransfer(datentransferCreateDTO);

        assertEquals(HttpStatus.OK, datentransferDTO.getStatusCode());
        assertNotNull(datentransferDTO.getBody());
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRED, noRollbackFor = Exception.class)
    void test_getBySchnittstelle_isBadRequest() throws Exception {

        mockMvc.perform(get("/api/datentransfer/1/0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    @Transactional(propagation = Propagation.REQUIRED, noRollbackFor = Exception.class)
    void test_createDatentransfer_isBadRequest() throws Exception {
        DatentransferCreateDTO datentransferCreateDTO = new DatentransferCreateDTO();
        datentransferCreateDTO.setEreignis("wrong-type");
        datentransferCreateDTO.setInfo("test");
        datentransferCreateDTO.setZeitstempel(LocalDateTime.now());
        datentransferCreateDTO.setProzessId("process");
        datentransferCreateDTO.setSchnittstelle(UUID.randomUUID());

        Map<String, Object> body = new HashMap<>();
        body.put("ereignis", "wrong-type");
        body.put("info", "test");
        body.put("zeitstempel", "2024-02-20T16:02:26.706Z");
        body.put("prozess", "test");
        body.put("schnittstelle", "1");

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/api/datentransfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isBadRequest());

    }
}
