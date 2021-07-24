package br.com.yuri.todomaneiro.security.service;

import br.com.yuri.todomaneiro.exception.TechnicalException;
import br.com.yuri.todomaneiro.repository.IUsuarioRepository;
import br.com.yuri.todomaneiro.service.email.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AuthService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private IEmailService emailService;

    private Random rand = new Random();

    public void sendNewPassword(final String email) {
        var usuario = this.usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new TechnicalException("Email não encontrado.", HttpStatus.NOT_FOUND));

        final var novaSenha = this.newPassword();
        usuario.setSenha(bCryptPasswordEncoder.encode(novaSenha));
        this.usuarioRepository.save(usuario);

        this.emailService.sendNewPasswordEmail(usuario, novaSenha);
    }

    private String newPassword() {
        var vetor = new char[10];
        for(int i=0; i<10; i++){
            vetor[i] = this.randomChar();
        }
        return new String(vetor);
    }

    private char randomChar() {
        var opt = rand.nextInt(3);
        if(opt == 0) { //Gera digito
            return (char) (rand.nextInt(10) + 48);
        } else if(opt == 1) { //Gera letra maiuscula
            return (char) (rand.nextInt(26) + 65);
        } else { //Gera letra minuscula
            return (char) (rand.nextInt(26) + 97);
        }
    }

}
