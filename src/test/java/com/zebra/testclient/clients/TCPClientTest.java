package com.zebra.testclient.clients;

import com.zebra.testclient.model.HttpMethod;
import com.zebra.testclient.model.HttpVersion;
import com.zebra.testclient.model.Message;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.event.annotation.AfterTestClass;

import static org.assertj.core.api.Assertions.assertThat;

class TCPClientTest {

    private static final String LOCAL_HOST = "127.0.0.1";
    private static final int TCP_PORT = 9000;

    private final SocketClient tcpClient = new TCPClient(LOCAL_HOST, TCP_PORT);

    @AfterTestClass
    private void tearDown() {
        tcpClient.closeConnection();
    }

    @Test
    // Returns Flag -> %9.0&%0.0&
    void sendMessage_withValueOf3_callsTcpServerEndpoint_andReturns9() {
        String expected = "%9.0&";
        Message message = Message.builder().messageBody("3.0").build();

        String result = tcpClient.sendMessage(message);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void sendMessage_withValueOf55_callsTcpServerEndpoint_andReturns3025() {
        String expected = "%3025.0&";
        Message message = Message.builder().messageBody("55").build();

        String result = tcpClient.sendMessage(message);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void sendMessage_withValueOfEADGBE_callsTcpServerEndpoint_andReturns0() {
        String expected = "%0.0&";
        Message message = Message.builder().messageBody("EADGBE").build();

        String result = tcpClient.sendMessage(message);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void sendMessage_withNegative6_callsTcpServerEndpoint_andReturns0() {
        String expected = "%36.0&";
        Message message = Message.builder().messageBody("-6").build();

        String result = tcpClient.sendMessage(message);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void sendMessage_with0_callsTcpServerEndpoint_andReturns0() {
        String expected = "%0.0&";
        Message message = Message.builder().messageBody("0.0").build();

        String result = tcpClient.sendMessage(message);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void sendMessage_withMaxFloatValueTo5thPower_callsTcpServerEndpoint_andReturnsInfinity() {
        String expected = "%Infinity&";
        Message message = Message.builder().messageBody(String.valueOf(Math.pow(Float.MAX_VALUE, 5))).build();

        String result = tcpClient.sendMessage(message);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void sendMessage_withHttpMessage_callsTcpServerEndpoint_andReturns0() {
        String expected = "%0.0&";
        Message message = Message.builder().httpMethod(HttpMethod.GET).path("/number?number=64").httpVersion(HttpVersion.ONE_DOT_ONE).build();

        String result = tcpClient.sendMessage(message);

        assertThat(result).isEqualTo(expected);
    }
}
