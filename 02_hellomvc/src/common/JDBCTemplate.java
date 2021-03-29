package common;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCTemplate {
	
	static String driverClass;
	static String url;
	static String user;
	static String password;
	
	static {
		
		Properties prop = new Properties();
		//클래스 객체 -> url 객체 getResource -> String 객체 : 절대경로 getPath
		String fileName = JDBCTemplate.class.getResource("/data-source.properties").getPath();
		
		System.out.println(fileName);
		try {

			prop.load(new FileReader(fileName));

			driverClass = prop.getProperty("driverClass");
			url = prop.getProperty("url");
			user = prop.getProperty("user");
			password = prop.getProperty("password");

		} catch (FileNotFoundException e1) {

			e1.printStackTrace();
		} catch (IOException e1) {

			e1.printStackTrace();
		}

		try {
			Class.forName(driverClass);
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}

	}
	
	public static Connection getConnection() {
		
		Connection conn = null;
		
		try {
			
			conn = DriverManager.getConnection(url, user, password);
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
				
		return conn;
	}
	
	public static void close(Connection conn) {

		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public static void close(ResultSet rset) {

		try {
			if (rset != null)
				rset.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		
	}
	
	public static void close(PreparedStatement pstmt) {
		
		try {
			if (pstmt != null)
				pstmt.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public static void commit(Connection conn) {
		
		try {
			if (conn != null)
				conn.commit();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		
	}

	public static void rollback(Connection conn) {
		try {
			if(conn != null)
			conn.rollback();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}

}
