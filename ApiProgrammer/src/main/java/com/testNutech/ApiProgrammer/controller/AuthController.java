package com.testNutech.ApiProgrammer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.testNutech.ApiProgrammer.config.JwtTokenUtil;
import com.testNutech.ApiProgrammer.config.MyUserDetailsService;
import com.testNutech.ApiProgrammer.dto.UserDto;
import com.testNutech.ApiProgrammer.exception.UserExistException;
import com.testNutech.ApiProgrammer.exception.UserInvalidEmail;
import com.testNutech.ApiProgrammer.exception.UserInvalidPassword;
import com.testNutech.ApiProgrammer.model.User;
import com.testNutech.ApiProgrammer.repository.UserRepository;
import com.testNutech.ApiProgrammer.request.JwtRequest;
import com.testNutech.ApiProgrammer.request.UserRegistrationReq;
import com.testNutech.ApiProgrammer.response.HttpResponse;
import com.testNutech.ApiProgrammer.response.JwtResponse;
import com.testNutech.ApiProgrammer.service.CekFormatEmail;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Register and login")
public class AuthController {
	@Autowired
	CekFormatEmail cekEmail;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	private MyUserDetailsService userDetailsService;
	
	@CrossOrigin()
	@Operation(summary = "Login", description = "Login Menggunakan email dan password yang sudah terdaftar, password harus lebih dari sama dengan 8")
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
	public HttpResponse<JwtResponse> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		HttpResponse<JwtResponse> result = new HttpResponse<JwtResponse>();
		
		if (cekEmail.cekFormat(authenticationRequest.getEmail()) && authenticationRequest.getPassword().length() >= 8 ) {
			authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
			final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
			final String token = jwtTokenUtil.generateToken(userDetails);
			
			result.setStatus(0);
			result.setMessage("Login Sukses");
			result.setData(new JwtResponse(token));

			return result;
			
		} else if (authenticationRequest.getPassword().length() < 8) {
			throw new UserInvalidPassword();
		} else {
			// Salah format email, throw Exception
			throw new UserInvalidEmail();
		}
		
	}
	
	@CrossOrigin()
	@Operation(summary = "Register new user, password harus lebih dari sama dengan 8", description = "Registering for new user, password harus lebih dari sama dengan 8")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Request Successfully"),
			@ApiResponse(responseCode = "400", description = "Bad Request")
	})
	@PostMapping(value = "/registration", produces = "application/json")
	public HttpResponse<UserDto> register(@RequestBody UserRegistrationReq req) {
		HttpResponse<UserDto> result = new HttpResponse<UserDto>();
		if (cekEmail.cekFormat(req.getEmail()) && req.getPassword().length() >= 8) {
			User user = userRepo.findByEmail(req.getEmail());
			if (user == null ) {
				userRepo.save(User.builder()
						.email(req.getEmail())
						.firstName(req.getFirstName())
						.lastName(req.getLastName())
						.password(passwordEncoder.encode(req.getPassword()))
						.build());
				
				UserDto response = UserDto.builder()
						.email(req.getEmail())
						.firstName(req.getFirstName())
						.lastName(req.getLastName())
						.build();
				
				result.setStatus(0);
				result.setMessage("Registrasi berhasil silahkan login");
				result.setData(response);
				
			} else {
				// User sudah terdaftar, throw Exception
				throw new UserExistException(req.getEmail());
			}
			
		}else if (req.getPassword().length() < 8){
			throw new UserInvalidPassword();
		}else {
			throw new UserInvalidEmail();
		}
		
		return result;
	}
	
	private void authenticate(String username, String password) throws Exception {
		try {
			authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
