package com.network;
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
@SuppressWarnings("serial")
public class Client extends JFrame {
	
   private JTextField usertext;
   private JTextArea window;
   private ObjectOutputStream out;
   private ObjectInputStream in;
   private String mess;
   private String serverIP;
   private Socket connection;
   
   public Client(String host){
	   super("client messenger");
	   serverIP = host;
	   usertext = new JTextField();
	   usertext.setEditable(false);
	   usertext.addActionListener(
			   new ActionListener(){
				   public void actionPerformed(ActionEvent ae){
					   sendMessage(ae.getActionCommand());
					   usertext.setText("");
				   }
			   }
			   );
	   add(usertext,BorderLayout.SOUTH);
	   window = new JTextArea();
	   add( new JScrollPane(window),BorderLayout.CENTER);
	   setSize(600,200);
	   setVisible(true);
   }
	public void start(){
		try{
			connectServer();
			setUpStream();
			whileChat();
		}catch(EOFException e){
			showMessage("\n client terminated the connection");
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			closeCrap();
		}
		
	}
   
	private void connectServer() throws IOException{
		showMessage("attemting to connect...\n");
		connection = new Socket(InetAddress.getByName(serverIP),6789);
		showMessage("connected to: "+connection.getInetAddress().getHostName());
	}
 	
	private void setUpStream() throws IOException{
		out = new ObjectOutputStream(connection.getOutputStream());
		out.flush();
		in = new ObjectInputStream(connection.getInputStream());
		showMessage("\n  stream are good to go!  \n");
	}
	
	private void whileChat() throws IOException{
		ableToType(true);
		do{
			try{
				mess = (String)in.readObject();
				showMessage("\n"+mess);
			}catch(ClassNotFoundException e){
				showMessage("\n i dont know that!!");
			}
		}while(!mess.equals("SERVER - END"));
	}
	public void closeCrap(){
		showMessage("\n closing the stuff");
		ableToType(false);
		try{
			out.close();
			in.close();
			connection.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	private void sendMessage(String s){
		try{
			out.writeObject("CLIENT - "+s);
			out.flush();
			showMessage("\nCLIENT - "+s);
		}catch(IOException e){
			window.append("\n somethng messed up");
		}
	}
	
	private void showMessage(final String text){
		SwingUtilities.invokeLater(
				new Runnable(){
					public void run(){
						window.append(text);
					}
				}
				);
	}
	
	private void ableToType(boolean b){
		SwingUtilities.invokeLater(
				new Runnable(){
					public void run(){
						usertext.setEditable(b);
					}
				}
				);
	}
}






















