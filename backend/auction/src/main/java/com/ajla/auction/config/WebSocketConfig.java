package com.ajla.auction.config;

import com.ajla.auction.model.HttpHandshakeInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // client app will subscribe messages at endpoints starting with these configured endpoint
        config.enableSimpleBroker("/queue", "/topic");
        // client will send messages at this endpoint
        // if client sends message at /app/message, the endpoint configured at /message in the spring controller will be invoked
        // @MessageMapping-annotated methods in controller class
        config.setApplicationDestinationPrefixes("/app");
        config.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // endpoint will be used by the client app to connect to STOMP
        registry.addEndpoint("/socket").addInterceptors(new HttpHandshakeInterceptor())
                .setAllowedOrigins("*")
                .withSockJS();
    }

}
