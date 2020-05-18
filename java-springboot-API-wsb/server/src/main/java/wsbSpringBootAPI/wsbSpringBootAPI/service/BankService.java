package wsbSpringBootAPI.wsbSpringBootAPI.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wsbSpringBootAPI.wsbSpringBootAPI.entities.Account;
import wsbSpringBootAPI.wsbSpringBootAPI.entities.Customer;
import wsbSpringBootAPI.wsbSpringBootAPI.exception.ResourceNotFound;
import wsbSpringBootAPI.wsbSpringBootAPI.exception.TransferException;
import wsbSpringBootAPI.wsbSpringBootAPI.repository.AccountsRepository;
import wsbSpringBootAPI.wsbSpringBootAPI.repository.CustomerRepository;
import wsbSpringBootAPI.wsbSpringBootAPI.viewObjects.FullAccountInfo;
import wsbSpringBootAPI.wsbSpringBootAPI.viewObjects.ViewCustomer;
import java.util.*;

@Service
public class BankService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AccountsRepository accountsRepository;

    public Customer createCustomer(Customer customer) {
        if (customer.getAccountSet() != null || customer.getAccountSet().size() > 0) {
            Set<Account> accountset = new HashSet<Account>();
            for (Account a : customer.getAccountSet()) {
                Account accountSaved = accountsRepository.save(a);
                accountset.add(accountSaved);
            }
            customer.setAccountSet(accountset);
        }
        Customer saved = customerRepository.save(customer);
        return saved;
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers;
    }


    public Customer getCustomer(String id) {
        try {
            Optional<Customer> customers = customerRepository.findById(id);
            return customers.get();

        } catch (NoSuchElementException noSuchElementException) {
            throw new ResourceNotFound("Customer with a given ID does not exist: " + id);
        }
    }

    public Customer updateCustomer(String id, Customer customerToUpdate) {
        try {
            Optional<Customer> customers = customerRepository.findById(id);
            Customer customer = customers.get();

            Set<Account> originalSet = customer.getAccountSet();
            Set<Account> savedSet = new HashSet<>();
            if (customerToUpdate.getAccountSet() != null && customerToUpdate.getAccountSet().size()> 0) {
                for (Account a : customerToUpdate.getAccountSet()) {
                    Account saved = accountsRepository.save(a);
                    savedSet.add(saved);
                }
            }
            if (originalSet != null && originalSet.size() > 0) {
                savedSet.addAll(originalSet);
                customerToUpdate.setAccountSet(savedSet);
            }

            customer.setFirstname(customerToUpdate.getFirstname());
            customer.setLastname(customerToUpdate.getLastname());
            customer.setEmail(customerToUpdate.getEmail());
            customer.setAccountSet(savedSet);
            return customerRepository.save(customer);
        } catch (NoSuchElementException noSuchElementException) {
            throw new ResourceNotFound("Customer with a given ID does not exist: " + id);
        }
    }

    public Customer deleteCustomer(String id) {
        try {
            Optional<Customer> customers = customerRepository.findById(id);
            Customer customer = customers.get();
            customerRepository.delete(customer);
            return customer;
        } catch (NoSuchElementException noSuchElementException) {
            throw new ResourceNotFound("Customer with a given ID does not exist: " + id);
        }
    }

    public Customer searchByEmail(String email) {
        try {
            Optional<Customer> customers = customerRepository.findByEmail(email);
            Customer customer = customers.get();
            return customer;
        } catch (NoSuchElementException noSuchElementException) {
            throw new ResourceNotFound("Customer with a given email does not exist: " + email);
        }
    }

    public List<Account> getAllAccounts() {
        return accountsRepository.findAll();
    }

    public Account getAccount(String id) {
        try {
            Optional<Account> accounts = accountsRepository.findById(id);
            Account account = accounts.get();
            return account;
        } catch (NoSuchElementException noSuchElementException) {
            throw new ResourceNotFound("Account with a given ID does not exist: " + id);
        }
    }

    public Account createAccount(Account account) {
        Account accountSaved = accountsRepository.save(account);
        return accountSaved;
    }

    public Account updateAccount(String id, Account accountToUpdate) {
        try {
            Optional<Account> accounts = accountsRepository.findById(id);
            Account account = accounts.get();
            account.setAccountNumber(accountToUpdate.getAccountNumber());
            account.setAccountType(accountToUpdate.getAccountType());
            account.setBalance(accountToUpdate.getBalance());
            return accountsRepository.save(account);
        } catch (NoSuchElementException noSuchElementException) {
            throw new ResourceNotFound("Account with a given ID does not exist: " + id);
        }
    }

    public Account deleteAccount(String id) {
        try {
            Optional<Account> accounts = accountsRepository.findById(id);
            Account account = accounts.get();
            List<Customer> customers = customerRepository.findAll();
            for (Customer customer : customers) {
                if(customer.getAccountSet().contains(account)) {
                    Set<Account> accountSet = customer.getAccountSet();
                    accountSet.remove(account);
                    customer.setAccountSet(accountSet);
                    customerRepository.save(customer);
                }
            }
            accountsRepository.delete(account);
            return account;
        } catch (NoSuchElementException noSuchElementException) {
            throw new ResourceNotFound("Account with a given ID does not exist: " + id);
        }
    }

    public FullAccountInfo getFullAccountInfo(String id) {
        try {
            Optional<Account> accounts = accountsRepository.findById(id);
            Account account = accounts.get();
            FullAccountInfo fullAccountInfo = new FullAccountInfo();
            fullAccountInfo.setAccount(account);
            Set<ViewCustomer> custviews = new HashSet<>();
            List<Customer> customers = customerRepository.findAll();
            for (Customer customer : customers) {
                if(customer.getAccountSet().contains(account)) {
                    ViewCustomer viewCustomer = new ViewCustomer(customer);
                    custviews.add(viewCustomer);
                }
            }
            fullAccountInfo.setCustomers(custviews);
            return fullAccountInfo;
        } catch (NoSuchElementException noSuchElementException) {
            throw new ResourceNotFound("Account with a given ID does not exist: " + id);
        }
    }

    public void transferFunds(Transfer transfer) {
        try {
            if (!transfer.getFromAccount().equals(transfer.getToAccount())) {
                Optional<Account> accounts = accountsRepository.findAccountByAccountNumber(transfer.getFromAccount());
                Account from = accounts.get();
                accounts = accountsRepository.findAccountByAccountNumber(transfer.getToAccount());
                Account to = accounts.get();
                if (from.getBalance().compareTo(transfer.getAmount()) >= 0) {
                    from.transferToAccount(to, transfer.getAmount());
                    accountsRepository.save(from);
                    accountsRepository.save(to);
                } else {
                    throw new TransferException(String.format("Insufficient funds on origin account: %s Current balance: %s, Requested amount %s", from.getAccountNumber(), from.getBalance(), transfer.getAmount()));
                }

            } else {
                throw new TransferException("Transfer accounts must be different. Requested fromAccount " + transfer.getFromAccount() + " Requested toAccount: " + transfer.getToAccount());
            }
        } catch (NullPointerException nullPointerException) {
            throw new ResourceNotFound(String.format("At least on of the accounts with the given account numbers does not exist: %d, %d", transfer.getFromAccount(), transfer.getToAccount()));
        } catch (NoSuchElementException noSuchElementException) {
            throw new ResourceNotFound(String.format("At least on of the accounts with the given account numbers does not exist: %d, %d", transfer.getFromAccount(), transfer.getToAccount()));
        }
    }
}
