package wsbSpringBootAPI.wsbSpringBootAPI.integration;


import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.RequestBuilder;
import wsbSpringBootAPI.wsbSpringBootAPI.entities.Account;
import wsbSpringBootAPI.wsbSpringBootAPI.entities.Customer;
import wsbSpringBootAPI.wsbSpringBootAPI.repository.AccountsRepository;
import wsbSpringBootAPI.wsbSpringBootAPI.repository.CustomerRepository;
import wsbSpringBootAPI.wsbSpringBootAPI.service.Transfer;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BankAPIApplicationIntegrationTests {

    RequestBuilder requestBuilder;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AccountsRepository accountsRepository;
    @Autowired
    private CustomerRepository customerRepository;

    private Date date;

    private String body;

    @Test
    @Order(1)
    public void test_createCustomer() {
        Customer customer = new Customer();
        String id = "12345";
        date = new Date();
        customer.setEmail("test.test@"+ date.getTime() +".pl");
        customer.setFirstname("Test");
        customer.setLastname("Test");
        customer.setId(id);
        Set<Account> accountSet = new HashSet<>();
        customer.setAccountSet(accountSet);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json");

        HttpEntity<Customer> request = new HttpEntity(customer, httpHeaders);
        ResponseEntity<String> response = this.restTemplate.postForEntity("/customers", request, String.class);

        Assertions.assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    @Order(2)
    public void test_createAccount() {
        Account account = new Account();
        account.setId("67890");
        account.setBalance(2234.04);
        account.setAccountType("CURRENT");
        account.setAccountNumber(23456);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json");

        HttpEntity<Account> request1 = new HttpEntity(account, httpHeaders);
        ResponseEntity<String> responseEntity = this.restTemplate.postForEntity("/customers/accounts", request1, String.class);

        Assertions.assertEquals(201, responseEntity.getStatusCodeValue());

        body = responseEntity.getBody();
    }



    @Test
    @Order(3)
    public void test_deleteCustomer() {
        List<Customer> listofAll = customerRepository.findAll();
        Customer customer = listofAll.get(0);
        String id = customer.getId();
        String path = "/customers/" + id;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json");

        HttpEntity<Customer> request1 = new HttpEntity(customer, httpHeaders);
        this.restTemplate.delete(path);
    }

    @Test
    @Order(4)
    public void test_deleteAccount() {
        List<Account> listofAll = accountsRepository.findAll();
        Account account = listofAll.get(0);
        String id = account.getId();
        String path = "/customers/accounts/" + id;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json");

        HttpEntity<Account> request1 = new HttpEntity(account, httpHeaders);
        this.restTemplate.delete(path);
    }

    @Test
    @Order(5)
    public void test_transferFunds() {
        Account account = new Account();
        account.setBalance(2234.04);
        account.setAccountType("CURRENT");
        account.setAccountNumber(1234);


        Account account1 = new Account();
        account1.setBalance(2234.04);
        account1.setAccountType("CURRENT");
        account1.setAccountNumber(23456);

        String id1 = accountsRepository.save(account).getId();
        String id2 = accountsRepository.save(account1).getId();

        Transfer transfer = new Transfer();
        transfer.setFromAccount(1234);
        transfer.setToAccount(23456);
        transfer.setAmount(200.00);

        String path = "/customers/accounts/transferFunds";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json");

        HttpEntity<Account> request1 = new HttpEntity(transfer, httpHeaders);
        this.restTemplate.put(path, transfer);

        Double balance1 = accountsRepository.findAccountByAccountNumber(account.getAccountNumber()).get().getBalance();
        Double balance2 = accountsRepository.findAccountByAccountNumber(account1.getAccountNumber()).get().getBalance();

        System.out.println(String.format("Balance1 before: %f, balance1 after: %f. Balance2 before: %f, balance2 after: %f.", account.getBalance(), balance1, account1.getBalance(), balance2));

        Assertions.assertTrue(balance1 == 2034.040000);
        Assertions.assertTrue(balance2 == 2434.040000);
    }


    @Test
    @Order(6)
    public void cleanup() {
        accountsRepository.deleteAll();
        customerRepository.deleteAll();
    }
}
