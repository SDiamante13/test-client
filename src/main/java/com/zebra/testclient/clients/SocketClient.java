package com.zebra.testclient.clients;

import com.zebra.testclient.model.Message;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


/**
 * This class is a top-level abstraction of a client used to connect and send messages to server hosts.
 * It uses a Socket to connect to a hostname and port of the server.
 *
 *  @see com.zebra.testclient.clients.HttpClient
 *  @see com.zebra.testclient.clients.TCPClient
 *
 *  @author Steven Diamante
 */
@Data
@Slf4j
@NoArgsConstructor
public abstract class SocketClient {

    protected String hostName;
    protected int port;
    protected Socket socket;
    protected PrintWriter writer;
    protected BufferedReader reader;

    /**
     * @param hostName The hostname of the server to connect to.
     * @param port The port number of the server to connect to.
     */
    SocketClient(String hostName, int port) {
        this.hostName = hostName;
        this.port = port;

        try {
            this.socket = new Socket(hostName, port);
            this.writer = new PrintWriter(socket.getOutputStream(), true);
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * Sends HTTP/TCP request to server.
     *
     * @param message A message sent from the client to the server
     * @return
     */
    String sendMessage(Message message) {
        writer.println(message);
        writer.println();

        return readResponse();
    }

    /**
     * Reads the response back from the server into a char array buffer.
     *
     * @return response from the socket server
     */
    String readResponse() {
        char[] numberBuffer = new char[256];
        StringBuilder response = new StringBuilder();
        try {
            reader.read(numberBuffer, 0, 256);
            for (char number : numberBuffer) {
                if (number == Character.MIN_VALUE) break;
                response.append(number);
            }
            return response.toString();
        } catch (IOException e) {
            return e.toString();
        }
    }

    /**
     * Closes the connection to the server.
     */
    void closeConnection() {
        try {
            reader.close();
            writer.close();
            socket.close();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
