package frontend.controller;

import frontend.entity.User;
import persistence.UserDAO;
import service.UserTimeZone;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "userManager", eager = true)
@SessionScoped
public class UserManager {

    private String username;
    private String password;
    private User user;
    UserTimeZone zone = new UserTimeZone();

    public String login() {

        UserDAO users = new UserDAO();
        user = users.getUserData(username, password);
        return "home?faces-redirect=true";
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "home?faces-redirect=true";
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public User getUser() {
        return user;
    }

    public String getTimeZone() {
        return zone.getTimeZone(user.getCoordinateX(), user.getCoordinateY());
    }
}
