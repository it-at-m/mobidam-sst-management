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

import de.muenchen.mobidam.annotations.StartDateNotAfterEndDate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ValidationException;
import java.lang.reflect.Method;
import java.time.LocalDate;

/**
 * Validation of a binary date correlation
 * The start date must not be after the end date
 */
public class StartDateNotAfterEndDateValidator implements ConstraintValidator<StartDateNotAfterEndDate, Object> {
    private String startDateFieldName;
    private String endDateFieldName;

    private String message;

    @Override
    public void initialize(final StartDateNotAfterEndDate constraintAnnotation) {
        startDateFieldName = constraintAnnotation.startDate();
        endDateFieldName = constraintAnnotation.endDate();
        message = constraintAnnotation.message();
    }

    /**
     * @param o Object to be tested
     * @param constraintValidatorContext triggering context
     * @return false if validation failed, otherwise true
     */
    @Override
    public boolean isValid(final Object o, final ConstraintValidatorContext constraintValidatorContext) {
        try {
            if (o == null) {
                return true;
            }
            final Method startDateGetter = o.getClass().getMethod(startDateFieldName);
            final LocalDate startDate = (LocalDate) startDateGetter.invoke(o);

            final Method endDateGetter = o.getClass().getMethod(endDateFieldName);
            final LocalDate endDate = (LocalDate) endDateGetter.invoke(o);
            if (endDate.isBefore(startDate)) {
                throw new ValidationException(message);
            } else {
                return true;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
