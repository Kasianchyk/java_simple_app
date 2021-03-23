package org.example.app;

import java.lang.annotation.*;
import org.junit.jupiter.api.extension.ExtendWith;

@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ExtendWith(TestRailExecutionCondition.class)
public @interface TestRail {
}
