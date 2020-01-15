package piotr.kedra.adhoc.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import piotr.kedra.adhoc.auth.entity.ResponseMessage;
import piotr.kedra.adhoc.auth.entity.User;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("auth")
public class AuthResource {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorizationTokenService authorizationTokenService;

    @PostMapping(consumes = "application/json")
    public ResponseEntity registerUser(@RequestBody User user){
        try {
            User saved = userRepository.save(user);
            return ResponseEntity.ok(new RegisterResponse(saved.getId(), "Account created successfully"));
        } catch (Exception e){
            return ResponseEntity.ok(new RegisterResponse(null, e.getMessage()));
        }
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    @RequestMapping("token")
    public ResponseEntity getAuthorizationToken(@RequestBody User user){
        System.out.println("GET");
        if(Objects.nonNull(user.getEmail())){
            Optional<User> byEmail = userRepository.getByEmail(user.getEmail());
            if(byEmail.isPresent()){
                if(byEmail.get().getPassword().equals(user.getPassword())) {
                    return ResponseEntity.ok("{\n    \"token\": \"" + authorizationTokenService.generateToken(byEmail.get()) + "\"\n}");
                }
                return ResponseEntity.status(401).body(new ResponseMessage("Given password is wrong.", 401));
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
