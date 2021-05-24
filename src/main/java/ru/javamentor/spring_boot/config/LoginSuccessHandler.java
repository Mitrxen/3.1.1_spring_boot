package ru.javamentor.spring_boot.config;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler{

//	@Override
//	public void onAuthenticationSuccess(HttpServletRequest request, 
//										HttpServletResponse response,
//										Authentication authentication) throws IOException, ServletException {
//		response.sendRedirect("/admin/users");
//		
//	}
	
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
    									HttpServletResponse response,
    									Authentication auth) throws IOException {
        Set<String> roles = AuthorityUtils.authorityListToSet(auth.getAuthorities());
        if (roles.contains("ROLE_ADMIN")) {
        	response.sendRedirect("/admin/users");
        } else if(roles.contains("ROLE_USER")){
        	response.sendRedirect("/user");
        }
    }
	

	
}
