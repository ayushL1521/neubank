package com.neubank.models;

public class VendorAccount {

    private Long id;
    private Long user_id;
    private String type;
    private double balance;
    private int branch_id;
    private String fullname;

    public VendorAccount() {
    }

    public VendorAccount(Long id, Long user_id, String type, double balance, int branch_id, String fullname) {
        this.id = id;
        this.user_id = user_id;
        this.type = type;
        this.balance = balance;
        this.branch_id = branch_id;
        this.fullname = fullname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(int branch_id) {
        this.branch_id = branch_id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

}
