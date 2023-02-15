package com.example.MGN_API_test;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

public class MultiClientHandler implements Runnable{

    private Socket socket;
    private InputStreamReader in;
    private OutputStreamWriter out;
    private BufferedReader br;
    private BufferedWriter bw;

    private static final Logger logger = LogManager.getLogger(MultiClientHandler.class);

    public MultiClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.in = new InputStreamReader(socket.getInputStream());
        this.out = new OutputStreamWriter(socket.getOutputStream());
        this.br = new BufferedReader(in);
        this.bw = new BufferedWriter(out);
    }
    @Override
    public void run() {
        try{
            while (true) {
                try {
                    String clientRequest = br.readLine();
//                        System.out.println("Client : " + clientRequest);
                    logger.info(socket.getPort() + " Client : " + clientRequest);
                    if (clientRequest.equalsIgnoreCase("END")) {
                        bw.write("bye");
                        logger.info(" Client disconnected " + socket);
                        bw.newLine();
                        bw.flush();
                        break;
                    } else {
                        JSONObject jsonObject = new JSONObject(clientRequest);
                        String requestType = jsonObject.getString("requestType");
                        switch (requestType) {
                            case "Select":
//                                bw.write(SqlConnection.getData(jsonObject).toString());
                                bw.newLine();
                                bw.flush();
                                break;
                            case "Create":
//                                bw.write(SqlConnection.createData(jsonObject));
                                bw.newLine();
                                bw.flush();
                                break;
                            case "Update":
                                JSONObject key = jsonObject.getJSONObject("key");
//                                bw.write(SqlConnection.updateData(jsonObject, key).toString());
                                bw.newLine();
                                bw.flush();
                                break;
                            case "Delete":
//                                bw.write(SqlConnection.deleteData(jsonObject));
                                bw.newLine();
                                bw.flush();
                                break;
                            default:
                                bw.write("Incorrect request, Only use for CRUD");
                                bw.newLine();
                                bw.flush();
                                break;
                        }
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
//                catch (SQLException e) {
//                    e.getStackTrace();
//                    logger.error("SQL syntax Error");
//                    bw.write("SQL syntax Error");
//                    bw.newLine();
//                    bw.flush();
//                }
                catch (JSONException e) {
                    e.getStackTrace();
                    logger.error("JSON type error(Please enter a JSONObject)");
                    bw.write("JSON type error(Please enter a JSONObject)");
                    bw.newLine();
                    bw.flush();
                } catch (RuntimeException e) {
                    e.getStackTrace();
                    logger.error(e.getMessage());
                    bw.write(e.getMessage());
                    bw.newLine();
                    bw.flush();
                }
            }
        } catch (IOException e) {
            e.getStackTrace();
            logger.error("IO Exception in Client Handler");
        }    }
}
