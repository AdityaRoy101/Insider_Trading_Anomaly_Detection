package com.md.marketdataingestionservice.publisher;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.md.marketdataingestionservice.proto.TradeEventProto;

@Service
@RequiredArgsConstructor
public class TradeProducer {

    private static final Logger log = LoggerFactory.getLogger(TradeProducer.class);
    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    public void send(TradeEventProto.TradeEvent event) {
        try {
            kafkaTemplate.send("trades", event.toByteArray());
            log.info("Sent event to kafka: {}", event);
        } catch (Exception e) {
            log.error("Failed to send kafka message", e);
        }
    }
}
