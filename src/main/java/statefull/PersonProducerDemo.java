package statefull;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.*;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import statefull.model.Person;
import statefull.serde.PersonSerde;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class PersonProducerDemo {

    public static void main(String... args) {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, PersonSerde.class);

        DefaultKafkaProducerFactory<Integer, Person> pf = new DefaultKafkaProducerFactory<>(props);
        KafkaTemplate<Integer, Person> template = new KafkaTemplate<>(pf, true);
        String topic = "person-case-input";
        Person person1 = new Person("Dupontel", "albert", "H");
        Person person2 = new Person("Page", "Elliot", "ND");
        Person person3 = new Person("Watson", "Emma", "F");
        template.send(topic,4, person1);
        template.send(topic,5, person2);
        template.send(topic,6, person3);
    }
}
