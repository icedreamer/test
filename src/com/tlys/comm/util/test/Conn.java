package com.tlys.comm.util.test;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conn {
	private static String driverClassName = "oracle.jdbc.driver.OracleDriver";

	private static String url = "jdbc:oracle:thin:@10.1.3.147:1521:scyyzhdb";

	private static String username = "hxzbc";

	private static String password = "tlys";

	public static synchronized Connection getConnection() throws Exception {

		Class.forName(driverClassName);
		Connection conn = DriverManager.getConnection(url, username, password);

		return conn;

	}
}
