package com.testNutech.ApiProgrammer.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.http.converter.HttpMessageConversionException;

import com.testNutech.ApiProgrammer.exception.BalanceKurang;
import com.testNutech.ApiProgrammer.exception.InvalidFormatImg;
import com.testNutech.ApiProgrammer.exception.UserExistException;
import com.testNutech.ApiProgrammer.exception.UserInvalidEmail;
import com.testNutech.ApiProgrammer.exception.UserInvalidPassword;
import com.testNutech.ApiProgrammer.exception.invalidTopUp;
import com.testNutech.ApiProgrammer.response.HttpResponse;
;

@ControllerAdvice
public class MyExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler({ BadCredentialsException.class })
    @ResponseBody
	public ResponseEntity<HttpResponse> handleBadCredentialException(Exception ex) {
		
		HttpResponse<Object> model = new HttpResponse<Object>();
		model.setStatus(108);
		model.setMessage(ex.getMessage());

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(model);
	}

	@ExceptionHandler({ InsufficientAuthenticationException.class })
    @ResponseBody
	public ResponseEntity<HttpResponse> handleInsufficientAuthenticationException(Exception ex) {
		
		HttpResponse<Object> model = new HttpResponse<Object>();
		model.setStatus(108);
		model.setMessage("Token tidak tidak valid atau kadaluwarsa");

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(model);
	}
	
	@ExceptionHandler({ UserInvalidEmail.class, UserExistException.class, InvalidFormatImg.class, invalidTopUp.class, BalanceKurang.class})
    @ResponseBody
	public ResponseEntity<HttpResponse> userAlreadyExistsException(Exception ex) {
		
		HttpResponse<Object> model = new HttpResponse<Object>();
		model.setStatus(102);
		model.setMessage(ex.getMessage());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(model);
	}
	
	@ExceptionHandler({ UsernameNotFoundException.class, UserInvalidPassword.class})
    @ResponseBody
	public ResponseEntity<HttpResponse> wrongDataUser(Exception ex) {
		
		HttpResponse<Object> model = new HttpResponse<Object>();
		model.setStatus(103);
		model.setMessage(ex.getMessage());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(model);
	}
	
	
}
