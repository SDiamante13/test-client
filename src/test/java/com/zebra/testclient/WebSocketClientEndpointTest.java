package com.zebra.testclient;

import org.junit.jupiter.api.Test;

import java.net.URI;

class WebSocketClientEndpointTest {

    private static final URI uri = URI.create("ws://localhost:8090/number");

    private WebSocketClientEndpoint webSocketClientEndpoint = new WebSocketClientEndpoint(uri);

    @Test
    void webSocketClient() {
        webSocketClientEndpoint.sendMessage("125");
    }
}
