package nightclub.web.nightclub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NightClubApplication {

    public static void main(String[] args) {
        SpringApplication.run(NightClubApplication.class, args);
    }

}
