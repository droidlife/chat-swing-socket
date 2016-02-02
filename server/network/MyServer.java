package com.network;

import java.io.*;
import java.net.*;

public class MyServer {
	private Socket connect;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private ServerSocket server;
	
	MyServer(){
		try {
			server = new ServerSocket(6789,100);
			System.out.println("waiting...");
			connect  = server.accept();
			System.out.println(connect.getInetAddress().getHostName());
			out = new ObjectOutputStream(connect.getOutputStream());
			out.flush();
			in = new ObjectInputStream(connect.getInputStream());
			System.out.println("streams are set up...1");
			System.out.println((String)in.readObject());
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args){
		new MyServer();
	}
}
