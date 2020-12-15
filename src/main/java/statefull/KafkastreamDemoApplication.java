package statefull;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.kstream.Joined;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import statefull.model.Person;
import statefull.model.PersonWithGenre;
import statefull.model.RefGenre;
import statefull.serde.PersonWithGenreSerde;

import java.util.function.BiFunction;

@SpringBootApplication
public class KafkastreamDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkastreamDemoApplication.class, args);
	}

	@Bean
	public BiFunction<KStream<String, Person>, KTable<String, RefGenre>, KStream<Long, PersonWithGenre>> joinKtable() {
		return (persons, referentielGenres) ->
				persons.selectKey((k,v) -> v.getCodeGenre())
						.join(referentielGenres,
								(person, genre) -> new PersonWithGenre(person.getNom(), person.getPrenom(),genre),
								Joined.with(Serdes.String(), new PersonWithGenreSerde(), null));
	}
}
