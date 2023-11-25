package com.example.kafka.config;

import com.example.kafka.dto.Account;
import com.example.kafka.dto.Transaction;
import com.example.kafka.dto.User;
import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.serializers.subject.RecordNameStrategy;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * /opt/bitnami/kafka/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --consumer.config /opt/bitnami/kafka/config/consumer.properties --topic account --from-beginning
 */
@Configuration
public class KafkaProducerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.schema-registry}")
    private String schemaRegistryServer;

    @Bean
    public ProducerFactory<Long, Transaction> transactionProducerFactory() {
        return new DefaultKafkaProducerFactory<>(getProps("Long"));
    }

    @Bean
    public ProducerFactory<String, Account> accountProducerFactory() {
        return new DefaultKafkaProducerFactory<>(getProps("String"));
    }

    @Bean
    public ProducerFactory<String, User> userProducerFactory() {
        return new DefaultKafkaProducerFactory<>(getProps("String"));
    }

    @Bean
    public KafkaTemplate<Long, Transaction> kafkaTransactionTemplate(
            ProducerFactory<Long, Transaction> transactionProducerFactory) {
        return new KafkaTemplate<>(transactionProducerFactory);
    }

    @Bean
    public KafkaTemplate<String, Account> kafkaAccountTemplate(
            ProducerFactory<String, Account> accountProducerFactory) {
        return new KafkaTemplate<>(accountProducerFactory);
    }

    @Bean
    public KafkaTemplate<String, User> kafkaUserTemplate(
            ProducerFactory<String, User> userProducerFactory) {
        return new KafkaTemplate<>(userProducerFactory);
    }

    private Map<String, Object> getProps(String keyType) {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryServer);
        props.put(AbstractKafkaSchemaSerDeConfig.VALUE_SUBJECT_NAME_STRATEGY, RecordNameStrategy.class.getName());
        props.put(AbstractKafkaSchemaSerDeConfig.USE_LATEST_VERSION, true);
        props.put(AbstractKafkaSchemaSerDeConfig.LATEST_COMPATIBILITY_STRICT, false);
        switch (keyType) {
            case "String" -> props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            case "Long" -> props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
            case "Integer" -> props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
        }
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        props.put(AbstractKafkaSchemaSerDeConfig.AUTO_REGISTER_SCHEMAS, false);

        return props;
    }
}
