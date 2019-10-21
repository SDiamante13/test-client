package com.zebra.testclient;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

@Data
@RequiredArgsConstructor
public class TCPClient {

    private final String host;
    private final int port;

    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;

    public void establishConnection() {
        try {
            socket = new Socket(host, port);
            writer = new PrintWriter(socket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String sendMessage(String message) {
        char[] numberBuffer = new char[256];
        StringBuilder response = new StringBuilder();
        try {
            writer.println(message);
            reader.read(numberBuffer, 0, 256);
            for (char number : numberBuffer) {
                if (number == Character.MIN_VALUE) break;
                response.append(number);
            }
            System.out.println(response.toString());
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
