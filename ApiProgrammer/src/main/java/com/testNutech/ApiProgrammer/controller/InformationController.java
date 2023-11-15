package com.testNutech.ApiProgrammer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.testNutech.ApiProgrammer.model.Banner;
import com.testNutech.ApiProgrammer.model.Service;
import com.testNutech.ApiProgrammer.response.HttpResponse;
import com.testNutech.ApiProgrammer.service.BannerService;
import com.testNutech.ApiProgrammer.service.ServiceService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Module Information")
public class InformationController {
	
	@Autowired
	private BannerService bannerService;
	
	@Autowired
	private ServiceService serviceService;
	
	@CrossOrigin()
	@GetMapping("/banner")
	public HttpResponse<List<Banner>> showBanner(){
		HttpResponse<List<Banner>> result = new HttpResponse<List<Banner>>();
		List<Banner> listBanner = bannerService.getAllBanner();
		
		result.setStatus(0);
		result.setMessage("Sukses");
		result.setData(listBanner);
		
		return result;
	}
	
	@CrossOrigin()
	@GetMapping("/service")
	@SecurityRequirement(name = "Tokenbearer")
	public HttpResponse<List<Service>> showService(){
		HttpResponse<List<Service>> result = new HttpResponse<List<Service>>();
		List<Service> listService = serviceService.getAllService();
		
		result.setStatus(0);
		result.setMessage("Sukses");
		result.setData(listService);
		
		return result;
	}
}
