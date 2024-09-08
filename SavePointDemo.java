package JDBCSavePoint;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;

public class SavePointDemo {
	static final String JDBC_URL = "jdbc:mysql://localhost:3306/Adjava";
	static final String USERNAME = "root";
	static final String PASSWORD = "root";

	public static void main(String[] args) {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			// establish connection
			connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

			// create the statement object
			statement = connection.createStatement();

			connection.setAutoCommit(false);
			statement.executeUpdate("insert into politicians values('CBN','TDP')");
			statement.executeUpdate("insert into politicians values('KCR','BRS')");

			// create savepoint
			Savepoint sp = connection.setSavepoint();
			statement.executeUpdate("insert into politicians values('SIDDU','BJP')");
			System.out.println("oops.. Wrong entry, rollingback ");
			connection.rollback(sp);
			System.out.println("Rollback successfull..");

			connection.commit();
			System.out.println("Transaction Committed");
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}
	}
}
