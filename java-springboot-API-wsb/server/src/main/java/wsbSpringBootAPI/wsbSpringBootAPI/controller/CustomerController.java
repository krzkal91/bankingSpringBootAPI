package wsbSpringBootAPI.wsbSpringBootAPI.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import wsbSpringBootAPI.wsbSpringBootAPI.entities.Account;
import wsbSpringBootAPI.wsbSpringBootAPI.entities.Customer;
import wsbSpringBootAPI.wsbSpringBootAPI.service.BankService;
import wsbSpringBootAPI.wsbSpringBootAPI.service.Transfer;
import wsbSpringBootAPI.wsbSpringBootAPI.viewObjects.FullAccountInfo;

import java.util.List;

@RestController
@RequestMapping("")
public class CustomerController {

    @Autowired
    private BankService bankService;

    @GetMapping("")
    public Welcome welcome() {
        Welcome welcome = new Welcome();
        return welcome;
    }

    @PostMapping("/customers")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Customer add(@RequestBody Customer customer) {
        return bankService.createCustomer(customer);
    }

    @GetMapping("/customers")
    public List<Customer> getAllCustomers() {
        return bankService.getAllCustomers();
    }

    @GetMapping(value = "/customers/{id}")
    public Customer getOneCustomer(@PathVariable String id) {
        Customer customer = bankService.getCustomer(id);
        return customer;
    }


    @PutMapping(value = "/customers/{id}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public Customer updateCustomer(@PathVariable String id, @RequestBody Customer customerToUpdate) {
        Customer customer = bankService.updateCustomer(id, customerToUpdate);
        return customer;
    }

    @DeleteMapping(value = "/customers/{id}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void deleteCustomer(@PathVariable String id) {
        Customer customer = bankService.deleteCustomer(id);
    }

    @GetMapping("/searchByEmail")
    public Customer searchByEmail(@RequestParam(name ="email") String email) {
        Customer customer = bankService.searchByEmail(email);
        return customer;
    }

    @GetMapping("/customers/accounts")
    public List<Account> getAllAccounts() {
        return bankService.getAllAccounts();
    }

    @GetMapping("/customers/accounts/{id}")
    public Account getOneAccount(@PathVariable String id) {
        Account account = bankService.getAccount(id);
        return account;
    }

    @PutMapping("/customers/accounts/{id}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public Account updateAccount(@PathVariable String id, @RequestBody Account accountToUpdate) {
        Account account = bankService.updateAccount(id, accountToUpdate);
        return account;

    }

    @PostMapping("/customers/accounts")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Account add(@RequestBody Account account) {
        return bankService.createAccount(account);
    }

    @DeleteMapping("/customers/accounts/{id}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void deleteAccount(@PathVariable String id) {
        bankService.deleteAccount(id);
    }

    @PutMapping("/customers/accounts/transferFunds")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void transferFunds(@RequestBody Transfer transfer) {
            bankService.transferFunds(transfer);
    }

    @GetMapping("/customers/accounts/{id}/fullinfo")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public FullAccountInfo getFullAccountInfo(@PathVariable String id) { FullAccountInfo fullAccountInfo = bankService.getFullAccountInfo(id); return fullAccountInfo;}

}
