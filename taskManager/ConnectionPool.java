package taskManager;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnectionPool {
	private static ComboPooledDataSource cpds = new ComboPooledDataSource();

	public static void instantiate() {
		try {
			cpds.setDriverClass("com.mysql.jdbc.Driver");
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cpds.setJdbcUrl("jdbc:mysql://ec2-184-73-45-179.compute-1.amazonaws.com:3306/senior");
		cpds.setUser("seniorUser");
		cpds.setPassword("seniorUser");
		cpds.setAutoCommitOnClose(true);
	}

	public static Connection getConnection() throws SQLException {
		return cpds.getConnection();

	}
}
