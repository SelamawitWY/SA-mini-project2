package cs590.gatewayservice.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthenticationStatus {
    private Boolean isAuthenticated;
    private String message;
    private Integer accountId;

    public Integer getAccountId() {
        return accountId;
    }
}
