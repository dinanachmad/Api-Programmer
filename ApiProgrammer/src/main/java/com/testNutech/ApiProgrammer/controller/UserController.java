package com.testNutech.ApiProgrammer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.testNutech.ApiProgrammer.config.JwtTokenUtil;
import com.testNutech.ApiProgrammer.dto.UserDto;
import com.testNutech.ApiProgrammer.dto.updateData;
import com.testNutech.ApiProgrammer.exception.InvalidFormatImg;
import com.testNutech.ApiProgrammer.model.User;
import com.testNutech.ApiProgrammer.repository.UserRepository;
import com.testNutech.ApiProgrammer.response.HttpResponse;
import com.testNutech.ApiProgrammer.service.CekFormatEmail;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@Tag(name = "Module Membership")
@RequestMapping("/profile")
@CrossOrigin()
public class UserController {
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	CekFormatEmail cekEmail;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@CrossOrigin()
	@GetMapping
	@SecurityRequirement(name = "Tokenbearer")
	public HttpResponse<UserDto> getUserInformation(HttpServletRequest request) {
		String jwtToken = getToken(request);
		User user = userRepo.findByEmail(jwtTokenUtil.getUsernameFromToken(jwtToken));

		HttpResponse<UserDto> result = new HttpResponse<UserDto>();
		
		if (user != null) {
			UserDto profile = UserDto.builder()
					.email(user.getEmail())
					.firstName(user.getFirstName())
					.lastName(user.getLastName())
					.profile_img(user.getProfileImg())
					.build();
			
			result.setStatus(0);
			result.setMessage("Sukses");
			result.setData(profile);
		}
		
		return result;
	}
	
	@CrossOrigin()
	@PutMapping("/update")
	@SecurityRequirement(name = "Tokenbearer")
	public HttpResponse<UserDto> userUpdate(@RequestBody updateData data, HttpServletRequest request) {
		
		String jwtToken = getToken(request);
		User user = userRepo.findByEmail(jwtTokenUtil.getUsernameFromToken(jwtToken));
		
		HttpResponse<UserDto> result = new HttpResponse<UserDto>();
		
		if(user != null) {
			User entity = User.builder()
					.id(user.getId())
					.email(user.getEmail())
					.firstName(data.getFirst_name())
					.lastName(data.getLast_name())
					.password(user.getPassword())
					.profileImg(user.getProfileImg())
					.build();
			
			UserDto response = UserDto.builder()
					.email(user.getEmail())
					.firstName(data.getFirst_name())
					.lastName(data.getLast_name())
					.profile_img(user.getProfileImg())
					.build();
			
			userRepo.save(entity);
			
			result.setStatus(0);
			result.setMessage("Update Pofile berhasil");
			result.setData(response);
		}
		
		return result;
		
		
	}
	
	@PutMapping(path = "/image",consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	@SecurityRequirement(name = "Tokenbearer")
	public HttpResponse<UserDto> userUpdate(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws Exception{
		String jwtToken = getToken(request);
		User user = userRepo.findByEmail(jwtTokenUtil.getUsernameFromToken(jwtToken));
		
		HttpResponse<UserDto> result = new HttpResponse<UserDto>();
		
		String fileName = file.getOriginalFilename();
		 
		 if (fileName.endsWith(".jpeg") || fileName.endsWith(".jpg") || fileName.endsWith(".png")){
			 User entity = User.builder()
						.id(user.getId())
						.email(user.getEmail())
						.firstName(user.getFirstName())
						.lastName(user.getLastName())
						.password(user.getPassword())
						.profileImg(fileName)
						.build();
			 
			 UserDto response = UserDto.builder()
						.email(user.getEmail())
						.firstName(user.getFirstName())
						.lastName(user.getLastName())
						.profile_img(fileName)
						.build();
			 
			 userRepo.save(entity);
				
				result.setStatus(0);
				result.setMessage("Update Profile Image berhasil");
				result.setData(response);
		 } else {
			 throw new InvalidFormatImg();
		 }
		 
		 return result;
	}
	
	
	//Method untuk mendapatkan token
	private String getToken(HttpServletRequest request) {
		String authorizationHeader = request.getHeader("Authorization");
		String jwtToken = authorizationHeader.substring(7);
		
		return jwtToken;
	}
	
}
