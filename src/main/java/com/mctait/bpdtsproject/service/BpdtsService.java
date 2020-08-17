package com.mctait.bpdtsproject.service;

import java.util.List;

import com.mctait.bpdtsproject.model.Instructions;
import com.mctait.bpdtsproject.model.User;

public interface BpdtsService {
	
	Instructions getInstructions();
	User[] getLondonUsers();
	List<User> getUsersWithin50MilesLondon();
	List<User> getUsersDistanceFromLondon(int d);
	
}
