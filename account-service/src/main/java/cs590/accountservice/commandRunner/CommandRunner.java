package cs590.accountservice.commandRunner;

import cs590.accountservice.entity.*;
import cs590.accountservice.repository.AccountRepository;
import cs590.accountservice.repository.AddressRepository;
import cs590.accountservice.repository.PaymentMethodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class CommandRunner implements CommandLineRunner {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Override
    public void run(String... args) throws Exception {
        Address shippingAddress = new Address("US","IA","FAIRFIELD", "52557", "1000 N Street");
        Account account = new Account("Selam", "yilma",  "selam@gmail.com", "1234", shippingAddress, PaymentType.BANK);
        accountRepository.save(account);

        PaymentMethod method = new BankPayment("R120000", "BA-1234445555");
        method.setPaymentType(PaymentType.BANK);
        method.setOwner(account);

        PaymentMethod method2 = new PaypalPayment("PP1234445555");
        method2.setPaymentType(PaymentType.PAYPAL);
        method2.setOwner(account);

        paymentMethodRepository.save(method);
        paymentMethodRepository.save(method2);

    }
}
