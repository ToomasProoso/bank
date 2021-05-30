package com.tom.bank.controller;


import com.tom.bank.objects.CreateAccount;
import com.tom.bank.objects.TransactionHistory;
import com.tom.bank.objects.TransferRequest;
import com.tom.bank.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BankController {

    @Autowired
    private BankService bankService;

    //http://localhost:9090/bank/createAccount?accountNumber=EE123&ownerName=john&address=tähe6&balance=5000&locked=false
    @PostMapping("bank/createAccount")
    private void createAccount(@RequestParam("accountNumber") String accountNumber,
                               @RequestParam(value = "ownerName", required = false) String ownerName,
                               @RequestParam("address") String address,
                               @RequestParam("balance") Double balance,
                               @RequestParam(value = "locked", required = false) Boolean locked) {
        bankService.createAccount(accountNumber, ownerName, address, balance, locked);
    }

    //http://localhost:9090/bank/accountInfo
    @GetMapping("bank/accountInfo")
    private List<CreateAccount> accountInfo() {
        return bankService.accountInfo();
    }

    //http://localhost:9090/bank?accountNumber=E
    @GetMapping("bank")
    private List<CreateAccount> getBalance(@RequestParam("accountNumber") String accountNumber) {
        return bankService.getBalance(accountNumber);
    }

    //http://localhost:9090/bank/EE222
    @GetMapping("bank/{accountNumber}")
    private String getOneBalance(@PathVariable("accountNumber") String accountNumber) {
        return bankService.getOneBalance(accountNumber);
    }

//postman

    @PostMapping("bank/deposit")//Test, saab ka PostMappinguga kuigi parem on Put
    private String deposit(@RequestBody CreateAccount deposit) {
        return bankService.deposit(deposit);
    }

    @PutMapping("bank/withdraw")
    private String withdraw(@RequestBody CreateAccount withdraw) {
        return bankService.withdraw(withdraw);
    }

    //http://localhost:9090/bank/transfer
    @PutMapping("bank/transfer")
    private String transfer(@RequestBody TransferRequest transfer) {
        return bankService.transfer(transfer);
    }

    @GetMapping("bank/allHistory")
    private List<TransactionHistory> allHistory() {
        return bankService.allHistory();
    }

    //http://localhost:9090/bank/history?fromAccount=EE
    @GetMapping("bank/history")
    public List<TransactionHistory> searchHistory(@RequestParam("fromAccount") String fromAccount) {
        return bankService.searchHistory(fromAccount);
    }

    //Ei toimi veel
    @DeleteMapping("bank/deleteAllHistory")
    public void deleteAllHistory() {
        bankService.deleteAllHistory();
    }

//    @PutMapping("/blockAccount")
//    public String blockAccount(@RequestBody AccountRequest accountRequest) {
//        bankService.blockAccount(accountRequest);
//        return accountRequest.getAccountNumber() + " is blocked.";
//    }
//
//    @PutMapping("/unBlockAccount")
//    public String unBlockAccount(@RequestBody AccountRequest accountRequest) {
//        bankService.unBlockAccount(accountRequest);
//        return accountRequest.getAccountNumber() + " is unblocked.";

}
