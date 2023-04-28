package it.unibz.archlab.digidojo.engagement.application.kafka;

import it.unibz.archlab.digidojo.engagement.application.dto.PostErasedEvent;
import it.unibz.archlab.digidojo.engagement.application.dto.PostWrittenEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Map;

@Configuration
public class KafkaConfiguration {

    @Value("${spring.kafka.properties.bootstrap.servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.properties.sasl.mechanism}")
    private String saslMechanism;

    @Value("${spring.kafka.properties.sasl.jaas.config}")
    private String saslJaasConfig;

    @Value("${spring.kafka.properties.security.protocol}")
    private String securityProtocol;

    @Value("${it.unibz.archlab.digidojo.engagement.kafka.consumer.group_id}")
    private String consumerGroupId;

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(Map.of(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
                "sasl.mechanism", saslMechanism,
                "sasl.jaas.config", saslJaasConfig,
                "security.protocol", securityProtocol,
                ProducerConfig.RETRIES_CONFIG, 0,
                ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432,
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class
        ));
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public Map<String, Object> baseConsumerProperties() {
        return Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
                "sasl.mechanism", saslMechanism,
                "sasl.jaas.config", saslJaasConfig,
                "security.protocol", securityProtocol,
                ConsumerConfig.GROUP_ID_CONFIG, consumerGroupId,
                ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false,
                ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 15000
        );
    }

    @Bean
    public ConsumerFactory<String, PostWrittenEvent> postWrittenEventConsumerFactory() {
        JsonDeserializer<PostWrittenEvent> jsonDeserializer = new JsonDeserializer<>(PostWrittenEvent.class);
        jsonDeserializer.addTrustedPackages("*");
        jsonDeserializer.setUseTypeMapperForKey(true);

        return new DefaultKafkaConsumerFactory<>(
                baseConsumerProperties(),
                new StringDeserializer(),
                jsonDeserializer
        );
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String,PostWrittenEvent>> postWrittenEventKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String,PostWrittenEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(postWrittenEventConsumerFactory());

        return factory;
    }

    @Bean
    public ConsumerFactory<String, PostErasedEvent> postErasedEventConsumerFactory() {
        JsonDeserializer<PostErasedEvent> jsonDeserializer = new JsonDeserializer<>(PostErasedEvent.class);
        jsonDeserializer.addTrustedPackages("*");
        jsonDeserializer.setUseTypeMapperForKey(true);

        return new DefaultKafkaConsumerFactory<>(
                baseConsumerProperties(),
                new StringDeserializer(),
                jsonDeserializer
        );
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String,PostErasedEvent>> postErasedEventKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, PostErasedEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(postErasedEventConsumerFactory());

        return factory;
    }
}