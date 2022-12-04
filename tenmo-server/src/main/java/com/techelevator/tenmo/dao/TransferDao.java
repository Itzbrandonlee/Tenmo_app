package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDao {

    List<Transfer> list();

    List<Transfer> listByStatus(int statusId);

    List<Transfer> listByType(int typeId);

    Transfer createTransfer(Transfer transfer);

    Transfer reqTransfer(Transfer transfer);

    Transfer transferStatusChange(Transfer transfer);

    Transfer getTransfer(int id);

}
