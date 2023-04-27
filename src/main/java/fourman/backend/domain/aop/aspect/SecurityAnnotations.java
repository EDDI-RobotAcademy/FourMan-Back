package fourman.backend.domain.aop.aspect;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class SecurityAnnotations {
    public enum UserType {
        MANAGER,
        CAFE,
        AUTHENTICATED,
        NOT_AUTHENTICATED
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface SecurityCheck {
        UserType value() default UserType.AUTHENTICATED;
    }
}