package com.testNutech.ApiProgrammer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.testNutech.ApiProgrammer.model.Banner;
import com.testNutech.ApiProgrammer.repository.BannerRepository;

@Service
public class BannerServiceImp implements BannerService{
	
	@Autowired
	private BannerRepository bannerRepo;

	@Override
	public List<Banner> getAllBanner() {
		// TODO Auto-generated method stub
		return (List<Banner>) bannerRepo.findAll();
	}

}
