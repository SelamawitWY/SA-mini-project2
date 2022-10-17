package cs590.accountservice.service;

import cs590.accountservice.entity.*;
import cs590.accountservice.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

//    @Autowired
//    private BankPaymentRepository bankPaymentRepository;
//
//    @Autowired
//    private CCPaymentRepository ccPaymentRepository;
//
//    @Autowired
//    private PaypalPaymentRepository paypalPaymentRepository;

    @Autowired
    private AddressRepository addressRepository;

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Account findById(Integer id) {
        Optional<Account> account = accountRepository.findById(id);
        return account.isPresent() ? account.get() : null;
    }

    public Account updateAccount(Integer id, Account account) {
        Optional<Account> accountOptional= accountRepository.findById(id);
        if(accountOptional.isPresent()){
            account.setId(id);
            return accountRepository.save(account);
        }

        return accountRepository.save(account);
    }

    public boolean deleteAccount(Integer accountId) {
        Optional<Account> accountOptional =accountRepository.findById(accountId);
        if(accountOptional.isPresent()){
            accountRepository.deleteById(accountId);
            return true;
        }
        return false;
    }

    public Boolean authenticate(String email, String password) {
        Account account = accountRepository.findAccountByEmailAndPassword(email, password);
        return (account != null);
    }

    public Account getUserByEmail(String email, String password) {
        Account account = accountRepository.findAccountByEmailAndPassword(email, password);
        return account;
    }

    public PaymentMethod addBankMethod(int accountId, BankPayment newMethod) {
        Optional<Account> account = this.accountRepository.findById(accountId);
        if (account.isPresent()) {
            newMethod.setOwner(account.get());
        }
        return this.paymentMethodRepository.save(newMethod);
    }

    public PaymentMethod addCCMethod(int accountId, CCPayment newMethod) {
        Optional<Account> account = this.accountRepository.findById(accountId);
        if (account.isPresent()) {
            newMethod.setOwner(account.get());
        }
        return this.paymentMethodRepository.save(newMethod);
    }

    public PaymentMethod addPPalMethod(int accountId, PaypalPayment newMethod) {
        Optional<Account> account = this.accountRepository.findById(accountId);
        if (account.isPresent()) {
            newMethod.setOwner(account.get());
        }
        return this.paymentMethodRepository.save(newMethod);
    }

    public Address addAddress(int accountId, Address address) {
        Optional<Account> account = this.accountRepository.findById(accountId);
        if (account.isPresent()) {
            address.setOwner(account.get());
        }
        return this.addressRepository.save(address);
    }

    public Address getAddress(int accountId) {
        Address address = this.addressRepository.findAddressByOwnerId(accountId);
        return address;
    }

    public String getPreferredPayment(int accountId) {
        Optional<Account> account = this.accountRepository.findById(accountId);
        if (account.isPresent()) {
           return account.get().getPreferredPayment().toString();
        }
        return null;
    }

    public Boolean updatePreferredPayment(int accountId, PaymentType paymentType) {
        Optional<Account> res = this.accountRepository.findById(accountId);
        if (res.isPresent()) {
             Account account = res.get();
             account.setPreferredPayment(paymentType);
             accountRepository.save(account);

             return  true;
        }

        return false;
    }

    public PaymentMethod getPaymentDetail(int accountId, PaymentType paymentType) {
        Optional<Account> account = this.accountRepository.findById(accountId);
        String preferredType = paymentType!= null? paymentType.toString() : this.getPreferredPayment(accountId);

        if (account.isPresent()) {
            List<PaymentMethod> paymentMethods= account.get().getPaymentMethods();

            if(paymentMethods.size() > 0) {
                Optional<PaymentMethod> preferredMethod= paymentMethods.stream()
                        .filter(paymentMethod -> paymentMethod.getPaymentType().toString().equals(preferredType)).findFirst();

                return preferredMethod.isPresent() ? preferredMethod.get() : null;
            }
        }

        return null;
    }


}
