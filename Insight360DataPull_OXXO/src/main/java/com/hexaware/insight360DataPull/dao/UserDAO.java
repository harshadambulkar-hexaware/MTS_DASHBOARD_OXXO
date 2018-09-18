package com.hexaware.insight360DataPull.dao;
 
import java.util.List;

import com.hexaware.insight360DataPull.model.User;
 
public interface UserDAO {
    public List<User> list();
    public List<User> UserList();
    public List<User> getUserByUsername(String username);
    public User getUserByUsername(String username, String password);
    public boolean createUser(User user);
    public boolean deleteUser(String username);
    public boolean updateUser(User user);
}