package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/transfer")
public class TransferController {
    private TransferDao dao;

    public TransferController(TransferDao dao) {
        this.dao = dao;
    }
    @PreAuthorize("permitAll")
    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<Transfer> getTransferList() {
        return dao.list();
    }
//added
    @PreAuthorize("permitAll")
    @RequestMapping(path = "/statusId/{statusId}", method = RequestMethod.GET)
    public List<Transfer> transferByStatus(@PathVariable int statusId) {
        return dao.listByStatus(statusId);
    }
//added
    @PreAuthorize("permitAll")
    @RequestMapping(path = "/typeId/{typeId}", method = RequestMethod.GET)
    public List<Transfer> transferByType(@PathVariable int typeId) {
        return dao.listByType(typeId);
    }

    @PreAuthorize("permitAll")
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Transfer getUsername(@PathVariable int id) {
        return dao.getTransfer(id);
    }
//added
    @PreAuthorize("permitAll")
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/sendTransfer", method = RequestMethod.POST)
    public Transfer sendTransfer(@RequestBody Transfer transfer) {
        return dao.createTransfer(transfer);
    }

    @PreAuthorize("permitAll")
    @RequestMapping(path = "/statusChange", method = RequestMethod.PUT)
    public Transfer updateTransferStatus(@RequestBody Transfer transfer){
        return dao.transferStatusChange(transfer);
    }
    //added
    @PreAuthorize("permitAll")
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/requestTransfer", method = RequestMethod.POST)
    public Transfer requestTransfer(@RequestBody Transfer transfer) {
        return dao.reqTransfer(transfer);
    }
}