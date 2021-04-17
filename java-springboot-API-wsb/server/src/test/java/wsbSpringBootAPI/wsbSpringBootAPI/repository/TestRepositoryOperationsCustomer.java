package wsbSpringBootAPI.wsbSpringBootAPI.repository;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import wsbSpringBootAPI.wsbSpringBootAPI.entities.Account;
import wsbSpringBootAPI.wsbSpringBootAPI.entities.Customer;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataMongoTest
public class TestRepositoryOperationsCustomer {

    @Autowired
    private CustomerRepository customerRepository;


    private Customer c;
    private Account a;
    private Date date;


    @Test
    @Order(1)
    public void test_createCustomer() {
        Customer customer = new Customer();
        date = new Date();
        customer.setEmail(String.format("mariano.italiano@%d.it", date.getTime()));
        customer.setFirstname("Mariano");
        customer.setLastname("Italiano");
        customer.setAccountSet(new HashSet<Account>());
        c = customerRepository.save(customer);
         System.out.println(c);

    }

    @Test
    @Order(2)
    public void test_getOneCustomer() {
        test_createCustomer();
        Customer tmp = customerRepository.findById(c.getId()).get();
        System.out.println(tmp);
    }

    @Test
    @Order(3)
    public void test_getAllCustomers() {
        List<Customer> listOfAll = customerRepository.findAll();
        for (Customer c: listOfAll) {
            System.out.println(c);
        }
    }

    @Test
    @Order(4)
    public void test_updateOneCustomer() {
        test_createCustomer();
        System.out.println("\n :::::ORIGINAL::::: \n");
        System.out.println(c);
        Customer tmp = customerRepository.findById(c.getId()).get();
        tmp.setFirstname("Wojtech");
        tmp.setLastname("Nuvak");
        tmp.setEmail("wojtech.nuvak@" + date.toString() + ".com");
        Customer tmp1 = customerRepository.save(tmp);
        System.out.println("\n :::::CHANGED TO::::: \n");
        System.out.println(tmp);
        System.out.println("\n :::::IN DATABASE IS NOW::::: \n");
        System.out.println(tmp1);
        assertFalse(c.equals(tmp) && tmp.equals(tmp1));
    }

    @Test
    @Order(5)
    public void test_deleteOneCustomer() {
        test_createCustomer();
        Customer tmp = customerRepository.findById(c.getId()).get();
        customerRepository.delete(tmp);
        assertFalse(customerRepository.existsById(tmp.getId()));
    }
/**
 * @Test
    @Order(6)
    public void cleanup() {
        customerRepository.deleteAll();
    }
 */
    



}
