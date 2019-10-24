package com.zebra.testclient.clients;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.event.annotation.AfterTestClass;

import javax.websocket.CloseReason;
import java.io.IOException;
import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

class WebSocketClientTest {

    private static final URI uri = URI.create("ws://localhost:8090/number");

    private WebSocketClient webSocketClient = new WebSocketClient(uri);

    @AfterTestClass
    private void tearDown() {
        terminateSession();
    }

    @Test
    // Returns Flag -> 5.0::ctf_ONE_TWO_PUNCH
    void sendMessage_whenSending125ToWebSocketEndpoint_returns5() throws InterruptedException {
        String expected = "5.0";
        String message = "125";
        String[] results = {""};
        webSocketClient.addMessageHandler(receivedMessage -> results[0] = receivedMessage);

        webSocketClient.sendMessage(message);

        Thread.sleep(250);
        assertThat(results[0]).isEqualTo(expected);
    }

    @Test
    // Returns Flag -> 2.3513346877207573::ctf_NEVER_TWO_WITHOUT_3
    void sendMessage_whenSending13ToWebSocketEndpoint_returnsRoughly2() throws InterruptedException {
        String expected = "2.3513346877207573";
        String message = "13";
        String[] results = {""};
        webSocketClient.addMessageHandler(receivedMessage -> results[0] = receivedMessage);

        webSocketClient.sendMessage(message);

        Thread.sleep(250);
        assertThat(results[0]).isEqualTo(expected);
    }

    @Test
    // Returns Flag -> 0.0::ctf_NOT_A_NUMBER
    void sendMessage_whenSendingABC123ToWebSocketEndpoint_returns() throws InterruptedException, IOException {
        String expected = "0.0";
        String message = "ABC123";
        String[] results = {""};
        webSocketClient.addMessageHandler(receivedMessage -> results[0] = receivedMessage);

        webSocketClient.sendMessage(message);

        Thread.sleep(250);
        assertThat(results[0]).isEqualTo(expected);
    }

    @Test
    // Returns Flag -> Infinity::ctf_GOOGLE
    void sendMessage_whenSendingFloatMaxValueToWebSocketEndpoint_returnsInfinity() throws InterruptedException {
        String expected = "Infinity";
        String message = String.valueOf(Math.pow(Float.MAX_VALUE, 50));
        String[] results = {""};
        webSocketClient.addMessageHandler(receivedMessage -> results[0] = receivedMessage);

        webSocketClient.sendMessage(message);

        Thread.sleep(250);
        assertThat(results[0]).isEqualTo(expected);
    }

    @Test
    void closeConnection_closesTheWebSocketSession() {
        assertThat(webSocketClient.getSession()).isNotNull();

        terminateSession();

        assertThat(webSocketClient.getSession()).isNull();
    }

    private void terminateSession() {
        webSocketClient.closeConnection(webSocketClient.getSession(),
                new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE ,
                        "Closed Successfully"));
    }
}
