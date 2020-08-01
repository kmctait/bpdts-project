# BPDTS Project API Reference
This project was written in response to the interview process for DWP.

This API calls the following API:

`https://bpdts-test-app.herokuapp.com/`

and, according to the instructions, "returns people who are listed as either living in London, or whose current coordinates are within 50 miles of London."


# Getting Started

This API was written using Spring Boot. To run, either fork this github project and run from your preferred development environment using maven or run the JAR file found in the root directory of the project:

`java -jar bpdts-project-0.0.1-SNAPSHOT.jar`

Of course, a JRE is required in order to run the service.

Unit tests can be found in the folder `/src/test/java` and are run using maven on the command line or using your preferred IDE.

Further API documentation can be found using the [Swagger UI pages](http://localhost:8080/swagger-ui.html)

# Authentication

No authentication (username/password pair, token etc.) is required to access this API.

# API Reference

## Get Instructions
This endpoint returns the original instructions for the task and is included here only to test access to the original endpoints.

HTTP Request:

`GET http://localhost:8080/instructions`

The above command returns JSON structured like this:

```json
{
  "todo": "Build an API which calls this API, and returns people who are listed as either 
  living in London, or whose current coordinates are within 50 miles of London. Push the 
  answer to Github, and send us a link."
}
```

## Get users from London
This endpoint simply mirrors the API call given by:

`https://bpdts-test-app.herokuapp.com/city/{London}/users`

> CAUTION: the endpoint above does not appear to provide a list of users who actually reside in London given the location of their IP address or their geospatial coordinates.

HTTP Request:

`GET http://localhost:8080/users/london`

The above command returns JSON structured like this:

```json
[
  {
    "id": 135,
    "first_name": "Mechelle",
    "last_name": "Boam",
    "email": "mboam3q@thetimes.co.uk",
    "ip_address": "113.71.242.187",
    "latitude": -6.5115909,
    "longitude": 105.652983
  },
  {
    "id": 396,
    "first_name": "Terry",
    "last_name": "Stowgill",
    "email": "tstowgillaz@webeden.co.uk",
    "ip_address": "143.190.50.240",
    "latitude": -6.7098551,
    "longitude": 111.3479498
  },
  ...
]
```

## Get Users living within 50 miles of London
From the list of all users, returns users whose coordinates are within 50 miles of central London.

HTTP Request:

`GET http://localhost:8080/users/london/50`

The above command returns JSON structured like this:

```json
[
	{
		"id":266,
		"first_name":"Ancell",
		"last_name":"Garnsworthy",
		"email":"agarnsworthy7d@seattletimes.com",
		"ip_address":"67.4.69.137",
		"latitude":51.6553959,
		"longitude":0.0572553
	},
	{
		"id":322,
		"first_name":"Hugo",
		"last_name":"Lynd",
		"email":"hlynd8x@merriam-webster.com",
		"ip_address":"109.0.153.166",
		"latitude":51.6710832,
		"longitude":0.8078532
	}..
]
```

## Get users living within any distance d from London
From the list of all users, returns users whose coordinates are within a given distance d, in miles, from central London.

HTTP Request:

`GET http://localhost:8080/users/london/distance/{d}`

The above command returns JSON structured like this:

```json
[
	{
		"id":266,
		"first_name":"Ancell",
		"last_name":"Garnsworthy",
		"email":"agarnsworthy7d@seattletimes.com",
		"ip_address":"67.4.69.137",
		"latitude":51.6553959,
		"longitude":0.0572553
	},
	{
		"id":322,
		"first_name":"Hugo",
		"last_name":"Lynd",
		"email":"hlynd8x@merriam-webster.com",
		"ip_address":"109.0.153.166",
		"latitude":51.6710832,
		"longitude":0.8078532
	}..
]
```

# Notes
There are issues with the API called by this API and the data set:
- endpoint `bpdts-test-app.herokuapp.com/city/{London}/users` returns users whose IP address or coordinates are not within the London area
- endpoint `bpdts-test-app.herokuapp.com/user/{id}` returns users with an extra field 'city' which is not consistent with the coordinates or location of the IP address
- the location of any given IP address does not match the geospatial coordinates of the user