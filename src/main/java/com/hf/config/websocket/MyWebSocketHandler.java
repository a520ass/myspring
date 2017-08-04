package com.hf.config.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

public class MyWebSocketHandler extends AbstractWebSocketHandler{
	
	private static final Logger LOGGER=LoggerFactory.getLogger(MyWebSocketHandler.class);
	
	@Override
	protected void handleTextMessage(WebSocketSession session,
			TextMessage message) throws Exception {
		LOGGER.info("received message: "+message.getPayload());
		Thread.sleep(2000);
		session.sendMessage(new TextMessage("Polo!"));
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session,
			CloseStatus status) throws Exception {
		LOGGER.info("connection closed.status: "+status);
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session)
			throws Exception {
		LOGGER.info("connection established");
	}
	
	
}
