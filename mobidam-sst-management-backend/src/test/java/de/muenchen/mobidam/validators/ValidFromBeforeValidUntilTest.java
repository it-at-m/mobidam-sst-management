package de.muenchen.mobidam.validators;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.muenchen.mobidam.annotations.ValidFromBeforeValidUntil;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ValidFromBeforeValidUntilTest {

    @ValidFromBeforeValidUntil(startDate = "startDate", endDate = "endDate")
    private record TestClass(LocalDate startDate, LocalDate endDate) {
    }

    private static Validator validator;

    @BeforeAll
    public static void createValidator() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    void testIsValidWithValidObjects() {
        final LocalDate startDate = LocalDate.now();
        final LocalDate endDate = LocalDate.now().plusDays(1);

        final TestClass testObject = new TestClass(
                startDate,
                endDate);

        assertTrue(validator.validate(testObject).isEmpty());
    }

    @Test
    void testIsValidWithInvalidObjectThrowingExceptionInValidator() {
        final LocalDate startDate = LocalDate.now();
        final LocalDate endDate = null;

        final TestClass testObject = new TestClass(
                startDate,
                endDate);

        assertThrows(Exception.class, () -> validator.validate(testObject));
    }

    @Test
    void testIsValidWithInvalidObjects() {
        final LocalDate startDate = LocalDate.now().plusDays(7);
        final LocalDate endDate = LocalDate.now();

        final TestClass testObject = new TestClass(
                startDate,
                endDate);
        assertThrows(Exception.class, () -> validator.validate(testObject));
    }
}
