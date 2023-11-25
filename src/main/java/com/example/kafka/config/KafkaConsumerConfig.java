package com.example.kafka.config;

import com.example.kafka.dto.Transaction;
import com.example.kafka.dto.TransactionEnriched;
import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.subject.RecordNameStrategy;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.schema-registry}")
    private String schemaRegistryServer;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Bean
    public DefaultKafkaConsumerFactory<Long, Transaction> transactionConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(getProps("Long"));
    }
    @Bean
    public DefaultKafkaConsumerFactory<Long, TransactionEnriched> transactionEnrichedConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(getProps("Long"));
    }


    private Map<String, Object> getProps(String keyType) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryServer);
        props.put(AbstractKafkaSchemaSerDeConfig.VALUE_SUBJECT_NAME_STRATEGY, RecordNameStrategy.class.getName());

        switch (keyType) {
            case "String" -> props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
            case "Long" -> props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
            case "Integer" -> props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
        }
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);

        return props;
    }

    @Bean("transactionListenerFactory")
    public ConcurrentKafkaListenerContainerFactory<Long, Transaction> kafkaTransactionListener() {
        ConcurrentKafkaListenerContainerFactory<Long, Transaction> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(transactionConsumerFactory());
        return factory;
    }

    @Bean("transactionEnrichedListenerFactory")
    public ConcurrentKafkaListenerContainerFactory<Long, TransactionEnriched> kafkaTransactionEnrichedListener() {
        ConcurrentKafkaListenerContainerFactory<Long, TransactionEnriched> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(transactionEnrichedConsumerFactory());
        return factory;
    }
}