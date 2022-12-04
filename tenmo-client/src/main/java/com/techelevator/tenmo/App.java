package com.techelevator.tenmo;

import com.fasterxml.jackson.databind.exc.InvalidTypeIdException;
import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.*;

import javax.naming.InvalidNameException;
import java.math.BigDecimal;
import java.util.Objects;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private AccountService accountService = new AccountService();
    private AuthenticatedUser currentUser;
    private UserService userService = new UserService();
    private TransferService transferService = new TransferService();

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }
    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }
    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        if (currentUser == null) {
            consoleService.printErrorMessage();
        }
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

	private void viewCurrentBalance() {
		// TODO Auto-generated method stub
        System.out.println("Your current account balance is: " + accountService.getBalance(currentUser.getUser().getId()));

	}

	private void viewTransferHistory() {
		// TODO Auto-generated method stub
        Transfer[] transfers = transferService.getAllTransfers();
        consoleService.viewTransfersPrompt(transfers);

        for (Transfer transfer : transfers) {
            while(accountService.getAccountId(currentUser.getUser().getId()) == transfer.getAccountFrom() || accountService.getAccountId(currentUser.getUser().getId()) == transfer.getAccountTo())
            if(transfer.getTransferTypeId() == 1) {
                System.out.println(transfer.getTransferId() + "           To: " + transfer.getAccountTo() + "        " + "$ " + transfer.getAmount());
                break;
            } else if (transfer.getTransferTypeId() == 2) {
                System.out.println(transfer.getTransferId() + "           From: " + currentUser.getUser().getUsername() + "        " + "$ " + transfer.getAmount());
                break;
            }
        }
        int transferId = consoleService.requestTransferId();
	}

	private void viewPendingRequests() {
//		// TODO Auto-generated method stub
//
        Transfer[] transfers = transferService.getPendingTransfers(currentUser);
        consoleService.viewTransfersPrompt(transfers);
        System.out.println("-------------------------------");
        System.out.println("Pending Transfers");
        System.out.println("ID          To          Amount");
        System.out.println("-------------------------------");
	}

	private void sendBucks() {
		// TODO Auto-generated method stub
        Account account = new Account();
        User[] users = userService.getAllUsers();
        consoleService.sendBucksPrompt(users);

		int getUserId = consoleService.enterUserId();
        BigDecimal amountToTransfer = consoleService.enterAmountToTransferPrompt();

        Transfer newTransfer = new Transfer();

        newTransfer.setTransferId(0);
        newTransfer.setTransferTypeId(2);

        newTransfer.setAccountFrom(accountService.getAccountId(currentUser.getUser().getId()));
        newTransfer.setAccountTo(accountService.getAccountId(getUserId));
        newTransfer.setAmount(amountToTransfer);

        transferService.newTransfer(newTransfer);
        accountService.addBalance(newTransfer);

            //throw exception for sending money to yourself
//        if (currentUser.getUser().getId() = consoleService.enterUserId())

            //add and subtract from balance
//        Account receiver = new Account();
//        receiver.setAccount_id(newTransfer.getAccountTo());
//        receiver.addBalance(amountToTransfer);
//v
//        Account sender = new Account();
//        sender.setAccount_id(newTransfer.getAccountFrom());
//        sender.subtractBalance(amountToTransfer);
        }

	private void requestBucks() {
		// TODO Auto-generated method stub
		User[] users = userService.getAllUsers();
        consoleService.sendBucksPrompt(users);
        int getUserId = consoleService.requestUserId();
        BigDecimal amountToRequest = consoleService.enterAmountToTransferPrompt();
	}
}
