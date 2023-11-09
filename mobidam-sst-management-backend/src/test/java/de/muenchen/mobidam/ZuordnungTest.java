package de.muenchen.mobidam;

import java.time.LocalDate;

import de.muenchen.mobidam.domain.Zuordnung;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class ZuordnungTest {

    @Test
    public void validFromInThePast() {
        LocalDate start = LocalDate.now().minusDays(7);

        Zuordnung zuordnung = new Zuordnung();

        assertThrows(ValidationException.class, () -> zuordnung.setValidFrom(start));
    }

    @Test
    public void validUntilInThePast() {
        LocalDate end = LocalDate.now().minusDays(1);

        Zuordnung zuordnung = new Zuordnung();

        assertThrows(ValidationException.class, () -> zuordnung.setValidFrom(end));
    }

}
