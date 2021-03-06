package frontend.controller;

import frontend.entity.User;
import persistence.UserDAO;
import service.UserTimeZone;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.util.List;

@ManagedBean(name = "userManager", eager = true)
@SessionScoped
public class UserManager {

    private static final String EDIT_OPERATION = "edit";
    private static final String ADD_OPERATION = "add";

    @ManagedProperty(value = "#{userDao}")
    private UserDAO users;

    @ManagedProperty(value = "#{userTimeZone}")
    UserTimeZone zone;

    private String username;
    private String password;
    private User user;
    private List<User> allUsers;
    private User editableUser;
    private String operation;

    public String login() {
        user = users.getUserData(username, password);
        if (isAdmin()) {
            setAllUsers(users.getAllUsers());
        }
        return LinkConstants.HOME_PAGE;
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return LinkConstants.HOME_PAGE;
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

    public boolean isAdmin() {
        return user.getAdmin() == 1;
    }

    public List<User> getAllUsers() {
        return allUsers;
    }

    public void setAllUsers(List<User> allUsers) {
        this.allUsers = allUsers;
    }

    public String getUserById(int id) {
        setOperation(EDIT_OPERATION);
        for (User userById : allUsers) {
            if (userById.getId() == id) {
                setEditableUser(userById);
            }
        }
        return LinkConstants.EDIT_USER_PAGE;
    }

    public User getEditableUser() {
        return editableUser;
    }

    public void setEditableUser(User editableUser) {
        this.editableUser = editableUser;
    }

    public String editUser() {
        boolean result = users.updateUser(editableUser);
        String returnPage = LinkConstants.EDIT_USER_PAGE;
        if (result) {
            returnPage = LinkConstants.ADMIN_PAGE;
        }
        return returnPage;
    }

    public String addUserForm() {
        setOperation(ADD_OPERATION);
        setEditableUser(new User());
        return LinkConstants.EDIT_USER_PAGE;
    }

    public String createUser() {
        String returnPage = LinkConstants.EDIT_USER_PAGE;
        boolean result = users.createUser(editableUser);
        if (result) {
            User user = users.getLastAddedUser();
            if (user != null) {
                returnPage = LinkConstants.ADMIN_PAGE;
                allUsers.add(user);
            }
        }

        return returnPage;
    }

    public UserDAO getUsers() {
        return users;
    }

    public UserTimeZone getZone() {
        return zone;
    }

    public void setUsers(UserDAO users) {
        this.users = users;
    }

    public void setZone(UserTimeZone zone) {
        this.zone = zone;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
