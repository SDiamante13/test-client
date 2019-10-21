package com.zebra.testclient;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TCPClientTest {

    private final String localhost = "127.0.0.1";
    private final int port = 9000;

    @Test
    void tcpClient_returnsSquaredNumber() {
        TCPClient tcpClient = new TCPClient(localhost, port);

        tcpClient.establishConnection();
        tcpClient.sendMessage("5");
        tcpClient.closeConnection();

    }
}