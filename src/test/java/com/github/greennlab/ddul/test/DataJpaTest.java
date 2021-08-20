package com.github.greennlab.ddul.test;

import com.github.greennlab.ddul.DDulJSR338Configuration;
import com.github.greennlab.ddul.DDulQuerydslConfiguration;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

@org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest(
    includeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = {
            DDulJSR338Configuration.class,
            DDulQuerydslConfiguration.class
        }
    )
)
@Sql(scripts = "classpath:ddul-schema.sql")
@TestMethodOrder(OrderAnnotation.class)
@SuppressWarnings("java:S2187")
public class DataJpaTest {

}
