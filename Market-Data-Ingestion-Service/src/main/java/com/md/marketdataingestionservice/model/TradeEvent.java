package com.md.marketdataingestionservice.model;

import java.time.Instant;
import java.util.UUID;

public record TradeEvent(
        String tradeId,
        String symbol,
        double price,
        int volume,
        Instant timestamp
) {
    public static TradeEvent of(
            String symbol,
            double price,
            int volume
    ) {
        return new TradeEvent(
                UUID.randomUUID().toString(),
                symbol,
                price,
                volume,
                Instant.now()
        );
    }
}
