package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class UserService {

    public String authToken = null;
    public static final String API_BASE_URL = "http://localhost:8080/user";
    private RestTemplate restTemplate = new RestTemplate();

    public User[] getAllUsers(){
        User[] users = null;
        ResponseEntity<User[]> response =
                restTemplate.exchange(API_BASE_URL, HttpMethod.GET,
                        makeAuthEntity(), User[].class);
        users = response.getBody();

        return users;
    }
    public int getAccountId(int id){
        User user = null;
        ResponseEntity<User> response =
                restTemplate.exchange(API_BASE_URL + "/getAccountId", HttpMethod.GET,
                        makeAuthEntity(), User.class);
        user = response.getBody();

        return user.getId();
    }


    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }

    private HttpEntity<User> makeAuctionEntity(User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(user, headers);
    }

}
