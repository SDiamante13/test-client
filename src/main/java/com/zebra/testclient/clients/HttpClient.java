package com.zebra.testclient.clients;

/**
 * This class is a subclass of {@code SocketClient}. It is a simple client using HTTP to send requests to an HTTP server.
 * When using {@code sendMessage} define an {@code HttpMethod}, a path (i.e. /number?number=36), an {@code HttpVersion},
 * and optional headers.
 *
 * @see com.zebra.testclient.clients.SocketClient
 * @see com.zebra.testclient.clients.TCPClient
 *
 * @author Steven Diamante
 */
public class HttpClient extends SocketClient {

    public HttpClient(String hostName, int port) {
        super(hostName, port);
    }
}
