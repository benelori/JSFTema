package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clasa care realizeaza conexiu cu baza de date
 */
public class DBConnection {
    private static DBConnection instance = null;

    private static Connection c;

    private DBConnection() {
        String url = "jdbc:mysql://localhost:3306/";
        String dbName = "testdb";
        String userName = "root";
        String password = "";
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {

            e.printStackTrace();
        }
        try {
            setC(DriverManager.getConnection(url + dbName, userName,password));
        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    public static synchronized DBConnection getInstance() {
        if (instance==null)
            instance = new DBConnection();
        return instance;
    }



    public static Connection getC() {
        return c;
    }


    public static void setC(Connection c) {
        DBConnection.c = c;
    }
}
