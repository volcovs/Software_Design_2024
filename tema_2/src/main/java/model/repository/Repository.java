package model.repository;

import java.sql.*;

public class Repository {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DBURL = "jdbc:mysql://localhost:3306/";
    private static final String TIMEZONE = "?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "password";

    private String dbName;
    private static Repository singleInstance;


    public Repository(String dbName) {
        this.dbName = dbName;

        try {
            Class.forName(DRIVER);
            singleInstance = this;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Connection createConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DBURL+dbName+TIMEZONE, USER, PASS);
        } catch (SQLException e) {
            System.out.println("Error creating connection");
            e.printStackTrace();
        }

        return connection;
    }

    public static Connection getConnection() {
        return singleInstance.createConnection();
    }

    public boolean executeCommand(String sql) {
        Connection conn  = getConnection();

        try {
            Statement s = conn.createStatement();
            if (sql.contains("SELECT")) {
                s.executeQuery(sql);
            }
            else {
                s.executeUpdate(sql);
            }

            System.out.println("SQL command executed successfully!");
            close(conn);
            return true;

        } catch(SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public ResultSet getTable(String sql) {
        Connection conn  = getConnection();

        try {
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery(sql);

            System.out.println("SQL command executed successfully!");
            return rs;

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Couldn't close the connection");
            }
        }
    }

    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                System.out.println("Couldn't close the statement");
            }
        }
    }

    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                System.out.println("Couldn't close the result set");
            }
        }
    }
}
