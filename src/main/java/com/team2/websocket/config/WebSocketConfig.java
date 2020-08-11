package com.team2.websocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /***
     * 注册一个websocket端点，客户端将使用它连接到我们的websocket服务器。
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/endpoint").setAllowedOrigins("*").withSockJS(); //@注解2
    }

    /***
     * 在configureMessageBroker方法中，我们配置一个消息代理，用于将消息从一个客户端路由到另一个客户端。
     *
     * 第一行定义了以“/app”开头的消息应该路由到消息处理方法（之后会定义这个方法）。
     *
     * 第二行定义了以“/topic”开头的消息应该路由到消息代理。消息代理向订阅特定主题的所有连接客户端广播消息。
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //注册广播消息代理和点对点代理
        registry.enableSimpleBroker("/topic", "/queue"); //@注解3
        //设置点对点代理订阅前缀
        registry.setUserDestinationPrefix("/queue"); //@注解4
    }
}