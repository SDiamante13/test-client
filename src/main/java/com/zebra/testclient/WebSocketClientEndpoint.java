package com.zebra.testclient;

import lombok.Data;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;

/**
 * WebSocketClientEndpoint
 *
 * @author Steven Diamante
 */
@ClientEndpoint
@Data
public class WebSocketClientEndpoint {

    private Session session;

    public WebSocketClientEndpoint(URI endpointURI) {
        try {
            WebSocketContainer webSocketContainer = ContainerProvider.getWebSocketContainer();
            webSocketContainer.connectToServer(this, endpointURI);
        } catch (IOException | DeploymentException e) {
            e.printStackTrace();
        }
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        System.out.println("Closing websocket: " + reason);
        this.session = null;
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println(message);
    }

    public void sendMessage(String message) {
        if (session != null) {
            this.session.getAsyncRemote().sendText(message);
        }
    }

//    public interface MessageHandler {
//        void handleMessage(String message);
//    }


}
