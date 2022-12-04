package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/user")
public class UserController {

    private UserDao userDao;
    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @PreAuthorize("permitAll")
    @RequestMapping(path = "/{username}", method = RequestMethod.GET)
    public int getIdByUsername(@PathVariable String username) {
        return userDao.findIdByUsername(username);
    }
    @PreAuthorize("permitAll")
    @RequestMapping(path="/username", method = RequestMethod.GET)
    public User findByName(@RequestParam String username){
        return userDao.findByUsername(username);
    }
    @PreAuthorize("permitAll")
    @RequestMapping(path= "/{id}", method = RequestMethod.GET)
    public User getUsernameById(@PathVariable int userId){
        return userDao.getUserById(userId);
    }
    @PreAuthorize("permitAll")
    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<User> findAllUsers(){
        return userDao.findAll();
    }
    //added
    @PreAuthorize("permitAll")
    @RequestMapping(path = "/getUsername", method = RequestMethod.GET)
    public String getUserFromAccount(@RequestParam int id){
        String username = userDao.getUsernameFromAccount(id);

        return username;
    }
    @PreAuthorize("permitAll")
    @RequestMapping(path = "/getAccountId", method = RequestMethod.GET)
    public int getAccountIdFromUserId(@RequestParam int id){
        int accountId = userDao.getAccountId(id);

        return accountId;
    }
//    @PreAuthorize("permitAll")
//    @ResponseStatus(HttpStatus.CREATED)
//    @RequestMapping(path = "/newUser", method = RequestMethod.GET)
//    public boolean createUser(@RequestParam String username, @RequestParam String password){
//        return userDao.create(username, password);

    }


//
//
//