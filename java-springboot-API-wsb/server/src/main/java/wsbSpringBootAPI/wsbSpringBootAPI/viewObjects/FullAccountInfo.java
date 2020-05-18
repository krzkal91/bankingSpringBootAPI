package wsbSpringBootAPI.wsbSpringBootAPI.viewObjects;

import wsbSpringBootAPI.wsbSpringBootAPI.entities.Account;

import java.util.Set;

public class FullAccountInfo {

    private Account account;

    private Set<ViewCustomer> customers;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Set<ViewCustomer> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<ViewCustomer> customers) {
        this.customers = customers;
    }
}
