package com.example.kafka.config;

import com.example.kafka.dto.Account;
import com.example.kafka.dto.User;
import com.example.kafka.service.KafkaAccountProducerService;
import com.example.kafka.service.KafkaUserProducerService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Setup {
    private final KafkaAccountProducerService kafkaAccountProducerService;
    private final KafkaUserProducerService kafkaUserProducerService;

    public Setup(KafkaAccountProducerService kafkaAccountProducerService, KafkaUserProducerService kafkaUserProducerService) {
        this.kafkaAccountProducerService = kafkaAccountProducerService;
        this.kafkaUserProducerService = kafkaUserProducerService;
    }

    @PostConstruct
    public void setup(){
        log.info("--- Start setup ---");
        setupAccount();
        setupUser();
        log.info("--- End setup ---");
    }

    private void setupAccount(){
        log.info("Setup accounts");
        kafkaAccountProducerService.send(new Account("account1", "user1", "žiro"));
        kafkaAccountProducerService.send(new Account("account2", "user2", "žiro"));
        kafkaAccountProducerService.send(new Account("account3", "user3", "žiro"));
        kafkaAccountProducerService.send(new Account("account4", "user4", "žiro"));
        kafkaAccountProducerService.send(new Account("account5", "user5", "tekući"));
        kafkaAccountProducerService.send(new Account("account6", "user6", "tekući"));
        kafkaAccountProducerService.send(new Account("account7", "user1", "tekući"));
        kafkaAccountProducerService.send(new Account("account8", "user2", "štednja"));
    }

    private void setupUser() {
        log.info("Setup users");
        kafkaUserProducerService.send(new User("user1","Marko", "Markić"));
        kafkaUserProducerService.send(new User("user2","Ivo", "Ivić"));
        kafkaUserProducerService.send(new User("user3","Pero", "Perić"));
        kafkaUserProducerService.send(new User("user4","Ivan", "Horvat"));
        kafkaUserProducerService.send(new User("user5","Luka", "Lukić"));
        kafkaUserProducerService.send(new User("user6","Roko", "Rokić"));
    }
}
