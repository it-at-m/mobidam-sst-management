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
package de.muenchen.mobidam.validators;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.muenchen.mobidam.annotations.StartDateNotAfterEndDate;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class StartDateNotAfterEndDateTest {

    @StartDateNotAfterEndDate(startDate = "startDate", endDate = "endDate")
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
