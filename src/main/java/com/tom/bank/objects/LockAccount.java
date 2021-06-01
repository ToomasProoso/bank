package com.tom.bank.objects;

import javax.persistence.*;

@Entity
@Table(name = "create_account")
public class LockAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private boolean locked;
    private String accountNumber;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }
}
