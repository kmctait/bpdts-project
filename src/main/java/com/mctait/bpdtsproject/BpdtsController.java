package com.mctait.bpdtsproject;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value="BPDTS Project API")
public class BpdtsController {

	final String uri = new String("https://bpdts-test-app.herokuapp.com/");
	
	@Autowired
	RestTemplate restTemplate;
	
	// to allow user to ensure WS is accessible and instructions have not changed
	//
	@GetMapping(value = "/instructions", produces = "application/json")
	public Instructions returnInstructions() {
	    Instructions result = restTemplate.getForObject(uri + "instructions", Instructions.class);
	    return result;
	}
	
	// return London users as defined by endpoint bpdts-test-app.herokuapp.com/city/{London}/users
	//
	@ApiOperation(value = "Returns London users as defined by bpdts-test-app.herokuapp.com/city/London/users", response = User[].class)
	@GetMapping(value = "/users/london", produces = "application/json")
	public User[] returnLondonUsers() {
		ResponseEntity<User[]> response = restTemplate.getForEntity(uri + "city/London/users", User[].class);
		return response.getBody();
	}
	
	// from the list of all users, return users within 50 miles of London
	// based on their current coordinates
	//
	@ApiOperation(value = "Returns all users whose current coordinates are 50 miles from London", response = User[].class)
	@GetMapping(value = "/users/london/50", produces = "application/json")
	public List<User> returnUsersWithin50MilesLondon() {
		ResponseEntity<User[]> response = restTemplate.getForEntity(uri + "users", User[].class);
		User[] allUsers = response.getBody();
		GeoDesicCalculator gdCalc = new GeoDesicCalculator();
		gdCalc.setUsers(allUsers);
		return gdCalc.getLondonUsers(50);
	}
	
	// Same as above, but with possibility to specify radius from London (for future use?)
	//
	@ApiOperation(value = "Returns all users whose current coordinates are d miles from London", response = User[].class)
	@GetMapping(value = "/users/london/distance/{d}", produces = "application/json")
	public List<User> returnUsersDistanceFromLondon(@PathVariable int d) {
		ResponseEntity<User[]> response = restTemplate.getForEntity(uri + "users", User[].class);
		User[] allUsers = response.getBody();
		GeoDesicCalculator gdCalc = new GeoDesicCalculator();
		gdCalc.setUsers(allUsers);
		return gdCalc.getLondonUsers(d);
	}
}
