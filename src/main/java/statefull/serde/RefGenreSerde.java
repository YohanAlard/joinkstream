package statefull.serde;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.beans.factory.annotation.Configurable;
import statefull.model.RefGenre;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@Configurable
public class RefGenreSerde implements Serializer<RefGenre>, Deserializer<JsonNode>, Serde {


    @Override
    public byte[] serialize(String s, RefGenre refGenre) {
        byte[] messageSer = null;
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        mapper.registerModule(new Jdk8Module());
        try {
            messageSer = mapper.writeValueAsString(refGenre).getBytes();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return messageSer;
    }

    @Override
    public byte[] serialize(String topic, Headers headers, RefGenre refGenre) {
        return serialize(topic, refGenre);
    }

    @Override
    public JsonNode deserialize(String s, byte[] bytes) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        java.util.List<String> values = new ArrayList<>();
        JsonNode jsonNodeRoot = null;
        try {
            jsonNodeRoot = mapper.readTree(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return jsonNodeRoot;
    }

    @Override
    public JsonNode deserialize(String topic, Headers headers, byte[] data) {
        return deserialize(topic, data);
    }

    @Override
    public void close() {
    }

    @Override
    public Serializer serializer() {
        return new RefGenreSerde();
    }

    @Override
    public Deserializer deserializer() {
        return new RefGenreSerde();
    }

    @Override
    public void configure(Map configs, boolean isKey) {
    }
}

