package com.jack.applications.websockets.config;

import com.jack.applications.websockets.handlers.SelectionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebConfig implements WebSocketConfigurer {

    private final static String SELECTION_ENDPOINT = "/selection";

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry
                .addHandler(getSelectionWebSocketHandler(), SELECTION_ENDPOINT)
                .setAllowedOrigins("*");
    }

    @Bean
    public SelectionHandler getSelectionWebSocketHandler() {
        return new SelectionHandler();
    }

}