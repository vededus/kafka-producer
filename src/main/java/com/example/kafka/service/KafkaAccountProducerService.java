package com.example.kafka.service;

import com.example.kafka.dto.Account;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaAccountProducerService {
    private final KafkaTemplate<String, Account> kafkaAccountTemplate;

    private final NewTopic accountTopic;

    public KafkaAccountProducerService(KafkaTemplate<String, Account> kafkaAccountTemplate, NewTopic accountTopic) {
        this.kafkaAccountTemplate = kafkaAccountTemplate;
        this.accountTopic = accountTopic;
    }

    public void send(Account account) {
        kafkaAccountTemplate.send(accountTopic.name(), account.getAccountNumber(), account);
    }
}
