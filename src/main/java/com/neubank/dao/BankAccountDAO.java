package com.neubank.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.neubank.models.BankAccount;
import com.neubank.models.VendorAccount;

public class BankAccountDAO {

    JdbcTemplate template;

    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    public double checkBalance(Long account_id)
            throws SQLException {
        String sql
                = "SELECT ROUND(COALESCE(SUM(credit), 0) - COALESCE(SUM(debit), 0), 2) AS balance FROM transactions WHERE account_id = ?";
        return template.queryForObject(sql, Double.class, account_id);
    }

    public List<BankAccount> getAccountsByUserId(Long userId)
            throws SQLException {
        String sql
                = "SELECT A.*, ROUND(COALESCE(SUM(T.credit), 0) - COALESCE(SUM(T.debit), 0), 2) AS balance FROM accounts A LEFT JOIN transactions T ON A.id = T.account_id WHERE A.user_id = ? GROUP BY A.id;";
        return template.query(
                sql, new BeanPropertyRowMapper(BankAccount.class), userId);
    }

    public BankAccount getAccountById(Long id)
            throws SQLException {
        String sql
                = "select * from accounts where id = id limit 1";
        return template.queryForObject(
                sql, BankAccount.class, id);
    }

    public List<VendorAccount> getVendorAccounts()
            throws SQLException {
        String sql
                = "SELECT A.*, C.fullname as fullname FROM accounts A JOIN customers C ON A.user_id = C.id WHERE A.type = 'current'";
        return template.query(
                sql, new BeanPropertyRowMapper(VendorAccount.class));
    }
}
