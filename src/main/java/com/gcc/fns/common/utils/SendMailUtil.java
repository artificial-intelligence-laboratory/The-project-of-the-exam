package com.gcc.fns.common.utils;

import com.gcc.fns.common.enums.ResponseStatusEnum;
import com.gcc.fns.common.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author xiaozhi
 * @description 发送邮件的工具类
 * @create 2022-10-2022/10/24 18:03
 */
@Slf4j
@Component
public class SendMailUtil {

    @Resource
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    /**
     * 发送邮件
     * @param targetUser    目标用户
     * @param subject       主题
     * @param content       内容
     */
    public void sendMail(String targetUser, String subject, String content) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        // 发送html
        try {
            helper.setFrom(from);
            helper.setTo(targetUser);
            helper.setSubject(subject);
            helper.setText(content, true);
            javaMailSender.send(helper.getMimeMessage());
        } catch (MessagingException e) {
            throw new CustomException(ResponseStatusEnum.MAIL_SEND_FAILED);
        }
    }

    public void sendCodeToMail(String targetUser, String code) {
        String content = mailTemplate(code);
        sendMail(targetUser, "验证码", content);
    }

    /**
     * 邮箱模板
     * @param code          验证码
     * @return
     */
    public String mailTemplate(String code) {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\"" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>小灵通</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <p>【小灵通】 尊敬的小灵通用户您好"+
                "，您的验证码为 [ <span style=\"color: red;\">" + code +
                "</span> ] ，请在页面填验证码完成登录</p>\n" +
                "</body>\n" +
                "</html>";
    }
}
