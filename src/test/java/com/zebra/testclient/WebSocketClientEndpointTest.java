package com.zebra.testclient;

import org.junit.jupiter.api.Test;

import java.net.URI;

class WebSocketClientEndpointTest {

    private WebSocketClientEndpoint webSocketClientEndpoint = new WebSocketClientEndpoint(URI.create("ws://localhost:8090/number"));

    @Test
    void webSocketClient() {
        webSocketClientEndpoint.sendMessage("125");
    }
}
