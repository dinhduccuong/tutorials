package jmaster.io.bootflight;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BootflightApplication {

    public static void main(String[] args) {
	SpringApplication.run(BootflightApplication.class, args);
    }

}
