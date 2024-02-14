package com.codeddecode.restaurantlisting;

// Code decode Restaurant  - Listing microservice
// https://www.udemy.com/course/master-spring-boot-microservice-angular-with-k8s-cicd-aws/learn/lecture/38836138#questions/20380806


/*                          Eureka client enterprise level Application.


              ▬▬ Create the basic structure of a web application. ▬▬


    1. Controller
                * is going to have all your rest endpoints
                            - required to interact with your clients

    2. Service
                * are going to have all your business logics written,

    3. Repository layer
                * have the connection with the database.

																○ So this is how we are following the Single Responsibility Model design
																   principle
																												* where controller we are not going to put any business logic
																												* or any database interaction in service node interaction with
																												* either the database nor nor any interaction with the client.
																				Only business logic will go here
																												* repo layer where we have only and only all the connections with
																																								- the database and
																																								- no interaction with the
																																																				• client or
																																																				• the business logic.

																○ Apart from that,
																												* we need one more package for entity and
																												* one more package for DTO.

    4. DTO(Data Transfer Object)
     			        * So whenever you interact with your client, getting a restaurant entity to
     			                    - save it in the database or
                            - to return a restaurant entity,
                    never work with the entities.
         In real world, you will
                 * always interact with your consumers with the data transfer objects and
                 * will not have entities interacting.

 */


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestaurantlistingApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestaurantlistingApplication.class, args);
	}
}


