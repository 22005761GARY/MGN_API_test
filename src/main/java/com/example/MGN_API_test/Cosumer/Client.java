package com.example.MGN_API_test.Cosumer;

import java.io.IOException;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        ClientThread client = new ClientThread(new Socket("localhost", 1111));
        client.start();
    }
}
