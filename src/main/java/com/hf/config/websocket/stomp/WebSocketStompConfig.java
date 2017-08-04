package com.hf.config.websocket.stomp;

import com.hf.config.GlobalConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

@Configuration
@EnableWebSocketMessageBroker
@ComponentScan(basePackages = {GlobalConfig.BASEWEBSOCKETPACKAGES})	//扫描websocket,不能从webconfig里面扫描
public class WebSocketStompConfig extends
		AbstractWebSocketMessageBrokerConfigurer {

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("stompendpoint").withSockJS(); // 端点。不是消息目的地
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		// registry.enableSimpleBroker("/queue", "/topic"); // 简单代理 模拟stomp消息

		registry.enableStompBrokerRelay("/queue", "/topic")
				.setRelayHost("10.10.3.118").setRelayPort(61613)
				.setVirtualHost("/admin")
				.setClientLogin("admin").setClientPasscode("admin")
				.setSystemLogin("admin").setSystemPasscode("admin");

		registry.setApplicationDestinationPrefixes("/app");
		//registry.setPathMatcher(new AntPathMatcher("."));		.分割
	}

	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		registration.setInterceptors(new MyChannelInterceptor());
	}

	@Override
	public void configureWebSocketTransport(
			WebSocketTransportRegistration registration) {
		registration.setSendTimeLimit(15 * 1000)
					.setSendBufferSizeLimit(512 * 1024)
					.setMessageSizeLimit(128 * 1024);
	}

}
