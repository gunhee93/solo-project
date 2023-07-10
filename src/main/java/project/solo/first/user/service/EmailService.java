package project.solo.first.user.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import project.solo.first.common.util.RedisUtil;
import project.solo.first.user.dto.EmailRequest;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
public class EmailService {

    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;
    private String authNum;

    private final RedisUtil redisUtil;

    // 랜덤 인증코드 생성
    private void createCode(){
        Random random = new Random();
        StringBuffer key = new StringBuffer();

        for(int i=0;i<8;i++) {
            int index = random.nextInt(3);

            switch (index) {
                case 0 :
                    key.append((char) ((int)random.nextInt(26) + 97));
                    break;
                case 1:
                    key.append((char) ((int)random.nextInt(26) + 65));
                    break;
                case 2:
                    key.append(random.nextInt(9));
                    break;
            }
        }
        authNum = key.toString();
    }

    //메일 양식 작성
    private MimeMessage createEmailForm(String email) throws MessagingException, UnsupportedEncodingException {

        createCode(); //인증 코드 생성
        String setFrom = "pjsassy0401@gmail.com"; //email-config에 설정한 자신의 이메일 주소(보내는 사람)
        String toEmail = email; //받는 사람
        String title = "project solo 인증 번호"; //제목

        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, email); //보낼 이메일 설정
        message.setSubject(title); //제목 설정
        message.setFrom(setFrom); //보내는 이메일
        message.setText(setContext(authNum), "utf-8", "html");

        return message;
    }

    //실제 메일 전송
    @SneakyThrows
    public void sendEmail(String email) {

        //메일전송에 필요한 정보 설정
        MimeMessage emailForm = createEmailForm(email);
        //실제 메일 전송
        emailSender.send(emailForm);

        redisUtil.setDataExpire(authNum, email , 60*5L);

    }

    //타임리프를 이용한 context 설정
    private String setContext(String code) {
        Context context = new Context();
        context.setVariable("code", code);
        return templateEngine.process("mail", context); //mail.html
    }
}

