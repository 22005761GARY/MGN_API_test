package com.example.MGN_API_test;

import java.io.*;
import java.net.Socket;

import com.example.MGN_API_test.config.SpringUtil;
import com.example.MGN_API_test.service.GetRequestService;
import com.example.MGN_API_test.service.SendResponseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;

import static java.lang.Thread.currentThread;

public class MultiClientHandler implements Runnable {
    private Socket socket;
    private InputStreamReader in;
    private OutputStreamWriter out;
    private BufferedReader br;
    private BufferedWriter bw;

    private static final Logger logger = LogManager.getLogger(MultiClientHandler.class);

    //若想在Spring以外的類別使用@Service -->getBean(類似@Autowired)
    private GetRequestService getRequestService = SpringUtil.getBean(GetRequestService.class);
    private SendResponseService sendResponseService = SpringUtil.getBean(SendResponseService.class);

    public MultiClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.in = new InputStreamReader(socket.getInputStream());
        this.out = new OutputStreamWriter(socket.getOutputStream());
        this.br = new BufferedReader(in);
        this.bw = new BufferedWriter(out);
    }

    @Override
    public void run() {
        try {
//                    String request = br.readLine();
//                    if (request.equalsIgnoreCase("end")){
//                        bw.write("bye");
//                        bw.newLine();
//                        bw.flush();
//
//                        logger.info("Client disconnected..." + socket);
//                        socket.close();
//                        bw.close();
//                        br.close();
//                        break;
//                    }
//                    bw.write(getResponseService.getResponse(request));
//                    sendService.sendResponse(getResponseService.getResponse(request));

//                    bw.newLine();
//                    bw.flush();
            logger.info(currentThread().getName());
//            socket.close();
        }

        catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
