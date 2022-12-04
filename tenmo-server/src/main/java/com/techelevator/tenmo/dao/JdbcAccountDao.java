package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcAccountDao implements AccountDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}
//add balance method..controller account/id/balance

    @Override
    public Account getAccountUsername(int id) {
        String getAccount = "SELECT account_id, user_id, balance FROM account WHERE account_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(getAccount, id);

        if (results.next()) {
            return mapRowToAccount(results);
        } else
            return null;
    }

    @Override
    public Account getAccountIdFromUser(int id) {
        Account accountId = new Account();
        String sqlGetId = "SELECT account_id, user_id, balance FROM account WHERE user_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sqlGetId, id);
        while (result.next()) {
            if (result == null) {
                return null;
            } else {
                accountId = mapRowToAccount(result);
            }
        }
        return accountId;
    }

    @Override
    public Account getBalance(int id) {
        Account accountBalance = new Account();
        String sqlGetBalance = "SELECT account_id, user_id, balance FROM account WHERE user_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sqlGetBalance, id);
        while (result.next()) {
            if (result == null) {
                return null;
            } else {
                accountBalance = mapRowToAccount(result);
            }
        }

        return accountBalance;
    }
//added
    @Override
    public void addBalance(Transfer transfer){
        String sqlAddBalance = "UPDATE account SET balance = balance + ? WHERE account_id = ?";
        jdbcTemplate.update(sqlAddBalance, transfer.getAmount(), transfer.getAccountTo());
    }
//added
    @Override
    public void subtractBalance(Transfer transfer){
        String sqlAddBalance = "UPDATE account SET balance = balance - ? WHERE account_id = ?";
        jdbcTemplate.update(sqlAddBalance, transfer.getAmount(), transfer.getAccountFrom());
    }

    private Account mapRowToAccount(SqlRowSet sql){
        Account account = new Account();
        account.setAccount_id(sql.getInt("account_id"));
        account.setBalance(sql.getBigDecimal("balance"));
        account.setUser_id(sql.getInt("user_id"));

        return account;
    }
}
