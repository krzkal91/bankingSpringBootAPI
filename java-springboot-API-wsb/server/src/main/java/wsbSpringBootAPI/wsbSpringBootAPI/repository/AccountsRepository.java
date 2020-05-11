package wsbSpringBootAPI.wsbSpringBootAPI.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import wsbSpringBootAPI.wsbSpringBootAPI.entities.Account;

import java.util.Optional;

@Repository
public interface AccountsRepository extends MongoRepository<Account, String> {

    Optional<Account> findAccountByAccountNumber(Integer accountNumber);

    boolean existsByAccountNumber(Integer accountNumber);
}
