package wsbSpringBootAPI.wsbSpringBootAPI.controller;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import wsbSpringBootAPI.wsbSpringBootAPI.entities.Account;
import wsbSpringBootAPI.wsbSpringBootAPI.entities.Customer;
import wsbSpringBootAPI.wsbSpringBootAPI.exception.ResourceNotFound;
import wsbSpringBootAPI.wsbSpringBootAPI.service.BankService;
import wsbSpringBootAPI.wsbSpringBootAPI.viewObjects.FullAccountInfo;
import wsbSpringBootAPI.wsbSpringBootAPI.viewObjects.ViewCustomer;

import java.util.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CustomerController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestCustomerController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BankService bankService;

    @Test
    @Order(1)
    void test_getAllCustomers() throws Exception {

        RequestBuilder requestBuilder;

        List<Customer> listOfAll = new ArrayList<>();
        Customer customer = new Customer();
        customer.setId("123");
        customer.setFirstname("Marian");
        customer.setLastname("Pazdzioch");
        customer.setEmail("marian.pazdzioch@mama.pl");
        Set<Account> accountSet = new HashSet<>();
        customer.setAccountSet(accountSet);
        Customer customer1 = new Customer();
        customer1.setId("321");
        customer1.setLastname("Boczek");
        customer1.setFirstname("Arnold");
        customer1.setEmail("arnold.boczek@mama.pl");
        accountSet = new HashSet<>();
        customer1.setAccountSet(accountSet);

        listOfAll.add(customer);
        listOfAll.add(customer1);
        System.out.println("Response: " + listOfAll.toString());
        Mockito.when(bankService.getAllCustomers()).thenReturn(listOfAll);

        requestBuilder= MockMvcRequestBuilders
                .get("/customers")
                .accept(MediaType.APPLICATION_JSON);
        String expected = "[{" +
                "        \"id\": \"123\"," +
                "        \"firstname\": \"Marian\"," +
                "        \"accountSet\": []," +
                "        \"lastname\": \"Pazdzioch\"," +
                "        \"email\": marian.pazdzioch@mama.pl" +
                "    }," +
                "{" +
                "        \"id\": \"321\"," +
                "        \"firstname\": \"Arnold\"," +
                "        \"accountSet\": []," +
                "        \"lastname\": \"Boczek\"," +
                "        \"email\": arnold.boczek@mama.pl" +
                "}]";

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expected))
                .andReturn();
    }

    @Test
    @Order(2)
    void test_createEmployeeAPI() throws Exception {
        RequestBuilder requestBuilder;
        Customer customer = new Customer();
        customer.setFirstname("Raj");
        customer.setLastname("Vadhran");
        customer.setEmail("raj.vadhran@mockito.com");
        Set<Account> accountSet = new HashSet<Account>();
        customer.setAccountSet(accountSet);
        Customer customer1 = customer;
        customer1.setId("123");
        Optional<Customer> optional = Optional.of(customer1);

        String request = "{\"firstname\":\"Raj\",\"accountSet\":[],\"lastname\":\"Vadhran\",\"email\":\"raj.vadhran@mockito.com\"}";
        System.out.println(request);
        Mockito.when(bankService.createCustomer(customer)).thenReturn(optional.get());

        requestBuilder = MockMvcRequestBuilders
                .post("/customers")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                ;



        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    @Order(3)
    void test_getNonExistentAccount() throws Exception {

        RequestBuilder requestBuilder;
        Optional<Account> optional = Optional.empty();
        Mockito.when(bankService.getAccount("123")).thenThrow(ResourceNotFound.class);


        requestBuilder= MockMvcRequestBuilders
                .get("/customers/accounts/123")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andExpect(status().isNotFound()).andReturn();
        System.out.println(mvcResult.getResolvedException().getMessage());
        Optional<ResourceNotFound> exception = Optional.ofNullable((ResourceNotFound) mvcResult.getResolvedException());
        exception.ifPresent( (e) -> Assertions.assertTrue((e instanceof ResourceNotFound)));


    }

    @Test
    @Order(4)
    void test_getFullAccountInfo() throws Exception {
        RequestBuilder requestBuilder;

        Account account = new Account();
        account.setAccountNumber(123456);
        account.setAccountType("CURRENT");
        account.setBalance(123948.09);
        account.setId("sjdhsu88888");

        Customer customer = new Customer();
        customer.setFirstname("Krzysztof");
        customer.setId("jshdsgd73645");
        customer.setLastname("Vadhran");
        customer.setEmail("krzysztof.vadhran@mockito.com");
        Set<Account> accountSet = new HashSet<Account>();
        accountSet.add(account);
        customer.setAccountSet(accountSet);
        ViewCustomer viewCustomer = new ViewCustomer(customer);

        Customer customer1 = new Customer();
        customer1.setId("sjkdsddhg637");
        customer1.setFirstname("Mariusz");
        customer1.setLastname("Szczygiel");
        customer1.setEmail("mariusz.szcz@mockito.com");
        accountSet = new HashSet<Account>();
        accountSet.add(account);
        customer1.setAccountSet(accountSet);
        ViewCustomer viewCustomer1 = new ViewCustomer(customer1);

        FullAccountInfo fullAccountInfo = new FullAccountInfo();
        fullAccountInfo.setAccount(account);
        Set<ViewCustomer> customerset = new HashSet<ViewCustomer>();
        customerset.add(viewCustomer);
        customerset.add(viewCustomer1);
        fullAccountInfo.setCustomers(customerset);

        Mockito.when(bankService.getFullAccountInfo("sjdhsu88888")).thenReturn(fullAccountInfo);

        requestBuilder= MockMvcRequestBuilders
                .get("/customers/accounts/sjdhsu88888/fullinfo")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isAccepted())
                .andReturn();

        System.out.println(mvcResult.toString());
    }


}
