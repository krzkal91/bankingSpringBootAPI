package wsbSpringBootAPI.wsbSpringBootAPI.viewObjects;

import wsbSpringBootAPI.wsbSpringBootAPI.entities.Customer;

public class ViewCustomer {

    private String id;

    private String firstname;

    private String lastname;

    private String email;

    public ViewCustomer() {

    }

    public ViewCustomer(Customer customer) {
        this.email = customer.getEmail();
        this.firstname = customer.getFirstname();
        this.id = customer.getId();
        this.lastname = customer.getLastname();
    }


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
        this.email = email;
    }
}
