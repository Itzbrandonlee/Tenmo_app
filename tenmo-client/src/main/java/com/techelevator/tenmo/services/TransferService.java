package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class TransferService {

    public String authToken = null;
    public static final String API_BASE_URL = "http://localhost:8080/transfer";
    private RestTemplate restTemplate = new RestTemplate();



    public Transfer[] getAllTransfers(){
        Transfer[] transfers = null;
        ResponseEntity<Transfer[]> response =
                restTemplate.exchange(API_BASE_URL, HttpMethod.GET,
                        makeAuthEntity(), Transfer[].class);
        transfers = response.getBody();

        return transfers;
    }

    public Transfer newTransfer(Transfer transfer){
        HttpEntity<Transfer> entity = makeAuctionEntity(transfer);
        Transfer newTransfer = null;
        newTransfer = restTemplate.postForObject(API_BASE_URL + "/sendTransfer", entity, Transfer.class);
        return newTransfer;
    }

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }

    private HttpEntity<Transfer> makeAuctionEntity(Transfer transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(transfer, headers);
    }
//
    public Transfer[] getPendingTransfers(AuthenticatedUser authenticatedUser) {
        Transfer[] transfers = null;
        ResponseEntity<Transfer[]> response = restTemplate.exchange(API_BASE_URL + "/transfers" + authenticatedUser.getUser().getId() + "/pending", HttpMethod.GET, makeAuthEntity(), Transfer[].class);
        transfers = response.getBody();
        return transfers;
    }
}