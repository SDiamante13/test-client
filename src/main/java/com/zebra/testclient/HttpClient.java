package com.zebra.testclient;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.*;
import java.net.Socket;


@Data
@RequiredArgsConstructor
public class HttpClient {

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

    public String sendMessage(String httpMethod, String path, String httpVersion) {
//        writer.println();
//
//        String line;
//        try {
//            line = reader.readLine();
//
//            while (line != null) {
//                System.out.println(line);
//                line = reader.readLine();
//                if(line == null) {
//                    line = reader.readLine();
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        char[] numberBuffer = new char[256];
        StringBuilder response = new StringBuilder();
        try {
            writer.println(httpMethod + " " + path + " " + httpVersion);
            writer.println();
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
