package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDao {

    Account getAccountUsername(int id);


    Account getAccountIdFromUser(int id);

    Account getBalance(int id);

    void addBalance(Transfer transfer);

    void subtractBalance(Transfer transfer);
}
