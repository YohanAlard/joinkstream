package statefull;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import statefull.model.RefGenre;
import statefull.serde.RefGenreSerde;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class RefGenreProducerDemo {
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    public static void main(String... args) {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, RefGenreSerde.class);

        DefaultKafkaProducerFactory<String, RefGenre> pf = new DefaultKafkaProducerFactory<>(props);
        KafkaTemplate<String, RefGenre> template = new KafkaTemplate<>(pf, true);
        String topic = "ref-genre-case-input";
        RefGenre genre1 = new RefGenre(1,"MALE");
        RefGenre genre2 = new RefGenre(2,"FEMALE");
        RefGenre genre3 = new RefGenre(3,"UNKNOW");
        template.send(topic, "H", genre1);
        template.send(topic, "F", genre2);
        template.send(topic, "ND", genre3);

    }
}
