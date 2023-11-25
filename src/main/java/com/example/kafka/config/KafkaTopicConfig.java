package com.example.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    @Bean(name = "transactionTopic")
    public NewTopic producerTransactionTopic() {
        return TopicBuilder.name("transaction")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean(name = "accountTopic")
    public NewTopic producerAccountTopic() {
        return TopicBuilder.name("account")
                .partitions(1)
                .replicas(1)
                .compact()
                .build();
    }

    @Bean(name = "userTopic")
    public NewTopic producerUserTopic() {
        return TopicBuilder.name("user")
                .partitions(1)
                .replicas(1)
                .compact()
                .build();
    }

    @Bean(name = "filteredTopic")
    public NewTopic filteredTopic() {
        return TopicBuilder.name("filteredTopic")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean(name = "enrichedTopic")
    public NewTopic enrichedTopic() {
        return TopicBuilder.name("enrichedTopic")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
