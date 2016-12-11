package fr.esiee.pic.analytics;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;


/**
 * Demarrage de l'application
 * @author etudiant
 *
 */
@SpringBootApplication
@EnableScheduling
public class Application {
	
	static final Logger logger = LogManager.getLogger(Application.class.getName());
    
    /**
     * Main class
     */
    public static void main(String[] args) {
        logger.info("entered application");
        
        SpringApplication.run(Application.class, args);
    }
}



