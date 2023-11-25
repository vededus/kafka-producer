package com.example.kafka.service;

import com.example.kafka.controller.request.TransactionRequest;
import com.example.kafka.dto.Transaction;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class KafkaTransactionProducerService {
    private final KafkaTemplate<Long, Transaction> kafkaTransactionTemplate;

    private final NewTopic transactionTopic;

    private final Random random;

    public KafkaTransactionProducerService(KafkaTemplate<Long, Transaction> kafkaTransactionTemplate, NewTopic transactionTopic) {
        this.kafkaTransactionTemplate = kafkaTransactionTemplate;
        this.transactionTopic = transactionTopic;
        this.random = new Random();
    }

    public void send(TransactionRequest transactionRequest) {
        Transaction transaction = createTransactionFromRequest(transactionRequest);
        kafkaTransactionTemplate.send(transactionTopic.name(), random.nextLong(), transaction);
    }

    private Transaction createTransactionFromRequest(TransactionRequest transactionRequest) {
        Transaction transaction = new Transaction();
        transaction.setFrom(transactionRequest.getFrom());
        transaction.setTo(transactionRequest.getTo());
        transaction.setAmount(transactionRequest.getAmount());
        return transaction;
    }
}
