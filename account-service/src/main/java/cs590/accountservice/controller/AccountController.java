package cs590.accountservice.controller;

import cs590.accountservice.security.AuthResponse;
import cs590.accountservice.entity.*;
import cs590.accountservice.security.AuthRequest;
import cs590.accountservice.security.JwtUtil;
import cs590.accountservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/check")
    public Boolean checkUser(@RequestBody AuthRequest authRequest) {
//        Account account = accountService.authenticate(authRequest.getUsername(), authRequest.getPassword());
        return  accountService.authenticate(authRequest.getUsername(), authRequest.getPassword()); //account==null;
    }

//    @PostMapping("/authenticate")
//    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest authRequest) throws Exception {
//        Account account = accountService.authenticate(authRequest.getUsername(), authRequest.getPassword());
//        AuthResponse authResponse = new AuthResponse(account==null, account.getId());
//        return ResponseEntity.ok(authResponse);
//    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createToken(@RequestBody AuthRequest authRequest) throws Exception {
        System.out.println(authRequest);

        if(accountService.authenticate(authRequest.getUsername(), authRequest.getPassword())){
            final Account account = accountService.getUserByEmail(authRequest.getUsername(), authRequest.getPassword());

            final String jwt = jwtUtil.generateToken(account.getId().toString());

            return ResponseEntity.ok(new AuthResponse(account==null, jwt));
        } else {
            throw new Exception("Incorrect username or password");
        }
    }

    @GetMapping(path = "/{accountId}")
    public ResponseEntity<?> findById(@PathVariable Integer accountId) {
        Account account = accountService.findById(accountId);
        return ResponseEntity.ok().body(account);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createAccount(@RequestBody Account account) {
        Account newAccount = accountService.createAccount(account);
        return new ResponseEntity<>(newAccount, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{accountId}")
    public ResponseEntity<?> update(@PathVariable Integer accountId, @RequestBody Account account) {
        Account updatedAccount = accountService.updateAccount(accountId, account);
        return ResponseEntity.ok(updatedAccount);
    }

    @DeleteMapping(path = "/{accountId}")
    public ResponseEntity<?> deleteAccount(@PathVariable Integer accountId) {
        if (!accountService.deleteAccount(accountId)) {
            return new ResponseEntity<>("Account not found!", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Successful", HttpStatus.OK);
    }


    @GetMapping(path = "/preferredPaymentType/{accountId}")
    public ResponseEntity<String> getPreferredPaymentType(@PathVariable Integer accountId) {
        String type = accountService.getPreferredPayment(accountId);
        return ResponseEntity.ok(type);
    }

    @PutMapping(path = "/updatePreferredPaymentType/{accountId}/{paymentType}")
    public ResponseEntity<Boolean> getPreferredPaymentType(@PathVariable Integer accountId,  @PathVariable  PaymentType paymentType) {
        Boolean response = accountService.updatePreferredPayment(accountId,paymentType );
        return ResponseEntity.ok(response);
    }

//    @PutMapping(path = "/updatePreferredPaymentType/{accountId}/{paymentType}")
//    public ResponseEntity<?> getPreferredPaymentMethod(@PathVariable(required = false) PaymentType paymentType, @PathVariable Integer accountId) {
//        PaymentMethod method = accountService.getPaymentDetail(accountId, paymentType );
//        return ResponseEntity.ok(method);
//    }

    @GetMapping(path = "/preferredPaymentMethod/{accountId}")
    public ResponseEntity<?> getPreferredPaymentMethod(@PathVariable Integer accountId) {
        PaymentMethod method = accountService.getPaymentDetail(accountId, null );
        return ResponseEntity.ok(method);
    }

    @GetMapping(path = "/preferredPaymentMethod/{accountId}/{paymentType}")
    public ResponseEntity<?> getPreferredPaymentMethod(@PathVariable Integer accountId, @PathVariable PaymentType paymentType) {
        PaymentMethod method = accountService.getPaymentDetail(accountId, paymentType );
        return ResponseEntity.ok(method);
    }

    @GetMapping(path = "/shippingAddress/{accountId}")
    public ResponseEntity<?> getShippingAddress(@PathVariable Integer accountId) {
        Address address = accountService.getAddress(accountId);
        return ResponseEntity.ok(address);
    }

    @PostMapping("/addPPPaymentMethod/{accountId}")
    public ResponseEntity<?> addBankAccount(@PathVariable Integer accountId, @RequestBody PaypalPayment ppayment) {
        PaymentMethod paymentMethod= accountService.addPPalMethod(accountId, ppayment);
        return new ResponseEntity<>(paymentMethod, HttpStatus.CREATED);
    }

    @PostMapping("/addBankPaymentMethod/{accountId}")
    public ResponseEntity<?> addBankAccount(@PathVariable Integer accountId, @RequestBody BankPayment bankPayment) {
        PaymentMethod paymentMethod= accountService.addBankMethod(accountId,bankPayment);
        return new ResponseEntity<>(paymentMethod, HttpStatus.CREATED);
    }

    @PostMapping("/addCCPaymentMethod/{accountId}")
    public ResponseEntity<?> addCCAccount(@PathVariable Integer accountId, @RequestBody CCPayment ccPayment) {
        PaymentMethod paymentMethod= accountService.addCCMethod(accountId, ccPayment);
        return new ResponseEntity<>(paymentMethod, HttpStatus.CREATED);
    }

}