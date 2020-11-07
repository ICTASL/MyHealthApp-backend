package lk.gov.govtech.covid19.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = ImageValidator.class)
public @interface AcceptableImage {
    String message() default "Insupportable type of image";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
