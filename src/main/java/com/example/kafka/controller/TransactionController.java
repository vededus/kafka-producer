package com.example.kafka.controller;

import com.example.kafka.controller.request.TransactionRequest;
import com.example.kafka.service.KafkaTransactionProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController{
    private final KafkaTransactionProducerService kafkaTransactionProducerService;

    @Autowired
    public TransactionController(KafkaTransactionProducerService kafkaTransactionProducerService) {
        this.kafkaTransactionProducerService = kafkaTransactionProducerService;
    }

    @PostMapping
    public String create(@RequestBody TransactionRequest transactionRequest) {
        kafkaTransactionProducerService.send(transactionRequest);
        return "Transaction produced";
    }
}
