package br.com.yuri.todomaneiro.security.controller;

import br.com.yuri.todomaneiro.security.dto.EmailDto;
import br.com.yuri.todomaneiro.security.service.AuthService;
import br.com.yuri.todomaneiro.security.service.UserService;
import br.com.yuri.todomaneiro.security.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthService authService;

    @PostMapping(value = "/refresh_token")
    public ResponseEntity<Void> refreshToken(final HttpServletResponse response) {
        var userSS = UserService.authenticated();
        var token = this.jwtUtil.generateToken(userSS.getUsername());
        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("access-control-expose-headers", "Authorization");
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/forgot")
    public ResponseEntity<Void> forgot(@Valid @RequestBody final EmailDto emailDto) {
        this.authService.sendNewPassword(emailDto.getEmail());
        return ResponseEntity.noContent().build();
    }

}
