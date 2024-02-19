package server.control;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Set;

import model.Message;

public class SeverReceiverThread extends Thread{

	Socket s;
	
	public SeverReceiverThread(Socket s) {
		this.s = s;
	}
	
	public void run() {
		ObjectInputStream ois;
		try {
			
			while(true) {
				ois = new ObjectInputStream(s.getInputStream());
				Message m = (Message)ois.readObject();
				
				
				if(m.getMessageType().equals(Message.ADD_NEW_FRIEND)) {
					String newFriendName = m.getChatContent();
					
					if(DBUtil.seekUser(newFriendName)) {
						if(DBUtil.seekUserRelation(m.getSender(),newFriendName,"1")) {
							m.setMessageType(Message.ADD_NEW_FRIEND_FAILURE_ALREADY_FRIEND);
						}else {
							DBUtil.insertIntoUserRelation(m.getSender(),newFriendName,"1");
							String allFriend = DBUtil.seekAllFriend(m.getSender());
							m.setChatContent(allFriend);
							m.setMessageType(Message.ADD_NEW_FRIEND_SUCCESS);
						}
					}else {
						m.setMessageType(Message.ADD_NEW_FRIEND_FAILURE_NO_USER);
					}
					
					sendMessage(s,m);
				}
				
				
				if(m.getMessageType().equals(Message.NEW_ONLINE_FRIEND_TO_SERVER)) {
					System.out.println(m.getSender()+"online");
					
					Set onLineFriendSet = ChatServer.hmsocket.keySet();
					//迭代器
					Iterator it = onLineFriendSet.iterator();
					String onLineFriendString ="";
					while(it.hasNext()) {
						String friendsName = (String)it.next();
				
						if(!friendsName.equals(m.getSender())) {
							Socket receiverSocket = (Socket)ChatServer.hmsocket.get(friendsName);
							m.setReceiver(friendsName);
							m.setMessageType(Message.NEW_ONLINE_FRIEND_TO_CLIENT);
							sendMessage(receiverSocket, m);
						}
					}
				}
				
				
				
				if(m.getMessageType().equals(Message.REQUEST_ONLINE_FRIEND)){
					System.out.println("server get"+ m.getSender() + "request message");
					
					Set onLineFriendSet = ChatServer.hmsocket.keySet();
					//迭代器
					Iterator it = onLineFriendSet.iterator();
					String onLineFriendString ="";
					while(it.hasNext()) {
						String friendsName = (String)it.next();
						if(!friendsName.equals(m.getSender())) {
							onLineFriendString = " "+friendsName+onLineFriendString;
						}
					}
					System.out.println(m.getSender()+"online friend:"+onLineFriendString);
					
					m.setOnLineFriend(onLineFriendString);
					m.setMessageType(Message.REPONSE_ONLINE_FRIEND);
					ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
					oos.writeObject(m);
				
				}
				
				
				
				if(m.getMessageType().equals(Message.COMMON_CHAT_MESSAGE)) {
					String receiver = m.getReceiver();
					m.setSendTime(new java.util.Date());
					DBUtil.saveMessage(m.getSender(),m.getReceiver(),m.getChatContent());
					System.out.println(m.getSender()+" to "+m.getReceiver()+" : "+m.getChatContent());
					
					Socket receiverSocket = (Socket)ChatServer.hmsocket.get(receiver);
					
					ObjectOutputStream oos = new ObjectOutputStream(receiverSocket.getOutputStream());
					oos.writeObject(m);
				}
			}
		
		} catch (IOException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void sendMessage(Socket s, Message m) {
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(m);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
