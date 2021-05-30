package com.tom.bank.objects;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction_history")
public class TransactionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer transferId;
    private String fromAccount;
    private String toAccount;
    private double transfer;
    private double deduction;
    private LocalDateTime time;

    @Override
    public String toString() {
        return "TransactionHistory{" +
                "transferId=" + transferId +
                ", fromAccount='" + fromAccount + '\'' +
                ", toAccount='" + toAccount + '\'' +
                ", transfer=" + transfer +
                ", deduction=" + deduction +
                ", time=" + time +
                '}';
    }

    public Integer getTransferId() {
        return transferId;
    }

    public void setTransferId(Integer transferId) {
        this.transferId = transferId;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }

    public double getTransfer() {
        return transfer;
    }

    public void setTransfer(double transfer) {
        this.transfer = transfer;
    }

    public double getDeduction() {
        return deduction;
    }

    public void setDeduction(double deduction) {
        this.deduction = deduction;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
