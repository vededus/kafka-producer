package com.example.kafka.service;

import com.example.kafka.dto.Transaction;
import com.example.kafka.dto.TransactionEnriched;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaConsumer {
    @KafkaListener(topics = "transaction", containerFactory= "transactionListenerFactory")
    public void consumeTransactionTopic(ConsumerRecord<Long, Transaction> record) {
        log.info("Received transaction '{}'", record.key());
        log.info("Transaction value '{}'", record.value());
    }

    @KafkaListener(topics = "enrichedTopic", containerFactory= "transactionEnrichedListenerFactory")
    public void consumeEnrichedTopic(ConsumerRecord<Long, TransactionEnriched> record) {
        log.info("Received enriched transaction '{}'", record.key());
        log.info("Enriched transaction value '{}'", record.value());
    }

    @KafkaListener(topics = "filteredTopic", containerFactory= "transactionEnrichedListenerFactory")
    public void consumeFilteredTopic(ConsumerRecord<Long, TransactionEnriched> record) {
        log.info("Received filtered transaction '{}'", record.key());
        log.info("Enriched transaction value '{}'", record.value());
    }
}
