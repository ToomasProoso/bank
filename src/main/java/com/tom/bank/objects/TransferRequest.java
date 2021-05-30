package com.tom.bank.objects;

public class TransferRequest {

    private String accountNumber;
    private String accountNumber1;
    private Double balance;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber1() {
        return accountNumber1;
    }

    public void setAccountNumber1(String accountNumber1) {
        this.accountNumber1 = accountNumber1;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
