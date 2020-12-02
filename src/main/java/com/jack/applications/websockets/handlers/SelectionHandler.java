package com.jack.applications.websockets.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jack.applications.database.DatabaseHandler;
import com.jack.applications.database.models.Movie;
import com.jack.applications.utils.JsonMapper;
import com.jack.applications.webservice.handlers.RoomHandler;
import com.jack.applications.webservice.models.Room;
import com.jack.applications.webservice.models.Selection;
import com.jack.applications.webservice.models.User;
import com.jack.applications.websockets.models.FoundMovieMessageDto;
import com.jack.applications.websockets.models.SelectionMessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
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

        if (room.likeMovie(movie, messageDto.getUserId(), room.getConnectedUsers().size())) {
            broadcastMovieFound(new FoundMovieMessageDto(room.getFoundMovie()));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        webSocketSessions.remove(session);
    }

    private void broadcastMovieFound(FoundMovieMessageDto foundMovieId) throws JsonProcessingException {
//        TextMessage messageToSend = new TextMessage(JsonMapper.getJsonMapper().getObjectMapper().writeValueAsString(new ArrayList<>(room.getSelectedMovies().values())));
        TextMessage messageToSend = new TextMessage(JsonMapper.getJsonMapper().getObjectMapper().writeValueAsString(foundMovieId));
        webSocketSessions.forEach(webSocketSession -> {
            try {
                webSocketSession.sendMessage(messageToSend);
                System.out.println(String.format("Send message : %s to %s", foundMovieId, webSocketSession.getId()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
