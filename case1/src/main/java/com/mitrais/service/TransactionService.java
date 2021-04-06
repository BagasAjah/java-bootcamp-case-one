package com.mitrais.service;

import com.mitrais.model.AccountInfo;
import com.mitrais.util.DelayUtils;

import java.util.Map;
import java.util.Scanner;

public class TransactionService implements IService {

    private boolean isExit;
    private AccountInfo accountInfo;
    private UserService userService;

    public TransactionService(AccountInfo accountInfo, UserService userService) {
        this.isExit = false;
        this.accountInfo = accountInfo;
        this.userService = userService;
    }

    @Override
    public void display() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("1. Withdraw");
        System.out.println("2. Fund Transfer");
        System.out.println("3. Exit");
        System.out.print("Please choose option[3]: ");
    }

    @Override
    public boolean process(Scanner scanner) {
        final String WITHDRAW_TYPE = "1";
        final String FUND_TRANSFER_TYPE = "2";
        final String EXIT_TYPE = "3";
        while (!this.isExit) {
            this.display();
            String transactionType = scanner.nextLine();
            if (WITHDRAW_TYPE.equals(transactionType)) {
                IService withdrawService = new WithdrawService(this.accountInfo, this.userService);
                if (!withdrawService.process(scanner)) {
                    this.isExit = true;
                }
            }
            if (FUND_TRANSFER_TYPE.equals(transactionType)) {
                IService fundTransferService = new FundTransferService(this.accountInfo, this.userService);
                fundTransferService.process(scanner);
            }
            if (transactionType.length() == 0 || EXIT_TYPE.equals(transactionType)) {
                break;
            }
        }
        return true;
    }
}
