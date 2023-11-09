package de.muenchen.mobidam.validators;

import de.muenchen.mobidam.annotations.ValidFromBeforeValidUntil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ValidationException;
import java.lang.reflect.Method;
import java.time.LocalDate;

public class ValidFromBeforeValidUntilValidator implements ConstraintValidator<ValidFromBeforeValidUntil, Object> {
    private String startDateFieldName;
    private String endDateFieldName;

    private String message;

    @Override
    public void initialize(final ValidFromBeforeValidUntil constraintAnnotation) {
        startDateFieldName = constraintAnnotation.startDate();
        endDateFieldName = constraintAnnotation.endDate();
        message = constraintAnnotation.message();
    }

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
