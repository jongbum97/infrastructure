package com.ssafy.util;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.game.model.Message;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebSocketHandler extends TextWebSocketHandler{

	private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
	private final ObjectMapper mapper = new ObjectMapper();
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		// 소켓 연결시 실행
		log.debug(session.toString());
		sessions.put(session.getId(), session);
		sessions.values().forEach( s -> {
			try {
				Message message = new Message("alert", session.getId()+"님 입장");
				s.sendMessage(new TextMessage(mapper.writeValueAsString(message)));
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		// 데이터 통신시 실행
		log.debug(message.getPayload());
		sessions.values().forEach( s -> {
			try {
				s.sendMessage(new TextMessage(message.getPayload()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		// 연결 종료시 실행
		log.debug(session.toString());
		sessions.remove(session.getId());
		sessions.values().forEach( s -> {
			try {
				Message message = new Message("alert", session.getId()+"님 퇴장");
				s.sendMessage(new TextMessage(mapper.writeValueAsString(message)));
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
	
	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		// 에러 처리
		
	}

	
	
}
