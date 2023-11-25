package com.example.kafka.service;

import com.example.kafka.dto.User;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaUserProducerService {
    private final KafkaTemplate<String, User> kafkaUserTemplate;

    private final NewTopic userTopic;

    public KafkaUserProducerService(KafkaTemplate<String, User> kafkaUserTemplate, NewTopic userTopic) {
        this.kafkaUserTemplate = kafkaUserTemplate;
        this.userTopic = userTopic;
    }

    public void send(User user) {
        kafkaUserTemplate.send(userTopic.name(), user.getId(), user);
    }
}
