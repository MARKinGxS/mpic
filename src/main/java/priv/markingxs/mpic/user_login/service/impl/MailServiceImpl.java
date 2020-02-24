package priv.markingxs.mpic.user_login.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import priv.markingxs.mpic.user_login.service.MailService;


import javax.mail.internet.MimeMessage;
import java.util.Date;

@Service("mailservice")
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${mail.fromMail.addr}")
    private String from;

    @Autowired
    private TemplateEngine templateEngine;



    private static Logger logger = LoggerFactory.getLogger(new MailServiceImpl().getClass());

    @Override
    public void sendMail(String to, String subject, String verifyCode) {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = null;

        //创建邮件正文
        Context context = new Context();
        context.setVariable("verifyCode",verifyCode);

        //将模块引擎内容解析成html字符串
        String emailContent = templateEngine.process("emailTemplate",context);
        try{
            helper = new MimeMessageHelper(mimeMessage,true);
            helper.setSubject(subject);
            helper.setTo(to);
            helper.setFrom(from);
            helper.setText(emailContent,true);

            mailSender.send(mimeMessage);
        }catch(Exception e){
            logger.error("["+new Date() +"]发送验证邮箱有误，error Exception:",e);
        }


    }
}
