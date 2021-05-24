package ru.javamentor.spring_boot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ru.javamentor.spring_boot.dao.RoleRepository;
import ru.javamentor.spring_boot.dao.UserRepository;
import ru.javamentor.spring_boot.model.Role;
import ru.javamentor.spring_boot.model.User;

@Component
@Transactional
public class UserServiceImpl implements UserService{
	
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	public User getUserByName(String name) {
		return userRepository.findAllByName(name).get(0);
	}
	
	
	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	} 
	
	@Override
	public User getUserById(int id) {
		return userRepository.findById(id).get();
	}
	
	@Override
	public void addUser(User user) {
		Role role = roleRepository.findAllByName("ROLE_USER").get(0);
		user.addRoleToUser(role);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}
	
	@Override
	public void addAdmin(User user) {
		Role role = roleRepository.findAllByName("ROLE_ADMIN").get(0);
		user.addRoleToUser(role);
		this.addUser(user);
	}
	
	@Override
	public void editUser(int id, User user) {
		user.setUserRoles(userRepository.findById(id).get().getUserRoles());
		userRepository.save(user);
	}
	
	@Override
	public void removeUser(int id) {
		userRepository.deleteById(id);
	}
	
}
