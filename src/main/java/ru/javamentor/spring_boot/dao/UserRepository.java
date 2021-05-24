package ru.javamentor.spring_boot.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.javamentor.spring_boot.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
//	List<User> getAllUsers();
//	
//	User getUserById(int id);
//	
	public List<User> findAllByName(String name);
//	
//	void addUser(User user);
//
//	void editUser(int id, User user);
//	
//	void removeUser(int id);
	
}
