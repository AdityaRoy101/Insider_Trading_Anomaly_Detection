package com.md.marketdataingestionservice.service;

import com.md.marketdataingestionservice.proto.TradeEventProto;
import com.md.marketdataingestionservice.publisher.TradeProducer;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IngestionService {

    private final TradeProducer tradeProducer;

    @Value("${finnhub.token}")
    private String apiToken;

    @Value("${finnhub.symbol}")
    private String symbol;

    @Value("${finnhub.path}")
    private String apiPath;

    private final Logger logger = LoggerFactory.getLogger(IngestionService.class);
    private final WebClient webClient = WebClient.create();

    @Scheduled(fixedRate = 1000)
    public void pollAndSend() {
        webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host("finnhub.io")
                        .path(apiPath)
                        .queryParam("symbol", symbol)
                        .queryParam("token", apiToken)
                        .build())
                .retrieve()
                .bodyToMono(FinnhubQuote.class)
                .subscribe(quote -> {
                    TradeEventProto.TradeEvent event = TradeEventProto.TradeEvent.newBuilder()
                            .setTradeId(UUID.randomUUID().toString())
                            .setSymbol(symbol)
                            .setPrice(quote.c())
                            .setVolume(500)
                            .setTimestamp(Instant.now().toString())
                            .build();
                    tradeProducer.send(event);
                });
    }
    private record FinnhubQuote(double c) {
    }
}
