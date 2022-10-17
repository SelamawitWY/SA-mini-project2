package cs590.accountservice.security;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private boolean Status;
//    private Integer accountId;
    private final String jwt;
}
