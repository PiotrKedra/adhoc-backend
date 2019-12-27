package piotr.kedra.adhoc.auth;


import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import piotr.kedra.adhoc.auth.entity.User;

import java.util.Objects;

@Component
@SessionScope
public class UserContext {

    private User user;

    public Long getUserID(){
        return user.getId();
    }

    void setUser(User user){
        this.user = user;
    }

    boolean isContext(){
        return Objects.nonNull(user);
    }
}
