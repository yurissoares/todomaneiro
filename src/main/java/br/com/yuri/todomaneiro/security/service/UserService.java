package br.com.yuri.todomaneiro.security.service;

import br.com.yuri.todomaneiro.security.UserSS;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserService {

    private UserService() {
        throw new IllegalStateException("Utility class");
    }

    public static UserSS authenticated() {
        try {
            return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }
}
