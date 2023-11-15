package com.testNutech.ApiProgrammer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.testNutech.ApiProgrammer.model.MyUserDetails;
import com.testNutech.ApiProgrammer.model.User;
import com.testNutech.ApiProgrammer.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MyUserDetailsService implements UserDetailsService{
	@Autowired
	UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByEmail(username);
		if(user != null) {
			try {
				// Mengambil data UserRol
				
				return new MyUserDetails(user);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				throw e;
			}
			
            
        } else {
            throw new UsernameNotFoundException("Email atau password salah");
        }
	}
}