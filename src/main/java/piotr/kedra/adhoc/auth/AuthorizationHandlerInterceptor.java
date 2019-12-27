package piotr.kedra.adhoc.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import piotr.kedra.adhoc.auth.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Component
public class AuthorizationHandlerInterceptor implements HandlerInterceptor {

    @Autowired
    private UserContext userContext;

    @Autowired
    private AuthorizationTokenService authorizationTokenService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        Optional<User> user = verifyToken(request);
        if(user.isPresent()){
            setUserContext(user.get());
            return true;
        }
        response.setStatus(401);
        return false;
    }

    private Optional<User> verifyToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if(Objects.isNull((bearerToken)))
            return Optional.empty();
        if (bearerToken.startsWith("Bearer ")){
            String token = bearerToken.substring(7);
            return authorizationTokenService.verifyToken(token);
        }
        return Optional.empty();
    }

    private void setUserContext(User user) {
        if(userContext.isContext())
            return;
        userContext.setUser(user);
    }
}