package io.github.greennlab.ddul.test;

import io.github.greennlab.ddul.DDulJSR338Configuration;
import io.github.greennlab.ddul.DDulQuerydslConfiguration;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.annotation.Rollback;

@DataJpaTest(
    includeFilters =
    @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = {
            DDulJSR338Configuration.class,
            DDulQuerydslConfiguration.class
        }
    )
)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
@TestMethodOrder(OrderAnnotation.class)

@SuppressWarnings("java:S2187")
public class DataJpaDBTest {

}
