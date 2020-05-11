package datahill.com.bankingAPITest.controller;

import datahill.com.bankingAPITest.entities.Account;
import datahill.com.bankingAPITest.entities.Customer;
import datahill.com.bankingAPITest.exception.ResourceNotFound;
import datahill.com.bankingAPITest.service.BankService;
import datahill.com.bankingAPITest.service.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private BankService bankService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Customer add(@RequestBody Customer customer) {
        return bankService.createCustomer(customer);
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return bankService.getAllCustomers();
    }

    @GetMapping(value = "/{id}")
    public Customer getOneCustomer(@PathVariable String id) {
        Customer customer = bankService.getCustomer(id);
        return customer;
    }


    @PutMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public Customer updateCustomer(@PathVariable String id, @RequestBody Customer customerToUpdate) {
        Customer customer = bankService.updateCustomer(id, customerToUpdate);
        return customer;
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void deleteCustomer(@PathVariable String id) {
        Customer customer = bankService.deleteCustomer(id);
    }

    @GetMapping("/searchByEmail")
    public Customer searchByEmail(@RequestParam (name ="email") String email) {
        Customer customer = bankService.searchByEmail(email);
        return customer;
    }

    @GetMapping("/accounts")
    public List<Account> getAllAccounts() {
        return bankService.getAllAccounts();
    }

    @GetMapping("/accounts/{id}")
    public Account getOneAccount(@PathVariable String id) {
        Account account = bankService.getAccount(id);
        return account;
    }

    @PutMapping("/accounts/{id}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public Account updateAccount(@PathVariable String id, @RequestBody Account accountToUpdate) {
        Account account = bankService.updateAccount(id, accountToUpdate);
        return account;

    }

    @PostMapping("/accounts")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Account add(@RequestBody Account account) {
        return bankService.createAccount(account);
    }

    @DeleteMapping("/accounts/{id}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void deleteAccount(@PathVariable String id) {
        Account account = bankService.deleteAccount(id);
    }

    @PutMapping("/accounts/transferFund")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void transferFunds(@RequestBody Transfer transfer) {
            bankService.transferFunds(transfer);
    }

}
