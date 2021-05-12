package com.libraryapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.libraryapp.entities.User;
import com.libraryapp.services.UserService;

@Service
public class CurrentUserFinder {

	@Autowired
	UserService usService;
	
	public long getCurrentUserId() {
		UserDetails details = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = details.getUsername();
		long userId = -1;
		for (User user : usService.findAll()) {
			if (user.getUserName().equals(username)) {
				userId = user.getUserId();
				break;		
			}
		}
		return userId;
	}
	
	public User getCurrentUser() {
		User currentUser = usService.findById(getCurrentUserId());
		return currentUser;
	}
}
