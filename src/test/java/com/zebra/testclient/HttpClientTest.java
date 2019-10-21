package com.zebra.testclient;

import org.junit.jupiter.api.Test;

class HttpClientTest {

    private final String localhost = "127.0.0.1";
    private final int port = 8080;


    @Test
    void httpClient_callsHttpServer() {
        HttpClient httpClient = new HttpClient(localhost, port);

        httpClient.establishConnection();
        httpClient.sendMessage("GET", "/number?number=64", "HTTP/1.1");
        httpClient.closeConnection();
    }
}
