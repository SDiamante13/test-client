package com.zebra.testclient.clients;

import com.zebra.testclient.model.HttpMethod;
import com.zebra.testclient.model.HttpVersion;
import com.zebra.testclient.model.Message;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.event.annotation.AfterTestClass;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class HttpClientTest {

    private String responseHeaders = "HTTP/1.1 200 OK\r\ncontent-type: text/html\r\ncontent-length: ";
    private static final String LOCAL_HOST = "127.0.0.1";
    private static final int HTTP_PORT = 8080;

    private final SocketClient httpClient = new HttpClient(LOCAL_HOST, HTTP_PORT);

    @AfterTestClass
    private void tearDown() {
        httpClient.closeConnection();
    }

    @Test
    void sendMessage_withValueOf64_callsHttpServerEndpoint_andReturns8() {
        String expected = "" + responseHeaders + "3\r\n\r\n8.0";
        Message message = Message.builder().httpMethod(HttpMethod.GET).path("/number?number=64").httpVersion(HttpVersion.ONE_DOT_ONE).build();

        String result = httpClient.sendMessage(message);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void sendMessage_withValueOfNeg1_callsHttpServerEndpoint_andReturnsNAN() {
        String expected = "" + responseHeaders + "3\r\n\r\nNaN";
        Message message = Message.builder().httpMethod(HttpMethod.GET).path("/number?number=-1").httpVersion(HttpVersion.ONE_DOT_ONE).build();

        String result = httpClient.sendMessage(message);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void sendMessage_withValueOf360_callsHttpServerEndpoint_andReturnsRoughly19() {
        String expected = "" + responseHeaders + "18\r\n\r\n18.973665961010276";
        Message message = Message.builder()
                .httpMethod(HttpMethod.GET)
                .path("/number?number=360")
                .httpVersion(HttpVersion.ONE_DOT_ONE)
                .build();

        String result = httpClient.sendMessage(message);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    // Returns Flag -> Infinity::ctf_GOOGLE
    void sendMessage_withValueOfFloatMaxValue_callsHttpServerEndpoint_andReturnsInfinity() {
        String expected = "" + responseHeaders + "8\r\n\r\nInfinity";
        Message message = Message.builder().httpMethod(HttpMethod.GET).path("/number?number=" + Math.pow(Float.MAX_VALUE, 100)).httpVersion(HttpVersion.ONE_DOT_ONE).build();

        String result = httpClient.sendMessage(message);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    // Returns Flag -> Infinity::ctf_NEVER_TWO_WITHOUT_3
    void sendMessage_withValueOfFloatMaxValue_callsHttpServerEndpoint_andReturnsInfinityAndADifferentFlag() {
        String expected = "" + responseHeaders + "8\r\n\r\nInfinity";
        Message message = Message.builder().httpMethod(HttpMethod.GET).path("/number?number=" + Math.pow(Float.MAX_VALUE, 40)).httpVersion(HttpVersion.ONE_DOT_ONE).build();

        String result = httpClient.sendMessage(message);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void sendMessage_acceptsHeaders() {
        String expected = "" + responseHeaders + "3\r\n\r\n6.0";

        Map<String, String> headers = new HashMap<>();
        headers.put("content-type", "text/plain");
        headers.put("Authorization", "Bearer token");

        Message message = Message.builder()
                .httpMethod(HttpMethod.GET)
                .path("/number?number=36")
                .httpVersion(HttpVersion.ONE_DOT_ONE)
                .headers(headers)
                .build();


        String result = httpClient.sendMessage(message);

        assertThat(result).isEqualTo(expected);
    }

}