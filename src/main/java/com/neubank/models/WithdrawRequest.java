package com.neubank.models;

public class WithdrawRequest {

    private double amount;
    private String account_id;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    @Override
    public String toString() {
        return "DepositRequest{" + "amount=" + amount + ", account_id=" + account_id + '}';
    }

}
