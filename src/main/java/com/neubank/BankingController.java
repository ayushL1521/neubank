package com.neubank;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.neubank.dao.BankAccountDAO;
import com.neubank.dao.CustomerDAO;
import com.neubank.dao.TransactionDAO;
import com.neubank.models.BankAccount;
import com.neubank.models.Customer;
import com.neubank.models.CustomerRequest;
import com.neubank.models.DepositRequest;
import com.neubank.models.TransferRequest;
import com.neubank.models.VendorAccount;
import com.neubank.models.WithdrawRequest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.core.MediaType;

@Controller
public class BankingController {

    CustomerDAO customerDao;
    BankAccountDAO bankAccountDao;
    TransactionDAO transactionDao;

    @Autowired
    public BankingController(CustomerDAO cdao, BankAccountDAO bdao, TransactionDAO tdao) {
        this.customerDao = cdao;
        this.bankAccountDao = bdao;
        this.transactionDao = tdao;
    }

    @ModelAttribute("customer-request")
    public CustomerRequest getCustomerRequest() {
        return new CustomerRequest();
    }

    @ModelAttribute("deposit-request")
    public DepositRequest getDepositRequest() {
        return new DepositRequest();
    }

    @ModelAttribute("withdraw-request")
    public WithdrawRequest getWithdrawRequest() {
        return new WithdrawRequest();
    }

    @RequestMapping("/")
    public String home(Model model, HttpServletRequest request) {
        if (request.getSession().getAttribute("login_id") != null) {
            return "redirect:/dashboard";
        }
        model.addAttribute("page_title", "Login");
        return "home";
    }

    @RequestMapping("/logout")
    public String logout(Model model, HttpServletRequest request) {
        var session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }
        model.addAttribute("page_title", "Login");
        return "redirect:/";
    }

    @RequestMapping("/dashboard")
    public String dashboard(Model model, HttpServletRequest request) {
        var loginId = request.getSession().getAttribute("login_id");
        if (loginId != null) {
            model.addAttribute("page_title", "Dashboard");
            model.addAttribute("login_id", loginId);
            return "dashboard";
        }
        model.addAttribute("page_title", "Login");
        return "redirect:/";
    }

    @RequestMapping("/deposits")
    public String deposits(Model model, HttpServletRequest request) {
        var loginId = request.getSession().getAttribute("login_id");
        if (loginId != null) {
            model.addAttribute("page_title", "Deposits");
            model.addAttribute("login_id", loginId);
            return "deposit";
        }
        model.addAttribute("page_title", "Login");
        return "redirect:/";
    }

    @RequestMapping("/withdrawal")
    public String withdrawal(Model model, HttpServletRequest request) {
        var loginId = request.getSession().getAttribute("login_id");
        if (loginId != null) {
            model.addAttribute("page_title", "Withdraw");
            model.addAttribute("login_id", loginId);
            return "withdraw";
        }
        model.addAttribute("page_title", "Login");
        return "redirect:/";
    }

    @RequestMapping("/transfers")
    public String trasnfers(Model model, HttpServletRequest request) {
        var loginId = request.getSession().getAttribute("login_id");
        if (loginId != null) {
            model.addAttribute("page_title", "Transfer");
            model.addAttribute("login_id", loginId);
            return "transfer";
        }
        model.addAttribute("page_title", "Login");
        return "redirect:/";
    }

    @RequestMapping("/billpay")
    public String billPay(Model model, HttpServletRequest request) {
        var loginId = request.getSession().getAttribute("login_id");
        if (loginId != null) {
            model.addAttribute("page_title", "Bill Pay");
            model.addAttribute("login_id", loginId);
            return "billpay";
        }
        model.addAttribute("page_title", "Login");
        return "redirect:/";
    }

    @RequestMapping(value = "/login",
            method = RequestMethod.POST)
    public ModelAndView login(@ModelAttribute("customer-request") CustomerRequest cr, HttpServletRequest request) {
        ModelAndView mav = null;
        Customer customer1;

        try {
            customer1 = customerDao.getCustomerByUsernamePassword(cr.getUsername(), cr.getPassword());

            if (null != customer1) {
                System.out.println(customer1.getId() + ">>" + customer1.getUsername());
                mav = new ModelAndView("redirect:/dashboard");
                request.getSession().setAttribute("login_id", customer1.getId());
            } else {
                mav = new ModelAndView("redirect:/");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mav;
    }

    @RequestMapping(value = "/customer/{customerId}",
            method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public Customer getCustomer(@PathVariable("customerId") Long customerId) {
        List<Customer> customer;
        try {
            customer = customerDao.getCustomerById(customerId);
            if (null != customer) {
                System.out.println(customer);
                return customer.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/accounts/{customerId}",
            method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public List<BankAccount> getAccounts(@PathVariable("customerId") Long customerId) {
        List<BankAccount> account = null;
        try {
            account = bankAccountDao.getAccountsByUserId(customerId);
            if (null != account) {
                System.out.println(account);
                return account;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

    @RequestMapping(value = "/accounts/vendors",
            method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public List<VendorAccount> getVendorAccounts() {
        List<VendorAccount> account = null;
        try {
            account = bankAccountDao.getVendorAccounts();
            if (null != account) {
                System.out.println(account);
                return account;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

    @RequestMapping(value = "/account/balance/{accountId}",
            method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public boolean checkBalance(@PathVariable("accountId") Long accountId) {
        boolean isAvailable = false;
        try {
            double balance = bankAccountDao.checkBalance(accountId);
            if (balance > 0) {
                isAvailable = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isAvailable;
    }

    @RequestMapping(value = "/deposit",
            method = RequestMethod.POST, consumes = {MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON, MediaType.WILDCARD})
    public ModelAndView deposit(@RequestBody DepositRequest cr, HttpServletRequest request) {
        ModelAndView mav = null;
        System.out.println(cr.toString());
        try {
            transactionDao.depositAmount(Long.valueOf(cr.getAccount_id()), cr.getAmount());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mav;
    }

    @RequestMapping(value = "/withdraw",
            method = RequestMethod.POST, consumes = {MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON, MediaType.WILDCARD})
    public ModelAndView withdraw(@RequestBody WithdrawRequest cr, HttpServletRequest request) {
        ModelAndView mav = null;
        System.out.println(cr.toString());
        try {
            transactionDao.withdrawAmount(Long.valueOf(cr.getAccount_id()), cr.getAmount());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mav;
    }

    @RequestMapping(value = "/transfer",
            method = RequestMethod.POST, consumes = {MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON, MediaType.WILDCARD})
    @ResponseBody
    public boolean transfer(@RequestBody TransferRequest cr, HttpServletRequest request) {
        ModelAndView mav = null;
        boolean result = false;
        try {
            result = transactionDao.transferAmount(Long.valueOf(cr.getFrom_account_id()), Long.valueOf(cr.getTo_account_id()), cr.getAmount());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
