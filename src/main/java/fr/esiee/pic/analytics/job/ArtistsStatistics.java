package fr.esiee.pic.analytics.job;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ArtistsStatistics {
	
	private static final Logger LOGGER = LogManager.getLogger(ArtistsStatistics.class);
	
	@Scheduled(cron = "*/10 * * * * *")
	public void countArtistByFirstLetter() {
		LOGGER.info("Start execution of JOB \"Count Artits By First Letter\"");
		LOGGER.info("End execution of JOB \"Count Artits By First Letter\"");
	}

}
