package com.hb.kafka.producer.controller;

import com.hb.kafka.producer.model.Message;
import com.hb.kafka.producer.service.MessageSender;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.status;

/**
 * Контроллер отправки сообщения
 */
@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SenderController
{
    MessageSender messageSender;

    /**
     * Отправка сообщения
     *
     * @param topicName название топика
     * @param message   сообщение
     * @return тело ответа
     */
    @PostMapping("/send")
    public ResponseEntity<String> send(@RequestParam(value = "topic") String topicName,
                                       @RequestBody Message message)
    {
        if (messageSender.send(message.getMessageText(), topicName))
        {
            return ResponseEntity.ok("ok");
        }
        return status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("kafka isn't available");
    }
}
