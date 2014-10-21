package persistence;

import frontend.entity.User;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "userDao", eager = true)
@SessionScoped
public class UserDAO {

    public User getUserData(String username, String password) {
        User user = null;
        try {
            Statement s = DBConnection.getInstance().getC().createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM `users` WHERE username = '" + username + "' AND password = '" + password + "'");
            if (!rs.equals(null)) {
                user = new User();
                while (rs.next()) {
                    setUserProperties(user, rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<User>();
        try {
            Statement s = DBConnection.getInstance().getC().createStatement();
            ResultSet rs = s.executeQuery("select * from users");
            while (rs.next()) {
                User user = new User();
                setUserProperties(user, rs);
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userList;
    }

    public boolean updateUser(User user) {
        boolean success = false;
        try {
            Statement s = DBConnection.getInstance().getC().createStatement();
            String query = "UPDATE `testdb`.`users` "+
                    "SET `firstname` = '"+user.getFirstName()+"', `lastname` = '"+user.getLastName()+"', "+
                    "`username` = '"+user.getUserName()+"', `password` = '"+user.getPassword()+"', `address` = '"+user.getAddress()+"', "+
                    "`coordinate_x` = '"+user.getCoordinateX()+"', `coordinate_y` = '"+user.getCoordinateY()+
                    "' WHERE `users`.`id` = "+user.getId()+";";
            s.execute(query);
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    public boolean createUser(User user) {
        boolean success = false;
        try {
            Statement s = DBConnection.getInstance().getC().createStatement();
            String query = "INSERT INTO `testdb`.`users`"+
                    " (`firstname`, `lastname`, `username`, `password`, `address`, `coordinate_x`, `coordinate_y`, `createdAt`)"+
                    " VALUES ('"+user.getFirstName()+"', '"+user.getLastName()+"', '"+user.getUserName()+"', '"+user.getPassword()+"', '"+
                    user.getAddress()+"', '"+user.getCoordinateX()+"', '"+user.getCoordinateY()+"', CURRENT_TIMESTAMP);";
            s.execute(query);
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    public User getLastAddedUser() {
        User user = null;
        try {
            Statement s = DBConnection.getInstance().getC().createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM `users` ORDER BY `createdAt` DESC LIMIT 0 , 1");
            if (!rs.equals(null)) {
                user = new User();
                while (rs.next()) {
                    setUserProperties(user, rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    private void setUserProperties(User user, ResultSet rs) throws SQLException {
        user.setId(rs.getInt(1));
        user.setFirstName(rs.getString(2));
        user.setLastName(rs.getString(3));
        user.setUserName(rs.getString(4));
        user.setPassword(rs.getString(5));
        user.setAddress(rs.getString(6));
        user.setCoordinateX(rs.getInt(7));
        user.setCoordinateY(rs.getInt(8));
        user.setAdmin(rs.getInt(9));
    }
}
