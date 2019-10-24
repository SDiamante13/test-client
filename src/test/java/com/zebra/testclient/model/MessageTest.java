package com.zebra.testclient.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class MessageTest {

    @Test
    void toString_printsOutMessageCorrectlyIgnoringAnyNulls() {
        String expected = " 4.0";

        Message message = Message.builder()
                .messageBody("4.0")
                .build();

        Assertions.assertThat(message.toString()).isEqualTo(expected);
    }

    @Test
    void toString_printsOutMessageCorrectly() {
        String expected = "GET /number?number=121 HTTP/1.0";

        Message message = Message.builder()
                .httpVersion(HttpVersion.ONE_DOT_ZERO)
                .httpMethod(HttpMethod.GET)
                .path("/number?number=121")
                .build();

        Assertions.assertThat(message.toString()).isEqualTo(expected);
    }

    @Test
    void toString_printsOutMessageIncludingHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Useless-header", "Means nothing");
        headers.put("Content-type", "text/plain");
        String expected = "GET /number?number=121 HTTP/1.0\r\nContent-type: text/plain\r\nUseless-header: Means nothing";

        Message message = Message.builder()
                .httpVersion(HttpVersion.ONE_DOT_ZERO)
                .httpMethod(HttpMethod.GET)
                .headers(headers)
                .path("/number?number=121")
                .build();

        Assertions.assertThat(message.toString()).isEqualTo(expected);
    }
}