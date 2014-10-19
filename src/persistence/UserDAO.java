package persistence;

import frontend.entity.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDAO {

    public User getUserData(String username, String password) {
        DBConnection dbcon = DBConnection.getInstance();
        Connection con = dbcon.getC();
        try {
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM `users` WHERE username = '" + username + "' AND password = '" + password + "'");
            if (!rs.equals(null)) {
                User user = new User(username, password);
                while (rs.next()) {
                    user.setId(rs.getInt(1));
                    user.setFirstName(rs.getString(2));
                    user.setLastName(rs.getString(3));
                    user.setAddress(rs.getString(6));
                    user.setCoordinateX(rs.getInt(7));
                    user.setCoordinateY(rs.getInt(8));
                }
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
