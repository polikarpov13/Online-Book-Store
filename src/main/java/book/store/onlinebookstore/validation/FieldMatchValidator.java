package book.store.onlinebookstore.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.beanutils.BeanUtils;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
    private String firstField;
    private String secondField;
    private String message;

    @Override
    public void initialize(FieldMatch constraintAnnotation) {
        firstField = constraintAnnotation.firstString();
        secondField = constraintAnnotation.secondString();
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        boolean valid = true;
        try {
            final Object firstObject = BeanUtils.getProperty(value, firstField);
            final Object secondObject = BeanUtils.getProperty(value, secondField);

            valid = firstObject == null && secondObject == null
                    || firstObject != null && firstObject.equals(secondObject);
        } catch (final Exception ex) {
            throw new RuntimeException("Error while validating "
                    + firstField + " and " + secondField, ex);
        }

        if (!valid) {
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(firstField)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }
        return valid;
    }
}
