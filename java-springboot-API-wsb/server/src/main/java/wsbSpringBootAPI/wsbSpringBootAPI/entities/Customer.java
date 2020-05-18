package wsbSpringBootAPI.wsbSpringBootAPI.entities;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

@Document(collection = "customers")
public class Customer {

    @Id
    private String id;

    @Field("firstname")
    private String firstname;

    @Field("lastname")
    private String lastname;

    @Indexed(unique = true)
    @Field("email")
    private String email;

    @DBRef
    @Field("accountSet")
    private Set<Account> accountSet;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        if(pattern.matcher(email).matches()) {
            this.email = email;
        } else {
            this.email = "no.valid.email.provided";
        }
    }
    public Set<Account> getAccountSet() {
        return accountSet;
    }

    public void setAccountSet(Set<Account> accountSet) {
        this.accountSet = accountSet;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Account a : this.getAccountSet()) {
            sb.append(a.toString()).append("\n");
        }
        String presentation = String.format("CUSTOMER:::ID:::%s: FIRSTNAME: %s LASTNAME: %s EMAIL: %s " +
                "\n ::::::::ACCOUNTS OF %s:::::::: " +
                "\n " + sb.toString(), id, firstname, lastname, email, id);
        return presentation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        return id.equals(customer.id) &&
                firstname.equals(customer.firstname) &&
                lastname.equals(customer.lastname) &&
                email.equals(customer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstname, accountSet, lastname, email);
    }
}
