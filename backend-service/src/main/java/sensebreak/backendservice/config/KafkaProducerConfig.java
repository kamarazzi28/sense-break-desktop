package sensebreak.backendservice.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonSerializer;
import sensebreak.backendservice.model.NotificationMessage;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration class for Kafka producer.
 * Sets up the necessary beans to produce messages to a Kafka topic.
 */
@Configuration
public class KafkaProducerConfig {

    /**
     * Creates a ProducerFactory for producing {@link NotificationMessage} objects.
     *
     * @return a configured ProducerFactory
     */
    @Bean
    public ProducerFactory<String, NotificationMessage> producerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(config);
    }

    /**
     * Creates a KafkaTemplate for sending {@link NotificationMessage} objects.
     *
     * @return a configured KafkaTemplate
     */
    @Bean
    public KafkaTemplate<String, NotificationMessage> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
