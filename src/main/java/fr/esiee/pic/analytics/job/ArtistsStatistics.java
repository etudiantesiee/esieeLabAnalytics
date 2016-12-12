package fr.esiee.pic.analytics.job;

import static com.datastax.spark.connector.japi.CassandraJavaUtil.mapRowTo;
import static com.datastax.spark.connector.japi.CassandraJavaUtil.mapToRow;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import scala.Tuple2;

import com.datastax.spark.connector.japi.CassandraJavaUtil;

import fr.esiee.pic.analytics.domain.ArtistByFirstLetter;
import fr.esiee.pic.analytics.domain.Statistics;

@Component
public class ArtistsStatistics implements Serializable {
	
	/**
	 * Serial version ID
	 */
	private static final long serialVersionUID = 4809428775486258647L;
	
	/**
	 * Logger
	 */
	private static final Logger LOGGER = LogManager.getLogger(ArtistsStatistics.class);
	
	@Scheduled(cron = "*/60 * * * * *")
	public void countArtistByFirstLetter() throws UnsupportedEncodingException {
		LOGGER.info("Start execution of JOB \"Count Artits By First Letter\"");
		
		// Chemin du jar courant
		String path = ArtistsStatistics.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		String decodedPath = URLDecoder.decode(path, "UTF-8");
		String jarPath = "/logiciels/esieeLabAnalytics/lib/esieeLabAnalytics-1.0.0-SNAPSHOT.jar"; //new File(decodedPath).getParentFile().getParentFile().getPath();
		LOGGER.info("Current jar path : {}", jarPath);
		String[] jars = {jarPath};
		
		// Lecture du contexte (cluster, spark master...) via les classes utilitaires DSE
		SparkConf conf = new SparkConf()
        						.setAppName("Count Artist By First Letter")
        						.setMaster("spark://127.0.0.1:7077")
        						.set("spark.cassandra.connection.host", "127.0.0.1");
		JavaSparkContext sc = new JavaSparkContext(conf);
		sc.addJar(jarPath);
		
		// Création du RDD correspondant à la table des artistes rangés par 1ere lettre 
		JavaRDD<ArtistByFirstLetter> artistByFirstLetterRdd = CassandraJavaUtil.javaFunctions(sc)
                .cassandraTable("playlist_comments", "artists_by_first_letter", mapRowTo(ArtistByFirstLetter.class));
		
		// Création pair <lettre du nom, 1>
		PairFunction<ArtistByFirstLetter, String, Integer> letterPairsFunction = new PairFunction<ArtistByFirstLetter, String, Integer>() {
			private static final long serialVersionUID = 1838112644014956949L;
			public Tuple2<String, Integer> call(ArtistByFirstLetter artist) { return new Tuple2<String, Integer>(artist.getFirstLetter(), 1); }
		};
		JavaPairRDD<String, Integer> letterPairs = artistByFirstLetterRdd.mapToPair(letterPairsFunction);
		
		// Phase de reduce pour comptabiliser
		Function2<Integer, Integer, Integer> countsArtistByFirstLetterRddFunction = new Function2<Integer, Integer, Integer>() {
			private static final long serialVersionUID = -3195087448899432717L;
			public Integer call(Integer a, Integer b) { return a + b; }
		};
		JavaPairRDD<String, Integer> countsArtistByFirstLetterRdd = letterPairs.reduceByKey(countsArtistByFirstLetterRddFunction);
		
		LOGGER.info("*************** Résultat : {}", countsArtistByFirstLetterRdd.collect());
		
		
		// Création du RDD de stats
		Function<Tuple2<String, Integer>, Statistics> statsFunction = new Function<Tuple2<String, Integer>, Statistics>() {
			private static final long serialVersionUID = -5866252527612848695L;
			public Statistics call(Tuple2<String, Integer> currentLetterCount) throws Exception {
				return new Statistics("Artist with " + currentLetterCount._1 , currentLetterCount._2);
			}
		};
		JavaRDD<Statistics> stats = countsArtistByFirstLetterRdd.map(statsFunction);
		
		// Enrégistrement dans Cassandra
		CassandraJavaUtil.javaFunctions(stats)
							.writerBuilder("playlist_comments", "statistics", mapToRow(Statistics.class))
							.saveToCassandra();
		
		LOGGER.info("End execution of JOB \"Count Artits By First Letter\"");
	}

}
