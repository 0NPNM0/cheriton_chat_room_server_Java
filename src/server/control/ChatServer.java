package server.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import model.Message;
import model.User;

public class ChatServer {
	
	public static HashMap hmsocket = new HashMap<String, Socket>();
	

	public ChatServer() {
		
		ServerSocket ss;
		
		try {
			ss = new ServerSocket(3456);
			System.out.print("server start! listening port 3456...");
			
			//wait for client to link
			while(true){
				Socket serverS = ss.accept();
				System.out.print("successfully link!"+serverS);
				
				
				InputStream is = serverS.getInputStream();
				ObjectInputStream ois = new ObjectInputStream(is);
				User u = (User)ois.readObject();
				System.out.println("username: "+u.getUserName());
				System.out.println("password: "+u.getPassword());
				
				String userType = u.getUserType();
				
				
				Message m = new Message();
				
				if(userType.equals(User.USER_REGISTER)) {
					if(DBUtil.seekUser(u.getUserName())) {
						m.setMessageType(Message.USER_REGISTER_FAILURE);
					}else {
						DBUtil.insertIntoUser(u.getUserName(),u.getPassword());
						m.setMessageType(Message.USER_REGISTER_SUCCESS);
					}
					ObjectOutputStream oos = new ObjectOutputStream(serverS.getOutputStream());
					oos.writeObject(m);
					
					serverS.close();
				}
				
				if(userType.equals(User.USER_LOGIN_VALIDATE)) {
					boolean loginSuccess = DBUtil.loginValidate(u.getUserName(),u.getPassword());
					
					if(loginSuccess) {
						
					    String allFriend = DBUtil.seekAllFriend(u.getUserName());
					    m.setChatContent(allFriend);
					    
						
						m.setMessageType(Message.LOGIN_VALIDATE_SUCCESS);
						
						new SeverReceiverThread(serverS).start();
					
						hmsocket.put(u.getUserName(),serverS);
						
					}else {
						m.setMessageType(Message.LOGIN_VALIDATE_FAILURE);
					}
					
					ObjectOutputStream oos = new ObjectOutputStream(serverS.getOutputStream());
					oos.writeObject(m);
				}
				
				
				
//				serverS.close();
			}
			
			
			
			
		}catch(IOException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
}
