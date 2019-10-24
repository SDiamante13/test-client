package com.zebra.testclient.clients;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;

/**
 * This class is implemented using . It is a simple client using HTTP to send requests to an HTTP server.
 * When using {@code sendMessage} define an {@code HttpMethod}, a path (i.e. /number?number=36), an {@code HttpVersion},
 * and optional headers.
 *
 * @author Steven Diamante
 * @see javax.websocket.WebSocketContainer
 * @see javax.websocket.ClientEndpoint
 * @see javax.websocket.ContainerProvider
 * @see javax.websocket.Session
 */
@ClientEndpoint
public class WebSocketClient {

    private Logger log = LoggerFactory.getLogger(WebSocketClient.class);

    private Session session;

    private MessageHandler messageHandler;

    /**
     * Example URI: ws://localhost:8090/number
     *
     * @param endpointURI The endpoint in which to start a connection with.
     */
    public WebSocketClient(URI endpointURI) {
        try {
            WebSocketContainer webSocketContainer = ContainerProvider.getWebSocketContainer();
            webSocketContainer.connectToServer(this, endpointURI);
        } catch (IOException | DeploymentException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * @param msgHandler Handles messages after they are received by the {@code @OnMessage} callback.
     */
    public void addMessageHandler(MessageHandler msgHandler) {
        this.messageHandler = msgHandler;
    }

    /**
     * @param session represents a conversation between two web socket endpoints.
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
    }

    /**
     * Closes the connection between the web socket endpoints.
     *
     * @param session
     * @param reason
     */
    @OnClose
    public void closeConnection(Session session, CloseReason reason) {
        log.info("Closing websocket: " + reason);
        this.session = null;
    }

    /**
     * @param message A message received from the WebSocket Server.
     */
    @OnMessage
    public void receiveMessage(String message) {
        if (this.messageHandler != null) {
            this.messageHandler.handleMessage(message);
        }
    }

    /**
     * @param message A message sent to the WebSocket Server.
     */
    void sendMessage(String message) {
        if (session != null) {
            this.session.getAsyncRemote().sendText(message);
        } else {
            log.error("The Web Socket session is closed. The message was not send successfully.");
        }
    }

    public interface MessageHandler {
        void handleMessage(String message);
    }

    public Session getSession() {
        return session;
    }
}
