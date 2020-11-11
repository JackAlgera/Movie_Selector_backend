package com.jack.applications.websockets.handlers;

import com.jack.applications.database.DatabaseHandler;
import com.jack.applications.database.models.Movie;
import com.jack.applications.utils.JsonMapper;
import com.jack.applications.webservice.handlers.RoomHandler;
import com.jack.applications.webservice.models.Room;
import com.jack.applications.webservice.models.User;
import com.jack.applications.websockets.models.SelectionMessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

public class SelectionHandler extends TextWebSocketHandler {

    @Autowired
    private DatabaseHandler databaseHandler;

    @Autowired
    private RoomHandler roomHandler;

    private final List<WebSocketSession> webSocketSessions = new ArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        webSocketSessions.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        SelectionMessageDto messageDto = JsonMapper.getJsonMapper().readValue(message.getPayload(), SelectionMessageDto.class);

        Room room = roomHandler.getRoom(messageDto.getRoomId());
        Movie movie = databaseHandler.getMovie(messageDto.getMovieId());

        room.likeMovie(movie, messageDto.getUserId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        webSocketSessions.remove(session);
    }

}
