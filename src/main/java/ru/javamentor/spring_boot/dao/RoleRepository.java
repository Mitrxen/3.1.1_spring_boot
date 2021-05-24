package ru.javamentor.spring_boot.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.javamentor.spring_boot.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{
	
	public List<Role> findAllByName(String name);
}
