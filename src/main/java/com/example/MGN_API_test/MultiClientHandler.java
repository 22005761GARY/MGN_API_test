package com.example.MGN_API_test;

import java.io.*;
import java.net.Socket;

import com.example.MGN_API_test.config.SpringUtil;
import com.example.MGN_API_test.service.GetResponseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;

import static java.lang.Thread.currentThread;

public class MultiClientHandler implements Runnable{

    private Socket socket;
    private InputStreamReader in;
    private OutputStreamWriter out;
    private BufferedReader br;
    private BufferedWriter bw;

    private static final Logger logger = LogManager.getLogger(MultiClientHandler.class);

    private GetResponseService getResponseService = SpringUtil.getBean(GetResponseService.class);

    public MultiClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.in = new InputStreamReader(socket.getInputStream());
        this.out = new OutputStreamWriter(socket.getOutputStream());
        this.br = new BufferedReader(in);
        this.bw = new BufferedWriter(out);
    }

    @Override
    public void run(){
        while (true){
            try{
                String request = br.readLine();
                if (request.equalsIgnoreCase("end")){
                    bw.write("bye");
                    bw.newLine();
                    bw.flush();
                    socket.close();
                }

                bw.write(getResponseService.getResponse(request));
                bw.newLine();
                bw.flush();

                logger.info(currentThread().getName());
                socket.close();

            } catch (IOException e) {
                e.getStackTrace();
                logger.error("ClientHandler Error!!");
            }catch (JSONException e){
                try {
                    logger.error("Please Enter a JSONObject type");
                    bw.write("Please Enter a JSONObject type");
                    bw.newLine();
                    bw.flush();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
