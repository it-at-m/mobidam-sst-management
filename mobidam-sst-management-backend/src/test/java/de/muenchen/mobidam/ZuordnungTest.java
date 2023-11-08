package de.muenchen.mobidam;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.UUID;

import de.muenchen.mobidam.domain.Zuordnung;
import de.muenchen.mobidam.domain.dtos.ZuordnungCreateDTO;
import de.muenchen.mobidam.domain.dtos.ZuordnungDTO;
import de.muenchen.mobidam.domain.mappers.ZuordnungMapper;
import de.muenchen.mobidam.repository.ZuordnungRepository;
import de.muenchen.mobidam.service.ZuordnungService;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class ZuordnungTest {
    @Spy
    private ZuordnungMapper mapper = Mappers.getMapper(ZuordnungMapper.class);
    @Mock
    private ZuordnungRepository repository;
    @InjectMocks
    private ZuordnungService service;

    @Test
    public void testCreateZuordnung() {
        String schnittstelle = "Schnittstelle";
        String userID = UUID.randomUUID().toString();
        String department = "Department";
        String functionAddress = "Address";
        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.now().plusDays(7);

        ZuordnungCreateDTO createDTO = ZuordnungCreateDTO.builder()
                .schnittstelle(schnittstelle)
                .userID(userID)
                .department(department)
                .functionAddress(functionAddress)
                .validFrom(start)
                .validUntil(end)
                .build();

        Zuordnung zuordnung = new Zuordnung();
        zuordnung.setId(UUID.randomUUID());
        zuordnung.setSchnittstelle(schnittstelle);
        zuordnung.setDepartment(department);
        zuordnung.setFunctionAddress(functionAddress);
        zuordnung.setUserID(userID);
        zuordnung.setValidFrom(start);
        zuordnung.setValidUntil(end);

        when(repository.save(any(Zuordnung.class))).thenReturn(zuordnung);

        ZuordnungDTO dto = service.create(createDTO);

        assertNotNull(dto);
    }

    @Test
    public void validFromInThePast(){
        LocalDate start = LocalDate.now().minusDays(7);

        Zuordnung zuordnung = new Zuordnung();

        assertThrows(ValidationException.class,() -> zuordnung.setValidFrom(start));
    }

    @Test
    public void validUntilInThePast(){
        LocalDate end = LocalDate.now().minusDays(1);

        Zuordnung zuordnung = new Zuordnung();

        assertThrows(ValidationException.class,() -> zuordnung.setValidFrom(end));
    }

}
