package com.tom.bank.service;

import com.tom.bank.bank.exception.BankException;
import com.tom.bank.objects.CreateAccount;
import com.tom.bank.objects.LockAccount;
import com.tom.bank.objects.TransactionHistory;
import com.tom.bank.objects.TransferRequest;
import com.tom.bank.repository.BankHibernateRepository;
import com.tom.bank.repository.TransactionRepository;
import liquibase.pro.packaged.B;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BankService {

    @Autowired
    private BankHibernateRepository bankRepository;

    @Autowired
    private TransactionRepository transactionRepository;


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
        CreateAccount dbBlocked = bankRepository.getByAccountNumber(accountNumber);
        if (dbBlocked.isLocked()) {
            throw new BankException("Account is Locked");
        } else {
            CreateAccount account = bankRepository.getByAccountNumber(accountNumber);
            return "Account balance is: " + account.getBalance();
        }
    }


    public String deposit(CreateAccount deposit) {
        CreateAccount dbBlocked = bankRepository.getByAccountNumber(deposit.getAccountNumber());
        if (dbBlocked.isLocked()) {
            throw new BankException("Account is Locked");
        } else {
            if (deposit.getBalance() < 0) {
                throw new BankException("The amount cannot be negative");
            }
            CreateAccount account = bankRepository.getByAccountNumber(deposit.getAccountNumber());
            Double newBalance = deposit.getBalance() + (double) account.getBalance();
            account.setBalance(newBalance);
            bankRepository.save(account);
            TransactionHistory history = new TransactionHistory();
            history.setTime(LocalDateTime.now());
            history.setDeduction(deposit.getBalance());
            history.setFromAccount(deposit.getAccountNumber());
            transactionRepository.save(history);
            return "Added " + deposit.getBalance() + " to " + deposit.getAccountNumber()
                    + " new balance is " + newBalance;
        }
    }


    public String withdraw(CreateAccount withdraw) {
        CreateAccount dbBlocked = bankRepository.getByAccountNumber(withdraw.getAccountNumber());
        if (dbBlocked.isLocked()) {
            throw new BankException("Account is Locked");
        } else {
            CreateAccount account = bankRepository.getByAccountNumber(withdraw.getAccountNumber());
            if (withdraw.getBalance() > account.getBalance()) {
                throw new BankException("You can't withdraw that much.");
            }
            Double newBalance = (double) account.getBalance() - withdraw.getBalance();
            account.setBalance(newBalance);
            bankRepository.save(account);
            TransactionHistory history = new TransactionHistory();
            history.setTime(LocalDateTime.now());
            history.setDeduction(withdraw.getBalance());
            history.setFromAccount(withdraw.getAccountNumber());
            transactionRepository.save(history);
            return "Withdraw " + withdraw.getBalance() + withdraw.getAccountNumber() + " new balance is: " + newBalance;
        }
    }

    @Transactional
//transfer meetodil saab @Transactional viitega hibernateRepository.save(account);ära kustutada,
//kui panna classi ette siis võib kõigil meetoditel savei ära kustutada

    public String transfer(TransferRequest transfer) {
        CreateAccount dbBlocked = bankRepository.getByAccountNumber(transfer.getAccountNumber());
        if (dbBlocked.isLocked()) {
            throw new BankException("Account is Locked");
        } else {
            CreateAccount account = bankRepository.getByAccountNumber(transfer.getAccountNumber());
            Double balance = (double) account.getBalance() - transfer.getBalance();
            account.setBalance(balance);
            bankRepository.save(account);
            if (balance < 0) {
                throw new BankException("You can't transfer that much.");
            }
            CreateAccount account1 = bankRepository.getByAccountNumber(transfer.getAccountNumber1());
            Double balance1 = (double) account1.getBalance() + transfer.getBalance();
            account1.setBalance(balance1);
            bankRepository.save(account1);
            TransactionHistory history = new TransactionHistory();
            history.setTime(LocalDateTime.now());
            history.setDeduction(transfer.getBalance());
            history.setTransfer(transfer.getBalance());
            history.setFromAccount(transfer.getAccountNumber());
            history.setToAccount(transfer.getAccountNumber1());
            transactionRepository.save(history);
            return "You transfer form account " + transfer.getAccountNumber() + " new balance is: " + balance +
                    " and account " + transfer.getAccountNumber1() + " new balance is " + balance1;
        }
    }

    public List<TransactionHistory> allHistory() {
        return transactionRepository.findAll();
    }

    public List<TransactionHistory> searchHistory(String fromAccount) {
        return transactionRepository.findAllByFromAccountContaining(fromAccount);
    }

    public void deleteAllHistory() {
        transactionRepository.deleteAll();
    }


    public void blockAccount(LockAccount lockAccount) {
        CreateAccount dbBlocked = bankRepository.getByAccountNumber(lockAccount.getAccountNumber());
        Boolean lock = lockAccount.isLocked();
        dbBlocked.setLocked(lock);
        bankRepository.save(dbBlocked);

    }

    public void unBlockAccount(LockAccount lockAccount) {
        CreateAccount dbBlocked = bankRepository.getByAccountNumber(lockAccount.getAccountNumber());
        Boolean unlock = lockAccount.isLocked();
        dbBlocked.setLocked(unlock);
        bankRepository.save(dbBlocked);
    }


}
