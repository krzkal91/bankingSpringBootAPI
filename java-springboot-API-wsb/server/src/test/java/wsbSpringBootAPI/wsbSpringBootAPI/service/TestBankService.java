package wsbSpringBootAPI.wsbSpringBootAPI.service;


import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import wsbSpringBootAPI.wsbSpringBootAPI.entities.Account;
import wsbSpringBootAPI.wsbSpringBootAPI.entities.Customer;
import wsbSpringBootAPI.wsbSpringBootAPI.exception.ResourceNotFound;
import wsbSpringBootAPI.wsbSpringBootAPI.exception.TransferException;
import wsbSpringBootAPI.wsbSpringBootAPI.repository.AccountsRepository;
import wsbSpringBootAPI.wsbSpringBootAPI.repository.CustomerRepository;

import java.util.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
public class TestBankService {

    @InjectMocks
    private BankService bankService;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private AccountsRepository accountsRepository;

    private Date date;


    @Test
    @Order(1)
    void test_createCustomer() {
        Customer customer = new Customer();
        date = new Date();
        customer.setEmail(String.format("mariano.italiano@%d.it", date.getTime()));
        customer.setFirstname("Mariano");
        customer.setLastname("Italiano");
        customer.setAccountSet(new HashSet<Account>());

        Customer mockCustomer = new Customer();
        mockCustomer.setEmail(String.format("natalie.malarie@%d.it", date.getTime()));
        mockCustomer.setFirstname("Natalie");
        mockCustomer.setLastname("Malarie");
        mockCustomer.setAccountSet(new HashSet<Account>());

        Mockito.when(bankService.createCustomer(customer)).thenReturn(mockCustomer);
        Customer customer1 = bankService.createCustomer(customer);
        System.out.println(customer1);
        Mockito.verify(customerRepository, Mockito.atLeastOnce()).save(customer);
    }

    @Test
    @Order(2)
    void test_getOneCust() {
        Customer customer = new Customer();
        date = new Date();
        customer.setEmail(String.format("mario.puzo@%d.it", date.getTime()));
        customer.setId("1");
        customer.setFirstname("Mario");
        customer.setLastname("Puzo");
        customer.setAccountSet(new HashSet<Account>());
        Optional<Customer> customers = Optional.of(customer);
        Mockito.when(customerRepository.findById("1")).thenReturn(customers);
        Customer customer1 = bankService.getCustomer("1");

        Customer savedCustomer = customers.get();

        System.out.println(savedCustomer.getId() + ":::::" + savedCustomer.getEmail());
        Assertions.assertEquals( "1", customer1.getId());
    }

    @Test
    @Order(3)
    void test_getAllCustomers() {
        List<Customer> customers = new ArrayList<>();

        for(int i = 0; i < 5; i++){
            Customer customer = new Customer();
            date = new Date();
            customer.setEmail(String.format("mario.puzo@%d.it", date.getTime()));
            customer.setId(String.valueOf(i));
            customer.setFirstname("Mario");
            customer.setLastname("Puzo");
            customer.setAccountSet(new HashSet<Account>());
            customers.add(customer);
        }

        Mockito.when(customerRepository.findAll()).thenReturn(customers);

        List<Customer> checkList = bankService.getAllCustomers();

        for(Customer customer: checkList) {
            System.out.println(customer.getId() + " ...:::::... " + customer.getEmail());
        }
        Assertions.assertEquals("2", checkList.get(2).getId());
        Assertions.assertEquals("4", checkList.get(4).getId());
        Assertions.assertEquals("0", checkList.get(0).getId());

    }
    @Test
    @Order(4)
    void test_createAccount() {
        Account account = new Account();
        date = new Date();
        account.setBalance(2000.0);
        account.setId("12345");
        account.setAccountNumber(12345);
        Customer customer = new Customer();
        customer.setId("654321");
        Set<Account> accountSet = new HashSet<Account>();
        accountSet.add(account);
        customer.setAccountSet(accountSet);

        Account account1 = new Account();
        date = new Date();
        account1.setBalance(500.0);
        account1.setId("54321");
        account1.setAccountNumber(12345);

        Mockito.when(accountsRepository.save(account)).thenReturn(account1);

        Account a = bankService.createAccount(account);

        Assertions.assertEquals(a, account1);
        Assertions.assertNotEquals(account, a);

    }

    @Test
    @Order(5)
    void test_getAllAccounts() throws InterruptedException {
        List<Account> accounts = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            Account account = new Account();
            date = new Date();
            account.setId(String.valueOf(i));
            if (i % 2 == 0) {
                account.setAccountType("CURRENT");
                account.setBalance(200.20);

            } else {
                account.setAccountType("SAVINGS");
                account.setBalance(100.10);
            }
            account.setAccountNumber(i);
            accounts.add(account);
            Thread.sleep(200);
        }

        Mockito.when(accountsRepository.findAll()).thenReturn(accounts);

        List<Account> checkList = bankService.getAllAccounts();
        int j = 0;
        for (Account a : checkList) {
            System.out.println(a);
            Assertions.assertEquals(a, accounts.get(j));
            j++;
        }
    }

    @Test
    @Order(6)
    void test_transferFunds() {

        Account account = new Account();
        account.setAccountNumber(123);
        account.setBalance(1111.11);
        Double newBalance = 2111.11;
        account.setId("123");
        Optional<Account> op = Optional.of(account);

        Account account1 = new Account();
        account1.setAccountNumber(321);
        account1.setBalance(2222.22);
        Double newBalance1 = 1222.22;
        account1.setId("321");
        Optional<Account> op1= Optional.of(account1);

        Mockito.when(accountsRepository.findAccountByAccountNumber(123)).thenReturn(op);
        Mockito.when(accountsRepository.findAccountByAccountNumber(321)).thenReturn(op1);
        Mockito.when(accountsRepository.save(account)).thenReturn(account);
        Mockito.when(accountsRepository.save(account1)).thenReturn(account1);

        Transfer transfer = new Transfer();
        transfer.setFromAccount(account1.getAccountNumber());
        transfer.setToAccount(account.getAccountNumber());
        transfer.setAmount(1000.00);


        bankService.transferFunds(transfer);

        Assertions.assertTrue(account.getBalance() >= 2111.10 && account.getBalance() <= 2111.12);
        Assertions.assertTrue(account1.getBalance() >= 1222.21 && account1.getBalance() <= 1222.23);
    }

    @Test
    @Order(7)
    void test_transferFunds2() {
        Account account = new Account();
        account.setAccountNumber(123);
        account.setBalance(1111.11);
        Double newBalance = 2111.11;
        account.setId("123");
        Optional<Account> op = Optional.of(account);

        Account account1 = new Account();
        account1.setAccountNumber(321);
        account1.setBalance(2222.22);
        Double newBalance1 = 1222.22;
        account1.setId("321");
        Optional<Account> op1 = Optional.of(account1);

        Mockito.when(accountsRepository.findAccountByAccountNumber(123)).thenReturn(op);
        Mockito.when(accountsRepository.findAccountByAccountNumber(321)).thenReturn(op1);

        Transfer transfer = new Transfer();
        transfer.setFromAccount(account.getAccountNumber());
        transfer.setToAccount(account1.getAccountNumber());
        transfer.setAmount(10000.00);

        Assertions.assertThrows(TransferException.class, () -> {
            bankService.transferFunds(transfer);
        });

        transfer.setFromAccount(2314);
        transfer.setToAccount(account1.getAccountNumber());
        transfer.setAmount(100.00);
        Assertions.assertThrows(ResourceNotFound.class, () -> {
            bankService.transferFunds(transfer);
        });
    }
}
