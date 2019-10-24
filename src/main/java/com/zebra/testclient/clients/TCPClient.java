package com.zebra.testclient.clients;

/**
 * This class is a subclass of {@code SocketClient}. It is a simple client using TCP to transmit message to a TCP server.
 * When using {@code sendMessage} it should only need the messageBody attribute from {@code Message} as an input.
 *
 *  @see com.zebra.testclient.clients.SocketClient
 *  @see com.zebra.testclient.clients.HttpClient
 *
 *  @author Steven Diamante
 */
public class TCPClient extends SocketClient {

    public TCPClient(String hostName, int port) {
        super(hostName, port);
    }
}
