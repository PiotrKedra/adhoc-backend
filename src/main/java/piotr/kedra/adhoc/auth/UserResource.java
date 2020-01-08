package piotr.kedra.adhoc.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import piotr.kedra.adhoc.auth.entity.User;

import java.util.Optional;

@RestController
@RequestMapping("users")
public class UserResource {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserContext userContext;

    @GetMapping
    @RequestMapping("{id}")
    public ResponseEntity getUser(@PathVariable long id){
        Optional<User> user = userRepository.getById(id);
        if(user.isPresent()){
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.status(404).build();
    }

    @GetMapping
    @RequestMapping("me")
    public ResponseEntity getUserData(){
        return ResponseEntity.ok(userContext.getUser());
    }
}
