package com.example.MGN_API_test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SpringBootApplication
public class MgnApiTestApplication {

	private static final Logger logger = LogManager.getLogger(MgnApiTestApplication.class);

	public static void main(String[] args) throws IOException {
		SpringApplication.run(MgnApiTestApplication.class, args);
//		ConfigurableApplicationContext context = SpringApplication.run(JmsConfig.class, args);
		ServerSocket server = new ServerSocket(1111);
		ExecutorService pool = Executors.newCachedThreadPool();

		while (true){
			try {
				Socket socket = server.accept();
				MultiClientHandler clientHandler = new MultiClientHandler(socket);
				logger.info("Client Connected!! : " + socket.getInetAddress());
				pool.execute(clientHandler);
			} catch (IOException e) {
				e.getStackTrace();
				logger.error("Socket Connection error!");
			}
		}
	}
}
