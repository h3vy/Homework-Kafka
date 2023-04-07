package com.hb.kafka.producer.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Конфигурация Kafka-Producer
 */
@Configuration
public class KafkaProducerConfig
{
    public static final String TOPIC_NAME = "kafka.example.topic";
    public static final String TOPIC_NAME_WITHOUT_PARTITION = "kafka.example.topic.without.partition";

    @Value("${spring.kafka.bootstrap-servers}")
    private String serverAddress;

    /**
     * Бин с partition
     *
     * @return топик
     */
    @Bean
    public NewTopic topicWithPartitions()
    {
        return TopicBuilder.name(TOPIC_NAME)
                .replicas(1)
                .partitions(10)
                .build();
    }

    /**
     * Бин без partition
     *
     * @return топик
     */
    @Bean
    public NewTopic topicWithoutPartitions()
    {
        return TopicBuilder.name(TOPIC_NAME_WITHOUT_PARTITION)
                .replicas(1)
                .build();
    }

    @Bean
    public ProducerFactory<String, String> producerFactory()
    {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, serverAddress);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaStringTemplate(ProducerFactory<String, String> producerFactory)
    {
        return new KafkaTemplate<>(producerFactory);
    }
}
