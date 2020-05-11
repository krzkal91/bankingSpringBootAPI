package datahill.com.bankingAPITest.entities;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import datahill.com.bankingAPITest.repository.CustomerRepository;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Objects;
import java.util.Set;

@Document(collection = "accounts")
public class Account {



    @Id
    private String id;

    @Indexed(unique = true)
    @Field("accountNumber")
    private Integer accountNumber;

    @DBRef(lazy = true)
    @Field("customerSet")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    private Customer customer;

    @Field("accountType")
    private String accountType;

    @Field("balance")
    private Double balance;

    public Integer getAccountNumber() {
        return accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String type) {
        if(type.toUpperCase().equals("SAVINGS")) {
            this.accountType = type.toUpperCase();
        } else {
            this.accountType = "CURRENT";
        }

    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }


    public void transferToAccount(Account target, Double amount) {
        this.balance -= amount;
        target.balance += amount;

    }

    public void setAccountNumber(Integer accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        if (customer != null) {
            String presentation = String.format(":::ACCOUNT | ID: %s | ACCOUNT NUMBER: %d |  CUSTOMER OWNING: %s | ACCOUNT TYPE: %s | BALANCE: %f :::", id, accountNumber, customer.getEmail(), accountType, balance);
            return presentation;
        } else {
            String presentation = String.format(":::ACCOUNT | ID: %s | ACCOUNT NUMBER: %d |  CUSTOMER OWNING: %s | ACCOUNT TYPE: %s | BALANCE: %f :::", id, accountNumber, "null", accountType, balance);
            return presentation;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return id.equals(account.id) &&
                accountNumber.equals(account.accountNumber) &&
                Objects.equals(customer, account.customer) &&
                accountType.equals(account.accountType) &&
                balance.equals(account.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountNumber, customer, accountType, balance);
    }
}
