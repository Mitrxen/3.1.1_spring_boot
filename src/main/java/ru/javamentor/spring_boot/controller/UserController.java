package ru.javamentor.spring_boot.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import ru.javamentor.spring_boot.model.User;
import ru.javamentor.spring_boot.service.UserService;


@Controller
public class UserController {

	private final UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	
    @GetMapping(value = "/")
    public String redirectFromRootToLoginPage() {
        return "redirect:/login";
    }
    
	@GetMapping(value = "/login")
	public String loginPage() {
		return "/login";
	}
    
    
    @GetMapping(value = "/admin/")
    public String redirectFromAdminRootToUsersListPage() {
    	return "redirect:/admin/users";
    }
    
    @GetMapping(value = "/admin/users")
	public String printAllUsers(ModelMap model) {
		model.addAttribute("users", userService.getAllUsers());
		return "/admin/usersList";
	}
    
	@GetMapping(value = "/user")
	public String printAboutPersonSelf(Principal principal, ModelMap model) {
		User user = userService.getUserByName(principal.getName());
		model.addAttribute("user", userService.getUserById(user.getId()));
		return "/user";
	}
	
	@GetMapping(value = "/admin/{id}")
	public String printUserInfo(@PathVariable("id") int id, ModelMap model) {
		User user = userService.getUserById(id);
		String isAdmin = "no";
		for(GrantedAuthority r : user.getAuthorities()) {
			if(r.getAuthority().equals("ROLE_ADMIN")) {
				isAdmin = "yes";
				break;
			}
		}
		model.addAttribute("user", user);
		model.addAttribute("isAdmin", isAdmin);
		return "/admin/userInfo";
	}
	
	@GetMapping(value = "/admin/add")
	public String addNewUser(ModelMap model) {
		model.addAttribute("user", new User());
		return "/admin/addNewUser";
	}
	
	@PostMapping(value = "/admin/add")
	public String addNewUser(@ModelAttribute("user") User user, HttpServletRequest request) {
		if(request.getParameter("isAdmin") != null) {
			userService.addAdmin(user);
		}else {
			userService.addUser(user);
		}
		return "redirect:/admin/users";
	}
	
	
	@GetMapping("/admin/{id}/edit")
	public String editUser(ModelMap model, @PathVariable("id") int id) {
		User user = userService.getUserById(id);
		boolean isAdmin = false;
		for(GrantedAuthority r : user.getAuthorities()) {
			if(r.getAuthority().equals("ROLE_ADMIN")) {
				isAdmin = true;
				break;
			}
		}
		model.addAttribute("isAdmin", isAdmin);
		model.addAttribute("user", user);
		return "/admin/editUser";
	}
	
	
	@PatchMapping("/admin/{id}")
	public String edit(@ModelAttribute("user") User user, @PathVariable("id") int id) {
		userService.addUser(user);
		return "redirect:/admin/users";
	}
	
	
	@DeleteMapping("/admin/{id}")
	public String deleteUser(@PathVariable("id") int id) {
		userService.removeUser(id);
		return "redirect:/admin/users";
	}
	
	
	
	
	
}