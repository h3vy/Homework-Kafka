package com.hb.kafka.producer.model;

import lombok.Data;

import java.io.Serializable;

/**
 * Сущность сообщения
 */
@Data
public class Message implements Serializable
{
    private String messageText;
}
