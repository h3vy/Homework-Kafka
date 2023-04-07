package com.hb.kafka.producer.service;

import com.hb.kafka.producer.config.KafkaProducerConfig;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

/**
 * Сервис отправки сообщений
 */
@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageSender
{
    private static final Random RA = new Random();

    private static final List<String> KEYS = new ArrayList<>()
    {{
        add("FIRST");
        add("MAIN");
        add("MESSAGE");
    }};

    KafkaTemplate<String, String> template;

    /**
     * Отправка сообщения
     *
     * @param msg       сообщение
     * @param topicName название топика
     * @return удачная отправка/неудачная отправка
     */
    public boolean send(String msg, String topicName)
    {
        String key = KEYS.get(RA.nextInt(KEYS.size()));
        CompletableFuture<SendResult<String, String>> future = template.send(topicName, key, msg);
        try
        {
            SendResult<String, String> result = future.get();
            log.info("Successful send to {} by key {} with offset {} to partition {}", result.getProducerRecord().topic(),
                    result.getProducerRecord().key(),
                    result.getRecordMetadata().offset(),
                    result.getRecordMetadata().partition());
            return true;
        }
        catch (Exception e)
        {
            log.error("Cannot send message to Kafka Topic {}", KafkaProducerConfig.TOPIC_NAME);
        }
        return false;
    }
}
