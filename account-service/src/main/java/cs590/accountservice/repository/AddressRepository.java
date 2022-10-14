package cs590.accountservice.repository;

import cs590.accountservice.entity.Account;
import cs590.accountservice.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AddressRepository extends JpaRepository<Address, Integer> {
    @Query("SELECT account.shippingAddress from Account AS account where account.id=?1")
    Address findAddressByOwnerId(int id);
}
