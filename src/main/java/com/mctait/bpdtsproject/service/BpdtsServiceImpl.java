package com.mctait.bpdtsproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mctait.bpdtsproject.model.Instructions;
import com.mctait.bpdtsproject.model.User;

// Service implementation serving as the logic behind the API
//
@Service("bpdtsService")
public class BpdtsServiceImpl implements BpdtsService {
	
	@Autowired
	RestTemplate restTemplate;
	
	final String uri = new String("https://bpdts-test-app.herokuapp.com");
	
	public BpdtsServiceImpl() {
	}
	
	public Instructions getInstructions() {
		Instructions result = restTemplate.getForObject(uri + "/instructions", Instructions.class);
		return result;
	}
	
	public User[] getLondonUsers() {
		ResponseEntity<User[]> response = restTemplate.getForEntity(uri + "/city/London/users", User[].class);
		return response.getBody();
	}
	
	public List<User> getUsersWithin50MilesLondon() {
		ResponseEntity<User[]> response = restTemplate.getForEntity(uri + "/users", User[].class);
		User[] allUsers = response.getBody();
		GeoDesicCalculator gdCalc = new GeoDesicCalculator();
		gdCalc.setUsers(allUsers);
		return gdCalc.getLondonUsers(50);
	}
	
	public List<User> getUsersDistanceFromLondon(int d) {
		ResponseEntity<User[]> response = restTemplate.getForEntity(uri + "/users", User[].class);
		User[] allUsers = response.getBody();
		GeoDesicCalculator gdCalc = new GeoDesicCalculator();
		gdCalc.setUsers(allUsers);
		return gdCalc.getLondonUsers(d);
	}
}
