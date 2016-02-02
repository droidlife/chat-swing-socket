package com.network;
import javax.swing.JFrame;
@SuppressWarnings("serial")
public class Testc extends JFrame {
	public static void main(String[] args){
    Client c = new Client("127.0.0.1");
    c.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    c.start();
    
}
}