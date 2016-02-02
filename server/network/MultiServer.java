package com.network;

import java.io.IOException;
import java.net.*;

public class MultiServer {
      private Socket connect;
      private ServerSocket server;
      MultiServer(){
    	  try {
			server = new ServerSocket(6789,100);
			while(true){
				connect = server.accept();
				MultiThread thread = new MultiThread(connect);
				Thread t = new Thread(thread);
				t.start();
				
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
      }
	public static void main(String[] args) {
		new MultiServer();
	}
}
