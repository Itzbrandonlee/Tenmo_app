package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/accounts")



public class AccountController {
    private AccountDao dao;

    public AccountController(AccountDao dao) {
        this.dao = dao;
    }
    @PreAuthorize("permitAll")
    @RequestMapping(path = "/{id}/balance", method = RequestMethod.GET)
    public Account getBalance(@PathVariable int id) {
        return dao.getBalance(id);
    }

    @PreAuthorize("permitAll")
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Account getUsername(@PathVariable int id) {
        return dao.getAccountUsername(id);
    }

    @PreAuthorize("permitAll")
    @RequestMapping(path = "/getAccountId", method = RequestMethod.GET)
    public Account getAccountFromUserId(@RequestParam int id){
        return dao.getAccountIdFromUser(id);
    }

    //added
    @PreAuthorize("permitAll")
    @RequestMapping(path = "/addBalance", method = RequestMethod.PUT)
    public void addBalanceToAccount(@RequestBody Transfer transfer){
        dao.addBalance(transfer);
    }

    //added
    @PreAuthorize("permitAll")
    @RequestMapping(path = "/subtractBalance", method = RequestMethod.PUT)
    public void subBalanceFromAccount(@RequestBody Transfer transfer){
        dao.subtractBalance(transfer);
    }
}

