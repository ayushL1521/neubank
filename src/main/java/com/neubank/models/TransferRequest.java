package com.neubank.models;

public class TransferRequest {

    private String from_account_id;
    private String to_account_id;
    private double amount;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getFrom_account_id() {
        return from_account_id;
    }

    public void setFrom_account_id(String from_account_id) {
        this.from_account_id = from_account_id;
    }

    public String getTo_account_id() {
        return to_account_id;
    }

    public void setTo_account_id(String to_account_id) {
        this.to_account_id = to_account_id;
    }

    @Override
    public String toString() {
        return "TransferRequest{" + "amount=" + amount + ", from_account_id=" + from_account_id + ", to_account_id=" + to_account_id + '}';
    }

}
