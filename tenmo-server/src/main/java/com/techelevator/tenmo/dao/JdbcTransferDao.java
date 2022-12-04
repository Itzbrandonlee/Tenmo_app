package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class JdbcTransferDao implements TransferDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcTransferDao (JdbcTemplate jdbcTemplate){this.jdbcTemplate = jdbcTemplate;}

    @Override
    public List<Transfer> list() {
        List<Transfer> listAll = new ArrayList<>();
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount FROM transfer";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);

        while(results.next()){
            Transfer transfer = mapRowToTransfer(results);
            listAll.add(transfer);
        }
        return listAll;
    }
    //added
    @Override
    public List<Transfer> listByType(int typeId) {
        List<Transfer> listAll = new ArrayList<>();
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount FROM transfer WHERE transfer_type_id = ?";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, typeId);

        while(results.next()){
            Transfer transfer = mapRowToTransfer(results);
            listAll.add(transfer);
        }
        return listAll;
    }
    //added
    @Override
    public List<Transfer> listByStatus(int statusId) {
        List<Transfer> listAll = new ArrayList<>();
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount FROM transfer WHERE transfer_status_id = ?";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, statusId);

        while(results.next()){
            Transfer transfer = mapRowToTransfer(results);
            listAll.add(transfer);
        }
        return listAll;
    }
//updated

    @Override
    public Transfer createTransfer(Transfer transfer){
        String createNewSql = "INSERT INTO transfer (transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount) VALUES (DEFAULT, 2, 1, ? ,?, ?)";
        jdbcTemplate.update(createNewSql, transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount());
        return transfer;
    }
    //added
    @Override
    public Transfer reqTransfer(Transfer transfer){
        String createNewSql = "INSERT INTO transfer (transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount) VALUES (DEFAULT, 1, 1, ?, ?, ?)";
        jdbcTemplate.update(createNewSql, transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount());
        return transfer;
    }

    @Override
    public Transfer transferStatusChange(Transfer newTransfer){
        String changeStatus = "UPDATE transfer SET transfer_status_id = ? WHERE transfer_id = ?";
        jdbcTemplate.update(changeStatus, newTransfer.getTransferStatusId(), newTransfer.getTransferId());
        return newTransfer;
    }

    @Override
    public Transfer getTransfer(int id){
        String getTransfer = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount FROM transfer JOIN transfer_type ON transfer.transfer_type_id = transfer_type.transfer_type_id JOIN transfer_status ON transfer.transfer_status_id = transfer_status.transfer_status_id WHERE transfer_id = ?";

        SqlRowSet results = jdbcTemplate.queryForRowSet(getTransfer, id);

        if(results.next()){
            return mapRowToTransfer(results);
        } else
            return null;
    }

    private Transfer mapRowToTransfer(SqlRowSet sql){
        Transfer transfer = new Transfer();
        transfer.setTransferId(sql.getInt("transfer_id"));
        transfer.setTransferTypeId(sql.getInt("transfer_type_id"));
        transfer.setTransferStatusId(sql.getInt("transfer_status_id"));
        transfer.setAccountFrom(sql.getInt("account_from"));
        transfer.setAccountTo(sql.getInt("account_to"));
        transfer.setAmount(sql.getBigDecimal("amount"));
        return transfer;
    }

}