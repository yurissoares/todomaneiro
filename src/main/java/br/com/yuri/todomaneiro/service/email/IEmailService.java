package br.com.yuri.todomaneiro.service.email;

import br.com.yuri.todomaneiro.entity.TodoEntity;
import br.com.yuri.todomaneiro.entity.UsuarioEntity;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.internet.MimeMessage;

public interface IEmailService {
    void sendTodoFinalizadoEmail(final TodoEntity todo);
    void sendEmail(final SimpleMailMessage msg);
    void sendTodoFinalizadoHtmlEmail(final TodoEntity todo);
    void sendHtmlEmail(final MimeMessage msg);
    void sendNewPasswordEmail(final UsuarioEntity usuario, final String novaSenha);
}
