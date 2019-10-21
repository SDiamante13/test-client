package com.zebra.testclient;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class SocketClientTest {

    private static final String LOCAL_HOST = "127.0.0.1";
    private static final int HTTP_PORT = 8080;
    private static final int TCP_PORT = 9000;

    private SocketClient socketClient = new SocketClient(LOCAL_HOST, HTTP_PORT);

    @AfterEach
    void tearDown() {
        socketClient.closeConnection();
    }

    @Test
    void sendMessage_withValueOf64_callsHttpServerEndpoint_andReturns8() {
        socketClient.setPort(HTTP_PORT);
        socketClient.establishConnection();
        String expected = "HTTP/1.1 200 OK\r\ncontent-type: text/html\r\ncontent-length: 3\r\n\r\n8.0";

        String result = socketClient.sendMessage("GET", "/number?number=64", "HTTP/1.1");

        Assertions.assertThat(result).isEqualTo(expected);
    }

    @Test
    void sendMessage_withValueOf3_callsTcpServerEndpoint_andReturns9() {
        socketClient.setPort(TCP_PORT);
        socketClient.establishConnection();
        String expected = "%9.0&";

        String result = socketClient.sendMessage("3.0");

        Assertions.assertThat(result).isEqualTo(expected);
    }
}
