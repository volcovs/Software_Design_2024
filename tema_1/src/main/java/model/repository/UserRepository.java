package model.repository;

import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private Repository repository;
    private String dbName = "gradina_botanica";


    public UserRepository(String dbName) {
        this.dbName = dbName;
        this.repository = new Repository(dbName);
    }


    public boolean addUser(User user) {
        String command = "INSERT INTO Users(person_id, username, password, admin_status) Values('";
        command += user.getPerson() + "', '" + user.getUsername() + "', '" + user.getPassword() + "', '" + user.getAdminStatus() + "' );";

        return this.repository.executeCommand(command);
    }

    public boolean updateUser(String id, User u) {
        String command = "UPDATE Users SET";
        command += " person_id = '" + u.getPerson() + "',";
        command += " username = '" + u.getUsername() + "',";
        command += " password = '" + u.getPassword() + "', ";
        command += " admin_status = '" + u.getAdminStatus() + "' ";

        command += "WHERE user_id = " + id + ";";

        return this.repository.executeCommand(command);
    }

    public boolean deleteUser(String id) {
        String command = "DELETE FROM Users WHERE user_id = " + id + ";";

        return this.repository.executeCommand(command);
    }

    public User searchUser(String id) {
        String search = "SELECT * FROM Users WHERE user_id = " + id + ";";
        ResultSet rs = this.repository.getTable(search);
        User u = null;

        try {
            rs.next();

            String userId = String.valueOf(rs.getInt("user_id"));
            String person = rs.getString("person_id");
            String username = rs.getString("username");
            String password = rs.getString("password");
            String status = rs.getString("admin_status");

            u = new User(userId, person, username, password, status);
            this.repository.close(rs);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return u;
    }

    public User searchUserLogIn(String username, String password) {
        String search = "SELECT * FROM Users WHERE username = '" + username + "' AND password = '" + password + "';";
        ResultSet rs = this.repository.getTable(search);
        User u = null;

        try {
            rs.next();

            String userId = String.valueOf(rs.getInt("user_id"));
            String person = rs.getString("person_id");
            String user = rs.getString("username");
            String pass = rs.getString("password");
            String status = rs.getString("admin_status");

            u = new User(userId, person, user, pass, status);
            this.repository.close(rs);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return u;
    }

    public List<User> userList() {
        List<User> users = new ArrayList<>();

        String getUsers = "SELECT * FROM Users";
        ResultSet rs = this.repository.getTable(getUsers);

        try {
            while(rs.next()) {
                User u;

                String userID = String.valueOf(rs.getInt("user_id"));
                String id = rs.getString("person_id");
                String password = rs.getString("password");
                String username = rs.getString("username");
                String status = rs.getString("admin_status");

                u = new User(userID, id, username, password, status);
                users.add(u);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        this.repository.close(rs);
        return users;
    }

}
