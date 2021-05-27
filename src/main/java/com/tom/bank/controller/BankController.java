package com.tom.bank.controller;


import com.tom.bank.objects.CreateAccount;
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
    @PostMapping("bank/deposit")
    private String deposit(@RequestBody CreateAccount deposit) {
        return bankService.deposit(deposit);
    }

    @PutMapping("bank/withdraw")
    private String withdraw(@RequestBody CreateAccount withdraw) {
        return bankService.withdraw(withdraw);
    }

}
