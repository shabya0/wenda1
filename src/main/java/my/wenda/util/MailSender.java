package my.wenda.util;

import com.sun.mail.util.MailSSLSocketFactory;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.util.Map;
import java.util.Properties;

/**
 * Created by nowcoder on 2016/7/15. // course@nowcoder.com NKnk66
 */
@Service
public class MailSender implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(MailSender.class);
    private JavaMailSenderImpl mailSender;

    @Autowired
    Configuration configuration;
//    @Autowired
//    private VelocityEngine velocityEngine;

    public boolean sendWithHTMLTemplate(String to, String subject,
                                        String template, Map<String, Object> model) {
        try {
            String nick = MimeUtility.encodeText("登录");
            InternetAddress from = new InternetAddress(nick + "<1574033296@qq.com>");
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

            MailSSLSocketFactory sf = new MailSSLSocketFactory();//ssl设置
            sf.setTrustAllHosts(true);


            Template tpl = configuration.getTemplate(template);     //free marker configuration
            String result = FreeMarkerTemplateUtils.processTemplateIntoString(tpl, model);      //freemarker 加载template
//            String result = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, template, "UTF-8", model);        //velocity加载template
            mimeMessageHelper.setTo(to);    //发给那个邮件
            mimeMessageHelper.setFrom(from);    //从什么地方发
            mimeMessageHelper.setSubject(subject);  //邮件主题
            mimeMessageHelper.setText(result, true);
//            mimeMessageHelper.setText("hello, this message from mailSender");
            mailSender.send(mimeMessage);
            return true;
        } catch (Exception e) {
            logger.error("发送邮件失败" + e.getMessage());
            return false;
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        mailSender = new JavaMailSenderImpl();
        mailSender.setUsername("mail@mail.com");    //源邮箱
        mailSender.setPassword("xxxxxxxxxxxxxx");     //邮箱授权码，账号密码修改会使该码过期
//        mailSender.setHost("smtp.exmail.qq.com");
        mailSender.setHost("smtp.qq.com");
        mailSender.setPort(465);
        mailSender.setProtocol("smtps");
        mailSender.setDefaultEncoding("utf8");
        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.ssl.enable", true);     //yuan lai de
        javaMailProperties.put("mail.smtp.auth", true);     //qq
        javaMailProperties.put("mail.smtp.starttls.enable", true);

        mailSender.setJavaMailProperties(javaMailProperties);
    }
}
