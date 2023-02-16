package com.example.MGN_API_test.Cosumer;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientThread extends Thread{

    private Socket socket;
    private BufferedReader br;
    private BufferedWriter bw;
    private InputStreamReader in;
    private OutputStreamWriter out;
    public ClientThread(Socket socket) throws IOException {
        this.socket = socket;
        in = new InputStreamReader(socket.getInputStream());
        out = new OutputStreamWriter(socket.getOutputStream());
        br = new BufferedReader(in);
        bw = new BufferedWriter(out);
    }

    @Override
    public void run() {
        try{
            while (true){
                try {
                    Scanner scanner = new Scanner(System.in);
                    System.out.println("Send your request : ");
                    String request = scanner.nextLine();
                    bw.write(request);
                    bw.newLine();//換行 -> 代表request end
                    bw.flush();//request send to server when ENTER key is pressed
                    System.out.println("Server : " + br.readLine());
                    if(request.equalsIgnoreCase("END")){
                        socket.close();
                        break;
                    }
                } catch (IOException e) {
                    e.getStackTrace();
                    System.out.println("IO Exception in Client Thread");
                }
            }
        } catch (RuntimeException e) {
            e.getStackTrace();
            System.out.println("Client Thread Error");
        }
    }
}
