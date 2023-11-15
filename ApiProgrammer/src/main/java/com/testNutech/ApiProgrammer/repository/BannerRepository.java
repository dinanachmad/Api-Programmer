package com.testNutech.ApiProgrammer.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.testNutech.ApiProgrammer.model.Banner;

@Repository
public interface BannerRepository extends CrudRepository<Banner, Integer>{

}
