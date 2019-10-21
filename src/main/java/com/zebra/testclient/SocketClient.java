package com.zebra.testclient;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

@Data
@NoArgsConstructor
public class SocketClient {
    private String host;
    private int port;

    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;

    public SocketClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void establishConnection() {
        try {
            socket = new Socket(host, port);
            writer = new PrintWriter(socket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends HTTP request to server.
     *
     * @param httpMethod
     * @param path
     * @param httpVersion
     */
    public String sendMessage(String httpMethod, String path, String httpVersion) {
        writer.println(httpMethod + " " + path + " " + httpVersion);
        writer.println();

        return readResponse();
    }

    /**
     * Sends TCP request to server.
     *
     * @param message
     */
    public String sendMessage(String message) {
        writer.println(message);

        return readResponse();
    }

    public String readResponse() {
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

    public void closeConnection() {
        try {
            reader.close();
            writer.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
