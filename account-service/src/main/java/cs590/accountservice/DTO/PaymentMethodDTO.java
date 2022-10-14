package cs590.accountservice.DTO;

import cs590.accountservice.entity.BankPayment;
import cs590.accountservice.entity.CCPayment;
import cs590.accountservice.entity.PaymentType;
import cs590.accountservice.entity.PaypalPayment;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentMethodDTO {

    private PaymentType paymentType;

    private String routingNumber;

    private String bankAccount;

    private String ccAccount;

    private String CCV;

    private String paypalAccount;

    public BankPayment getBankPayment() {
        BankPayment paymentMethod = new BankPayment();
        paymentMethod.setPaymentType(paymentType);
        paymentMethod.setBankAccount(bankAccount);
        paymentMethod.setBankAccount(routingNumber);

        return paymentMethod;
    }

    public PaypalPayment getPPayment() {
        PaypalPayment paymentMethod = new PaypalPayment();
        paymentMethod.setPaymentType(paymentType);
        paymentMethod.setPaypalAccount(paypalAccount);

        return paymentMethod;
    }

    public CCPayment getCCPayment() {
        CCPayment paymentMethod = new CCPayment();
        paymentMethod.setPaymentType(paymentType);
        paymentMethod.setCcAccount(ccAccount);
        paymentMethod.setCCV(CCV);

        return paymentMethod;
    }
}
