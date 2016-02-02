package com.network;

import java.net.*;
import java.io.*;

public class MultiThread implements Runnable {
	private Socket connect;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	MultiThread(Socket connect){
		this.connect = connect;
	}
	public void run(){
		System.out.println(connect.getInetAddress().getHostName());
		try {
			out = new ObjectOutputStream(connect.getOutputStream());
			out.flush();
			in = new ObjectInputStream(connect.getInputStream());
			System.out.println("streams are set up...1");
			System.out.println((String)in.readObject());
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}

}
