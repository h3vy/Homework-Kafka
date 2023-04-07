package com.hb.kafka.consumer.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

/**
 * Сервис Kafka-Consumer
 */
@Slf4j
@Service
public class KafkaConsumer
{

    public static final String TOPIC_NAME = "kafka.example.topic";

    /**
     * Слушатель
     *
     * @param message           сообщение
     * @param receiveTopic      топик
     * @param receivedPartition partition
     */
    @KafkaListener(topics = TOPIC_NAME)
    public void consumer(@Payload String message,
                         @Header(KafkaHeaders.TOPIC) String receiveTopic,
                         @Header(KafkaHeaders.RECEIVED_PARTITION) String receivedPartition)
    {
        log.info("Receive from Topic {} by partition {}: {}", receiveTopic, receivedPartition, message);
    }

    /**
     * Слушатель
     *
     * @param message сообщение
     */
    @KafkaListener(topics = {TOPIC_NAME + ".without.partition"})
    public void messageTopicWithoutPartition(String message)
    {
        log.info("Receive new message! {}", message);
    }
}
