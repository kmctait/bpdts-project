package com.mctait.bpdtsproject.service;

import java.util.ArrayList;
import java.util.List;

import com.mctait.bpdtsproject.model.User;

import net.sf.geographiclib.Geodesic;
import net.sf.geographiclib.GeodesicData;
import net.sf.geographiclib.GeodesicMask;

public class GeoDesicCalculator {

	// class to calculate the shortest path on an ellipsoid (surface of the Earth) i.e. its geodesic
	// neographiclib is used to calculate the Inverse geodesic (s12 or distance) between two pairs of coordinates
	// arc length and azimuth may be ignored as the distance between points of interest is short (50 miles)
	// see https://geographiclib.sourceforge.io/html/java/net/sf/geographiclib/Geodesic.html
	//
	private User[] users;
	private List<User> londonUsers = new ArrayList<User>();

	// geo coordinates (decimal) of central London according to Wikipedia
	private double londonLat = 51.507222;
	private double londonLon = -0.1275;
	
	public GeoDesicCalculator() {
	}

	private void calculateUsersWithinDistanceFromLondon(int radius) {
		
		for(User user: this.users) {
			double userLat = user.getLatitude();
			double userLon = user.getLongitude();

			// Inverse geodesic (s12), returned in metres. Convert to miles
			GeodesicData result = Geodesic.WGS84.Inverse(londonLat, londonLon, userLat, userLon, GeodesicMask.DISTANCE);
			double distanceMiles = result.s12 / 1609.344;
			
			if(distanceMiles <= radius) {
				londonUsers.add(user);
			}
		}
	}
	
	public List<User> getLondonUsers(int radius) {
		calculateUsersWithinDistanceFromLondon(radius);
		return londonUsers;
	}

	public void setUsers(User[] users) {
		this.users = users;
	}
	
}
