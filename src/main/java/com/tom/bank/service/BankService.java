package com.tom.bank.service;

import com.tom.bank.bank.exception.BankException;
import com.tom.bank.objects.CreateAccount;
import com.tom.bank.repository.BankHibernateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankService {

    @Autowired
    private BankHibernateRepository bankRepository;



    public void createAccount(String accountNumber, String ownerName, String address, Double balance, Boolean locked) {
        CreateAccount account = new CreateAccount();
        account.setAccountNumber(accountNumber);
        account.setOwnerName(ownerName);
        account.setAddress(address);
        account.setBalance(balance);
        account.setLocked(locked);
        bankRepository.save(account);
    }

    public List<CreateAccount> accountInfo() {
        return bankRepository.findAll();
    }

    public List<CreateAccount> getBalance(String accountNumber) {
        return bankRepository.findAllByAccountNumberContaining(accountNumber);
    }

    public String getOneBalance(String accountNumber) {
        CreateAccount account = bankRepository.getByAccountNumber(accountNumber);
        return "Account balance is: " + account.getBalance();
    }

    public String deposit(CreateAccount deposit) {
        if (deposit.getBalance()<0) {
            throw new BankException("The amount cannot be negative");
        }
        CreateAccount account = bankRepository.getByAccountNumber(deposit.getAccountNumber());
        Double newBalance = deposit.getBalance() + (double) account.getBalance();
        account.setBalance(newBalance);
        bankRepository.save(account);
        return "Added " + deposit.getBalance() + " to " + deposit.getAccountNumber()
        +" new balance is " + newBalance;
    }


    public String withdraw(CreateAccount withdraw) {
        CreateAccount account = bankRepository.getByAccountNumber(withdraw.getAccountNumber());
        if (withdraw.getBalance()>account.getBalance()) {
            throw new BankException("You can't withdraw that much.");
        }
        Double newBalance = (double) account.getBalance() - withdraw.getBalance();
        account.setBalance(newBalance);
        bankRepository.save(account);
        return "Withdraw " + withdraw.getBalance() + withdraw.getAccountNumber() + " new balance is: " + newBalance;
    }
}
