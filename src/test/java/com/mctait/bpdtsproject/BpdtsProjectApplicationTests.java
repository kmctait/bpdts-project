package com.mctait.bpdtsproject;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.mctait.bpdtsproject.controller.BpdtsController;
import com.mctait.bpdtsproject.model.Instructions;
import com.mctait.bpdtsproject.model.User;
import com.mctait.bpdtsproject.service.GeoDesicCalculator;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class BpdtsProjectApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private BpdtsController controller;
	
	@Autowired
	private MockMvc mockMvc;
	
	final String uri = new String("http://localhost:");
	String instructions = new String("Build an API which calls this API, and returns people who "
		+ "are listed as either living in London, or whose current coordinates are within 50 miles " 
		+ "of London. Push the answer to Github, and send us a link.");


	// Tests
	// ---------------------------------------------------------------------------------------
	
	// Check correct start up of application context
	//
	@Test
	public void contexLoads() throws Exception {
		assertThat(controller).isNotNull();
	}
	
	// Check that API called is available, check instructions are present
	//
	@Test
	public void returnDefaultInstructions() throws Exception {
		Instructions result = restTemplate.getForObject(uri + port + "/instructions", Instructions.class);
		assertThat(result.getTodo().contains(instructions));
	}
	
	// Same test as above, using Mockito
	//
	@Test
	public void returnDefaultInstructionsMock() throws Exception {
		this.mockMvc.perform(get(uri + port + "/instructions")).andDo(print()).andExpect(status().isOk())
			.andExpect(content().string(containsString(instructions)));
	}
	
	// Check that endpoint /london/users returns a non-empty list
	//
	@Test
	public void returnUsersFromLondon() throws Exception {
		ResponseEntity<User[]> response = restTemplate.getForEntity(uri + port + "/users/london", User[].class);
		User[] users = response.getBody();
		assertThat(users.length > 0);
	}
	
	// Check that endpoint london/users/50 returns 3 users
	//
	@Test
	public void returnAllTrueLondonUsers() throws Exception {
		ResponseEntity<User[]> response = restTemplate.getForEntity(uri + port + "/users/london/50", User[].class);
		User[] allUsers = response.getBody();
		GeoDesicCalculator gdCalc = new GeoDesicCalculator();
		gdCalc.setUsers(allUsers);
		assertThat(gdCalc.getLondonUsers(50).size() == 3);
	}
	
	// Spot check one of the users within 50 miles of London
	//
	public void spotCheckTrueLondonUser() throws Exception {
		ResponseEntity<User[]> response = restTemplate.getForEntity(uri + port + "/users/london/50", User[].class);
		User[] allUsers = response.getBody();
		GeoDesicCalculator gdCalc = new GeoDesicCalculator();
		gdCalc.setUsers(allUsers);
		List<User> users = gdCalc.getLondonUsers(50);
		User user = users.get(1);
		assertThat(user.getLast_name().equals("Lynd"));
	}

}
