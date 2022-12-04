package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class AccountService {

    public String authToken = null;
    public static final String API_BASE_URL = "http://localhost:8080/accounts/";
    private RestTemplate restTemplate = new RestTemplate();

    public BigDecimal getBalance(int id){
        BigDecimal balance;
        Account account = null;
        ResponseEntity<Account> response =
                restTemplate.exchange(API_BASE_URL + id + "/balance", HttpMethod.GET,
                        makeAuthEntity(), Account.class);
        account = response.getBody();
        balance = account.getBalance();
        return balance;
    }

    public Account getAccountFromUserId(int id){
        Account account = null;
        ResponseEntity<Account> response =
                restTemplate.exchange(API_BASE_URL + id + "/balance", HttpMethod.GET,
                        makeAuthEntity(), Account.class);
        account = response.getBody();

        return account;
    }

    public int getAccountId(int id){
        Account account = null;
        ResponseEntity<Account> response =
                restTemplate.exchange(API_BASE_URL + "getAccountId/?id=" + id, HttpMethod.GET,
                        makeAuthEntity(), Account.class);
        account = response.getBody();
        int accountId = account.getAccount_id();
        return accountId;
    }

    public void addBalance(Transfer transfer) {
        HttpEntity<Transfer> entity = makeTransferEntity(transfer);
        Account returnedAccount = null;
        restTemplate.put(API_BASE_URL + "/addBalance", entity, Account.class);
    }
    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }

    private HttpEntity<Transfer> makeTransferEntity(Transfer transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(transfer, headers);
    }
    private HttpEntity<Account> makeAccountEntity(Account account) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(account, headers);
    }
}
