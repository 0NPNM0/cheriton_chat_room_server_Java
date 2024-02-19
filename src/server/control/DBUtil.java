package server.control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class DBUtil {
	
	//建立连接对象
	public static final String URL = "jdbc:mysql://127.0.0.1:3306/jcr?useUnicode=true&characterEncoding=UTF-8";
	public static final String USERNAME = "root";
	public static final String PASSWORD = "123456";
	
	public static Connection getConnection() throws SQLException, ClassNotFoundException {
		Connection con = null;

		//加载驱动
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(URL,USERNAME,PASSWORD);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return con;
	}
	
	public static String seekAllFriend(String userName) throws ClassNotFoundException, SQLException {
		String allFriend = "";
		
		Connection con = getConnection();
		
		//查询user表
		String seek_Friend_Sql = "select slaveuser from userrelation where masteruser=? and relationtype = '1'";
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement(seek_Friend_Sql);
			stmt.setString(1, userName);
			
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				allFriend = allFriend + " " + rs.getString(1);
				
			}
			
			closeDB(con, stmt, rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return allFriend;
	}
	
	
	public static boolean loginValidate(String userName, String password) throws ClassNotFoundException, SQLException{
		boolean loginSuccess = false;
		Connection con = getConnection();
		
		//查询user表
		String user_Login_Sql = "select * from user where username=? and password=?";
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement(user_Login_Sql);
			stmt.setString(1, userName);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();
			loginSuccess = rs.next();
			
			closeDB(con, stmt, rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return loginSuccess;
	}
	
	public static void closeDB(Connection con, PreparedStatement stmt, ResultSet rs) {
		if(con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void closeDB(Connection con, PreparedStatement stmt) {
		if(con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
	public static boolean seekUser(String userName) throws ClassNotFoundException, SQLException {
		boolean seekSuccess = false;
		
		Connection con = getConnection();
		
		//查询user表
		String seek_User_Sql = "select * from user where username=? ";
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement(seek_User_Sql);
			stmt.setString(1, userName);
			
			ResultSet rs = stmt.executeQuery();
			//结果集如果有返回内容，rs.next()就返回true，反之
			seekSuccess = rs.next();
			
			closeDB(con, stmt, rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return seekSuccess;
	}
	
	public static void insertIntoUser(String userName, String password) throws ClassNotFoundException, SQLException {
		Connection con = getConnection();
		
		String Insert_Into_User_Sql = "insert into user(username, password) values(?,?) ";
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement(Insert_Into_User_Sql);
			stmt.setString(1, userName);
			stmt.setString(2, password);
			stmt.executeUpdate();
			//结果集如果有返回内容，rs.next()就返回true，反之
			
			closeDB(con, stmt);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
	public static boolean seekUserRelation(String masteruser, String slaveuser, String relationtype) throws ClassNotFoundException, SQLException {
		boolean friendRelation = false;
		
		Connection con = getConnection();
		
		
		String seek_User_Relation_Sql = "select * from userrelation where masteruser=? and slaveuser=? and relationtype =?";
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement(seek_User_Relation_Sql);
			stmt.setString(1, masteruser);
			stmt.setString(2, slaveuser);
			stmt.setString(3, relationtype);
			
			ResultSet rs = stmt.executeQuery();
			friendRelation = rs.next();
			
			closeDB(con, stmt, rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return friendRelation;
	}
	
	public static void insertIntoUserRelation(String masteruser, String slaveuser, String relationtype) throws ClassNotFoundException, SQLException {
		Connection con = getConnection();
		
		String Insert_Into_User_Relation_Sql = "insert into userrelation(masteruser, slaveuser, relationtype) values(?,?,?) ";
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement(Insert_Into_User_Relation_Sql);
			stmt.setString(1, masteruser);
			stmt.setString(2, slaveuser);
			stmt.setString(3, relationtype);
			stmt.executeUpdate();
			//结果集如果有返回内容，rs.next()就返回true，反之
			
			closeDB(con, stmt);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void saveMessage(String sender, String receiver, String content) throws ClassNotFoundException, SQLException {
		Connection con = getConnection();
		
		String Insert_Into_Message_Relation_Sql = "insert into message(sender, receiver, sendtime, content) values(?,?,?,?) ";
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement(Insert_Into_Message_Relation_Sql);
			stmt.setString(1, sender);
			stmt.setString(2, receiver);
			
			Date date = new Date();
			stmt.setTimestamp(3, new java.sql.Timestamp(date.getTime()));
			
			stmt.setString(4, content);
			stmt.executeUpdate();
			//结果集如果有返回内容，rs.next()就返回true，反之
			
			closeDB(con, stmt);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
