package app.core.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import app.core.exception.CouponSystemException;

public class ConnectionPool {

	private Set<Connection> connections = new HashSet<>();
	public static final int MAX = 10;
	private String url = "jdbc:mysql://localhost:3306/coupons_db"; // ?createDatabaseIfNotExist = true";
	private String user = "root";
	private String password = "1234";
	private static ConnectionPool instance;

	/**
	 * Private constructor in the Singleton class, creates in a set an amount of
	 * connections to the database by choice, and this is part of the process in the
	 * Singleton class.
	 * 
	 * @throws CouponSystemException if an error in creating a connection.
	 */
	private ConnectionPool() throws CouponSystemException {
		try {
			for (int i = 0; i < MAX; i++) {
				Connection con = DriverManager.getConnection(url, user, password);
				connections.add(con);
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Error in creating a connection. ");
		}
	}

	/**
	 * The method initializes the single instance of this object.
	 * 
	 * @return initializes the single instance of this object.
	 * @throws CouponSystemException if can't initialize connection pool.
	 */
	public static ConnectionPool getInstance() throws CouponSystemException {
		if (instance == null) {
			try {
				instance = new ConnectionPool();
			} catch (CouponSystemException e) {
				throw new CouponSystemException("can't initialize connection pool. ");
			}
		}
		return instance;
	}

	/**
	 * Removes one Connection object from the database and returns it with return so
	 * that it can be used for performing operations on the database. If the pool is
	 * empty because all the Connections are currently in use - Performs wait to
	 * wait until some connection is freed and returned to the pool.
	 * 
	 * @return connection.
	 * @throws CouponSystemException if the operation failed.
	 */
	public synchronized Connection getConnection() throws CouponSystemException {
		while (connections.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				throw new CouponSystemException("Method was interrupted");
				// break;
			}
		}
		Iterator<Connection> it = connections.iterator();
		Connection connection = it.next();
		it.remove();
		return connection;
	}

	/**
	 * Receives as an argument one Connection object that has been freed and returns
	 * it to the pool. Because it has now been added For an additional Connection
	 * pool - notify is performed to notify any thread that is waiting (that is, it
	 * performed a wait) Now one connection has become available and so you can try
	 * to get it.
	 * 
	 * @param connection
	 * @throws CouponSystemException if the operation failed.
	 */
	public synchronized void restoreConnection(Connection connection) throws CouponSystemException {
		connections.add(connection);
		notify();
	}

	/**
	 * Closes all connections to the database.
	 * 
	 * @throws CouponSystemException if can't close a connection - failed.
	 */
	public synchronized void closeAllConnections() throws CouponSystemException {
		while (connections.size() < MAX) {
			try {
				wait();
			} catch (InterruptedException e) {
				throw new CouponSystemException("Method was interrupted");
			}
		}
		Iterator<Connection> iterator = connections.iterator();
		while (iterator.hasNext()) {
			Connection connection = iterator.next();
			try {
				connection.close();
			} catch (SQLException e) {
				throw new CouponSystemException("Can't close a connection");
			}
		}
	}

}
