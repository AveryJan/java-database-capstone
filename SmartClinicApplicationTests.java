package com.project.back_end;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
@SpringBootTest(properties={"spring.datasource.url=jdbc:h2:mem:clinic;MODE=MySQL;DB_CLOSE_DELAY=-1","spring.datasource.driver-class-name=org.h2.Driver","spring.datasource.username=sa","spring.datasource.password=","spring.jpa.hibernate.ddl-auto=create-drop","app.jwt.secret=01234567890123456789012345678901","app.jwt.expiration-ms=3600000"})
@ActiveProfiles("test")
class SmartClinicApplicationTests { @Test void contextLoads() {} }
