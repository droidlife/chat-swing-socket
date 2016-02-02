package com.network;
import java.net.*;
import java.awt.*;
import java.io.*;
import javax.swing.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class Server extends JFrame{
	private JTextField usertext;
	private JTextArea window;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private ServerSocket server;
	private Socket connection;
	
	public Server(){
		super("messenger");
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
		add(new JScrollPane(window),BorderLayout.CENTER);
		setSize(600,200);
	    setVisible(true);
	}
	
   public void start(){
	   try{
		   server = new ServerSocket(6789,100);
		   while(true){
			   try{
				   waitFor();
				   setUpStream();
				   whileChat();
			   }catch(EOFException e){
				   showMessage("\n server ended connection !");
			   }
			   finally{
				   closeCrap();
			   }
			   
		   }
	   }
	   catch(IOException e){
		   e.printStackTrace();
	   }
   }
   private void waitFor() throws IOException{
	   showMessage("waiting for someone to connect...");
	   connection = server.accept();
	   showMessage("now connected to"+" "+connection.getInetAddress().getHostName());
   }
   
   private void setUpStream() throws IOException{
	   out = new ObjectOutputStream(connection.getOutputStream());
	   out.flush();
	   in = new ObjectInputStream(connection.getInputStream());
	   showMessage("\n streams are set up! \n");
   }
   
   private void whileChat() throws IOException{
	   String mess = "";
	   sendMessage("you are now connected");
	   ableToType(true);
	   do{
		   try{
			   mess = (String)in.readObject();
			   showMessage("\n"+mess);
		   }catch(ClassNotFoundException e){
			   showMessage("\n idk wtf user sent!");
		   }
		   
	   }while(!mess.equals("CLIENT - END"));
   }
   
   private void closeCrap(){
	   showMessage("\n closing connection... \n");
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
		   out.writeObject("SERVER - " +s);
		   out.flush();
		    showMessage("\nSERVER - "+s);
	   }catch(IOException e){
		   window.append("\n i cant send that message");
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
   
   private void ableToType(final boolean tof){
	   SwingUtilities.invokeLater(
			  new Runnable(){
				  public void run(){
					  usertext.setEditable(tof);
				  }
			  }
			   );
   }
}

































