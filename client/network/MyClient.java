package com.network;

import java.io.*;
import java.net.*;

public class MyClient {
	private String ip = "127.0.0.1";
	private Socket connect;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	public void start(){
		try {
			connect = new Socket(InetAddress.getByName(ip),6789);
			out = new ObjectOutputStream(connect.getOutputStream());
			out.flush();
			in = new ObjectInputStream(connect.getInputStream());
			System.out.println("streams");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void data(){
		String s = "hello world of network";
		try {
			out.writeObject(s);
			out.writeObject(s);
			out.flush();
			connect.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args){
		MyClient client  = new MyClient();
		client.start();
		client.data();
	}

}
