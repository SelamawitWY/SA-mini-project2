package cs590.gatewayservice.controller;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


import cs590.gatewayservice.models.*;
import cs590.gatewayservice.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


@RestController
public class JwtAuthenticationController {

    @Autowired
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    private RestTemplate restTemplate;

    public JwtAuthenticationController(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @GetMapping("/getAllProduct")
    public ResponseEntity<?> getData() {
        ResponseEntity<ProductDTO[]> response =
                restTemplate.getForEntity(
                        "http://product-service:9091/products/getAll",
                        ProductDTO[].class);
        ProductDTO[] data = response.getBody();

         return ResponseEntity.ok().body(data);
    }
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) {
        System.out.println(authenticationRequest);
        AuthenticationStatus status = authenticate(authenticationRequest) ;

        if (!status.getIsAuthenticated()) {
            List<String> details = new ArrayList<>();
            details.add(status.getMessage());
            ErrorResponseDto error = new ErrorResponseDto(new Date(),
                    HttpStatus.UNAUTHORIZED.value(),
                    "UNAUTHORIZED", details, "uri");

            return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
        }

//        final String token = jwtTokenUtil.generateToken(status.getAccountId().toString());
        return ResponseEntity.ok(new JwtResponse(status.getJwt()));
    }

    private AuthenticationStatus authenticate(JwtRequest authenticationRequest) {//String username, String password) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<JwtRequest> request =
                new HttpEntity<JwtRequest>(authenticationRequest, headers);


        //account-service:8081
        AuthResponseDto response = restTemplate.postForObject("http://account-service:8081/accounts/authenticate", request, AuthResponseDto.class);
        System.out.println(response);
        if (response == null) {
            return new AuthenticationStatus(false, "Missing Authorization Header", null    );
        } else if (response.getStatus()) {
            return new AuthenticationStatus(false, "Invalid Username/Password", null    );
        }

        return new AuthenticationStatus(true, "Authentication Successful", response.getJwt());
    }
}