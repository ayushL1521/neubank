package com.neubank.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.neubank.models.Customer;

public class CustomerDAO {

    JdbcTemplate template;

    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    public List<Customer> getCustomersByName(String name)
            throws SQLException {
        String sql
                = "select * from customers where name=?";
        return template.query(sql, new BeanPropertyRowMapper(Customer.class), name);
    }

    public List<Customer> getCustomerById(Long id)
            throws SQLException {
        String sql
                = "select id, fullname, username, password='' from customers where id = ? limit 1";
        return template.query(
                sql, new BeanPropertyRowMapper(Customer.class), id);
    }

    public Customer getCustomerByUsernamePassword(String username, String password) throws SQLException {
        String sql = "SELECT id, fullname, username, password FROM customers WHERE username=? AND password=? LIMIT 1";
        List<Customer> customers = template.query(sql, new Object[]{username, password}, new BeanPropertyRowMapper(Customer.class));
        return customers.isEmpty() ? null : customers.get(0);
    }

    public Customer createCustomer(String fullname, String username, String password)
            throws SQLException {
        String sql
                = "insert into customers(fullname, username, password) values(?,?,?)";
        return template.queryForObject(
                sql, Customer.class, fullname, username, password);
    }

    /**
     * @Autowired @PersistenceUnit private EntityManagerFactory
     * entityManagerFactory;
     *
     * @Autowired
     * @PersistenceContext private EntityManager entityManager;
     *
     * public CustomerDAO() { entityManagerFactory =
     * Persistence.createEntityManagerFactory("default"); entityManager =
     * entityManagerFactory.createEntityManager(); }
     *
     * public void close() { entityManagerFactory.close();
     * entityManager.close(); }
     *
     * public void create(Customer customer) { // entityManager =
     * entityManagerFactory.createEntityManager();
     * entityManager.getTransaction().begin(); entityManager.persist(customer);
     * entityManager.getTransaction().commit(); entityManager.close(); }
     *
     * public Customer findById(Long id) { // entityManager =
     * entityManagerFactory.createEntityManager(); Customer customer =
     * entityManager.find(Customer.class, id); entityManager.close(); return
     * customer; }
     *
     * public List<Customer> findAll() { // entityManager =
     * entityManagerFactory.createEntityManager(); List<Customer> customers =
     * entityManager.createQuery("SELECT c FROM customers c",
     * Customer.class).getResultList(); entityManager.close(); return customers;
     * }
     *
     * public void update(Customer customer) { // entityManager =
     * entityManagerFactory.createEntityManager();
     * entityManager.getTransaction().begin(); entityManager.merge(customer);
     * entityManager.getTransaction().commit(); entityManager.close(); }
     *
     * public void delete(Long id) { // entityManager =
     * entityManagerFactory.createEntityManager();
     * entityManager.getTransaction().begin(); Customer customer =
     * entityManager.find(Customer.class, id); if (customer != null) {
     * entityManager.remove(customer); }
     * entityManager.getTransaction().commit(); entityManager.close(); }
     *
     * public void setEntityManager(EntityManager em) { entityManager = em; }
     *
     * public Customer findUserByUsernamePassword(CustomerRequest
     * customerRequest) {
     *
     * Customer customer = entityManager.createQuery("SELECT c FROM customers c
     * WHERE c.username = :username AND c.password = :password LIMIT 1",
     * Customer.class) .setParameter("username", customerRequest.getUsername())
     * .setParameter("password",
     * customerRequest.getPassword()).getSingleResult();
     *
     * entityManager.close(); return customer; }
     */
}
