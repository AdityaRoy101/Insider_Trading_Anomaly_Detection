package com.md.marketdataingestionservice.devtools;

import com.google.protobuf.InvalidProtocolBufferException;
import com.md.marketdataingestionservice.proto.TradeEventProto;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaTradeConsumer {

    @KafkaListener(topics = "trades", groupId = "test-consumer")
    public void consume(ConsumerRecord<String, byte[]> record) {
        try {
            TradeEventProto.TradeEvent event = TradeEventProto.TradeEvent.parseFrom(record.value());
            System.out.println("✅ Received Protobuf TradeEvent from Kafka:");
            System.out.println("   Trade ID:  " + event.getTradeId());
            System.out.println("   Symbol:    " + event.getSymbol());
            System.out.println("   Price:     " + event.getPrice());
            System.out.println("   Volume:    " + event.getVolume());
            System.out.println("   Timestamp: " + event.getTimestamp());
        } catch (InvalidProtocolBufferException e) {
            System.err.println("❌ Failed to parse Protobuf message: " + e.getMessage());
        }
    }
}