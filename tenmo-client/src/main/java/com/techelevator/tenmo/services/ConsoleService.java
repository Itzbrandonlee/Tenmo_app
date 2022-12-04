package com.techelevator.tenmo.services;


import com.techelevator.tenmo.model.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.Scanner;

public class ConsoleService {

    private final Scanner scanner = new Scanner(System.in);
    private AuthenticatedUser currentUser;

    public int promptForMenuSelection(String prompt) {
        int menuSelection;
        System.out.print(prompt);
        try {
            menuSelection = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            menuSelection = -1;
        }
        return menuSelection;
    }

    public void printGreeting() {
        System.out.println("*********************");
        System.out.println("* Welcome to TEnmo! *");
        System.out.println("*********************");
    }

    public void printLoginMenu() {
        System.out.println();
        System.out.println("1: Register");
        System.out.println("2: Login");
        System.out.println("0: Exit");
        System.out.println();
    }

    public void printMainMenu() {
        System.out.println();
        System.out.println("1: View your current balance");
        System.out.println("2: View your past transfers");
        System.out.println("3: View your pending requests");
        System.out.println("4: Send TE bucks");
        System.out.println("5: Request TE bucks");
        System.out.println("0: Exit");
        System.out.println();
    }

    public UserCredentials promptForCredentials() {
        String username = promptForString("Username: ");
        String password = promptForString("Password: ");
        return new UserCredentials(username, password);
    }

    public String promptForString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public int promptForInt(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }

    public BigDecimal promptForBigDecimal(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return new BigDecimal(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a decimal number.");
            }
        }
    }

    public void pause() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    public void printErrorMessage() {
        System.out.println("An error occurred. Check the log for details.");
    }

    public void printBalance(){
        System.out.println("Your current account balance is: ");
    }

    public void sendBucksPrompt (User[] users) {
        System.out.println("-----------------------------------------");
        System.out.println("User ID                Name");
        System.out.println("-----------------------------------------");
        for (User user : users){
            System.out.println(user.getId() + "                   " + user.getUsername());
        }
    }

    public void viewTransfersPrompt (Transfer[] transfers) {
        AccountService accountService = new AccountService();
        System.out.println("-----------------------------------------");
        System.out.println("TransferID     Username       Amount");
        System.out.println("-----------------------------------------");
//        for (Transfer transfer : transfers) {
//            while(accountService.getAccountId(currentUser.getUser().getId()) == transfer.getAccountFrom() || accountService.getAccountId(currentUser.getUser().getId()) == transfer.getAccountTo())
//            if (transfer.getTransferTypeId() == 1) {
//                System.out.println(transfer.getTransferId() + "        To: " + transfer.getAccountTo() + "        " + "$ " + transfer.getAmount());
//            } else if (transfer.getTransferTypeId() == 2) {
//                System.out.println(transfer.getTransferId() + "        From: " + transfer.getAccountFrom() + "        " + "$ " + transfer.getAmount());
//            }
//        }
    }
    public int enterUserId (){
        int userId = promptForInt("Enter ID of the user you are sending to (0 to cancel): ");
        return userId;
    }

    public int requestUserId (){
        int userId = promptForInt("Enter ID of the user you are requesting from (0 to cancel): ");
        return userId;
    }
    public int requestTransferId (){
        int transferId = promptForInt("Please enter transfer ID to view details (0 to Cancel): ");
        return transferId;
    }

    public BigDecimal enterAmountToTransferPrompt (){
        BigDecimal amount = promptForBigDecimal("Enter amount:");
        return amount;
    }

}
