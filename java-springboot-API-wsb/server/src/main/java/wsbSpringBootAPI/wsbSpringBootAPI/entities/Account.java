package wsbSpringBootAPI.wsbSpringBootAPI.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Objects;
import java.util.Random;

@Document(collection = "accounts")
public class Account {



    @Id
    private String id;

    @Indexed(unique = true)
    @Field("accountNumber")
    private Integer accountNumber;


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
        if(accountNumber != null) {
            this.accountNumber = accountNumber;
        } else {
            Random random = new Random(234);
            this.accountNumber = random.nextInt();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
            String presentation = String.format(":::ACCOUNT | ID: %s | ACCOUNT NUMBER: %d | ACCOUNT TYPE: %s | BALANCE: %f :::", id, accountNumber, accountType, balance);
            return presentation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return id.equals(account.id) &&
                accountNumber.equals(account.accountNumber) &&
                accountType.equals(account.accountType) &&
                balance.equals(account.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountNumber, accountType, balance);
    }
}
