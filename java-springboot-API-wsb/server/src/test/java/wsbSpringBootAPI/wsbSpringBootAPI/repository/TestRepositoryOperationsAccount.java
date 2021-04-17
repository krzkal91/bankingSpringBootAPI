package wsbSpringBootAPI.wsbSpringBootAPI.repository;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import wsbSpringBootAPI.wsbSpringBootAPI.entities.Account;
import wsbSpringBootAPI.wsbSpringBootAPI.entities.Customer;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertFalse;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataMongoTest
public class TestRepositoryOperationsAccount {

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private Account a;
    private Date date;
    private Random r;


    @Test
    @Order(1)
    public void test_createAccountFORCustomer() {
        Account account = new Account();
        date = new Date();
        r = new Random();
        Long timeinLong = (Long)date.getTime();
        Integer tmp = Math.abs(timeinLong.intValue());
        account.setAccountNumber(tmp);
        int i = r.nextInt();
        if(i % 2 == 0) {
            account.setAccountType("CURRENT");
        } else {
            account.setAccountType("SAVINGS");
        }
        account.setBalance(r.nextDouble());
        Customer c = new Customer();
        c.setEmail("foo.bar@" + String.valueOf(r.nextFloat()) + ".pl");
        c.setFirstname("Foo");
        c.setLastname("Bar");
        Set<Account> accountSet = new HashSet<>();
        c.setAccountSet(accountSet);
        c = customerRepository.save(c);
        a = accountsRepository.save(account);
        c = customerRepository.findByEmail(c.getEmail()).get();
        accountSet.add(a);
        c.setAccountSet(accountSet);
        System.out.println(a);
    }

    @Test
    @Order(2)
    public void test_updateAccount(){
        test_createAccountFORCustomer();
        r = new Random();
        System.out.println("\n :::::ORIGINAL::::: \n");
        System.out.println(a);
        Account account = accountsRepository.findAccountByAccountNumber(a.getAccountNumber()).get();
        account.setBalance(r.nextDouble());
        account.setAccountType("SAVINGS");
        accountsRepository.save(account);
        Account account1 = accountsRepository.findAccountByAccountNumber(a.getAccountNumber()).get();
        System.out.println("\n :::::CHANGED TO::::: \n");
        System.out.println(account);
        System.out.println("\n :::::IN DATABASE IS NOW::::: \n");
        System.out.println(account1);
        assertFalse(a.equals(account) && account.equals(account1));
    }

    @Test
    @Order(3)
    public void test_getOneAccount() {
        test_createAccountFORCustomer();
        Account account = accountsRepository.findById(a.getId()).get();
        System.out.println(account);
    }

    @Test
    @Order(4)
    public void test_getAllAccounts() {
        List<Account> listOfAll = accountsRepository.findAll();
        for (Account a : listOfAll) {
            System.out.println(a);
        }
    }

    @Test
    @Order(5)
    public void test_deleteAccount() {
        test_createAccountFORCustomer();
        accountsRepository.delete(a);
        assertFalse(accountsRepository.existsByAccountNumber(a.getAccountNumber()));
    }

/**
 * @Test
    @Order(6)
    public void cleanup() {
        customerRepository.deleteAll();
        accountsRepository.deleteAll();
    }
 */
    


}
