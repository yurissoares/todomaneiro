package br.com.yuri.todomaneiro.service.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;

public class SmtpEmailService extends AbstractEmailService {

    private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class);

    @Autowired
    private MailSender mailSender;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendEmail(final SimpleMailMessage msg) {
        LOG.info("Enviando e-mail...");
        this.mailSender.send(msg);
        LOG.info("E-mail enviado.");
    }

    @Override
    public void sendHtmlEmail(final MimeMessage msg) {
        LOG.info("Enviando e-mail...");
        this.javaMailSender.send(msg);
        LOG.info("E-mail enviado.");
    }
}
