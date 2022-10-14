package cs590.accountservice.repository;

import cs590.accountservice.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AccountRepository extends JpaRepository<Account, Integer> {
    public Account findAccountByEmailAndPassword(String email, String password);
}
