package server.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import model.User;

public class TestNetworkServer {
	public static void main(String args[]) {
		try {
			ServerSocket ss = new ServerSocket(3456);
			System.out.print("server start! listening port 3456...");
			//wait for client to link
			Socket s = ss.accept();
			System.out.print("successfully link!"+s);
			
			
			InputStream is = s.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(is);
			User u = (User)ois.readObject();
			System.out.println("username: "+u.getUserName());
			System.out.println("password: "+u.getPassword());
			
			
		}catch(IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
