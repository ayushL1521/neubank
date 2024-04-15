package com.neubank.dao;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;

import com.neubank.models.Transactions;

public class TransactionDAO {

    JdbcTemplate template;

    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    public List<Transactions> getAllTransactions()
            throws SQLException {
        String sql
                = "select * from transactions";
        return template.query(sql, new BeanPropertyRowMapper(Transactions.class));
    }

    public List<Transactions> getTransactionsByAccountId(Long accountId)
            throws SQLException {
        String sql
                = "select * from transactions where account_id = ?";
        return template.query(
                sql, new BeanPropertyRowMapper(Transactions.class), accountId);
    }

    public void depositAmount(Long account_id, double amount)
            throws SQLException {
        String sql
                = "insert into transactions(account_id, credit) values(" + account_id + "," + amount + ")";
        template.execute(sql);

    }

    public void withdrawAmount(Long account_id, double amount)
            throws SQLException {
        String sql
                = "insert into transactions(account_id, debit) values(" + account_id + "," + amount + ")";
        template.execute(sql);
    }

    public boolean transferAmount(Long from_account_id, Long to_account_id, double amount) throws SQLException {
        String sql = "{CALL TransferAmount (?, ?, ?, ?)}";

        CallableStatementCreator csc = connection -> {
            CallableStatement cs = connection.prepareCall(sql);
            cs.setLong(1, from_account_id);
            cs.setLong(2, to_account_id);
            cs.setDouble(3, amount);
            cs.registerOutParameter(4, java.sql.Types.TINYINT); // Output parameter for success indicator
            return cs;
        };

        return template.execute(csc, (CallableStatement cs) -> {
            cs.execute();
            return cs.getBoolean(4); // Retrieve the success indicator from the output parameter
        });
    }

}
