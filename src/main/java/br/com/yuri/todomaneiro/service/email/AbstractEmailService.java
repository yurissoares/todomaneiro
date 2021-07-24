package br.com.yuri.todomaneiro.service.email;

import br.com.yuri.todomaneiro.entity.TodoEntity;
import br.com.yuri.todomaneiro.entity.UsuarioEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

public abstract class AbstractEmailService implements IEmailService {

    @Value("${default.sender}")
    private String emailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendTodoFinalizadoEmail(final TodoEntity todo) {
        var simpleMailMsg = this.prepareSimpleMailMsgParaTodo(todo);
        sendEmail(simpleMailMsg);
    }

    protected SimpleMailMessage prepareSimpleMailMsgParaTodo(final TodoEntity todo) {
        var simpleMailMsg = new SimpleMailMessage();
        simpleMailMsg.setTo(todo.getUsuarioEntity().getEmail());
        simpleMailMsg.setFrom(emailSender);
        simpleMailMsg.setSubject("To-do finalizado: " + todo.getId());
        simpleMailMsg.setSentDate(new Date(System.currentTimeMillis()));
        simpleMailMsg.setText(todo.toString());
        return simpleMailMsg;
    }

    protected String htmlFromTemplateFinalizacaoTodo(final TodoEntity todo) {
        var context = new Context();
        context.setVariable("todo", todo);
        return templateEngine.process("email/finalizacaoTodo", context);
    }

    @Override
    public void sendTodoFinalizadoHtmlEmail(final TodoEntity todo) {
        try {
            var mimeMsg = this.prepareMimeMsgParaTodo(todo);
            sendHtmlEmail(mimeMsg);
        } catch (MessagingException m) {
            sendTodoFinalizadoEmail(todo);
        }
    }

    protected MimeMessage prepareMimeMsgParaTodo(final TodoEntity todo) throws MessagingException {
        var mimeMsg = javaMailSender.createMimeMessage();
        var mimeMsgHelper = new MimeMessageHelper(mimeMsg, false);
        mimeMsgHelper.setTo(todo.getUsuarioEntity().getEmail());
        mimeMsgHelper.setFrom(this.emailSender);
        mimeMsgHelper.setSubject("To-do finalizado! Id: " + todo.getId());
        mimeMsgHelper.setSentDate(new Date(System.currentTimeMillis()));
        mimeMsgHelper.setText(this.htmlFromTemplateFinalizacaoTodo(todo), true);

        return mimeMsg;
    }

    @Override
    public void sendNewPasswordEmail(final UsuarioEntity usuario, final String novaSenha) {
        var simpleMailMsg = this.prepareNewPasswordEmail(usuario, novaSenha);
        sendEmail(simpleMailMsg);
    }

    protected SimpleMailMessage prepareNewPasswordEmail(final UsuarioEntity usuario, final String novaSenha) {
        var simpleMailMsg = new SimpleMailMessage();
        simpleMailMsg.setTo(usuario.getEmail());
        simpleMailMsg.setFrom(emailSender);
        simpleMailMsg.setSubject("Solicitação de nova senha.");
        simpleMailMsg.setSentDate(new Date(System.currentTimeMillis()));
        simpleMailMsg.setText("Senha nova: " + novaSenha);
        return simpleMailMsg;
    }

}
