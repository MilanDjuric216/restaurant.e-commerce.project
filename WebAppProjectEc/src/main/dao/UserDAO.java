package main.dao;

import java.awt.List;

import main.entity.User;

public interface UserDAO {

	public java.util.List<User> getUserList();
	
	public User getUserByUsername(String username);
	
	public void saveUser(User user);
	
	public void deleteUser(String username);
	
	public void enableUser(String username);
}
