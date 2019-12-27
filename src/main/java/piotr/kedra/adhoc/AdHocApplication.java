package piotr.kedra.adhoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class AdHocApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdHocApplication.class, args);
    }

}
