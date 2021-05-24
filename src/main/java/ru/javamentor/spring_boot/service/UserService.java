package ru.javamentor.spring_boot.service;

import java.util.List;

import ru.javamentor.spring_boot.model.User;

public interface UserService {
	List<User> getAllUsers();
	
	User getUserById(int id);
	
	User getUserByName(String username);
	
	void addUser(User user);
	
	void addAdmin(User user);

	void editUser(int id, User user);
	
	void removeUser(int id);
}
