package com.test.demo.controller;

import com.test.demo.entity.WeatherConfig;
import com.test.demo.mapper.WeatherConfigMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author kun.han on 2019/6/14 11:12
 */
@RestController
public class TestController {
    @Resource
    private RestTemplate restTemplate;

    @Resource
    private JavaMailSenderImpl javaMailSender;

    @Resource
    private WeatherConfigMapper weatherConfigMapper;

    @GetMapping("test")
    public void test(){
//        String url = "https://weather.com/zh-CN/weather/today/l/31.25,121.58?par=apple_widget&locale=zh_CN";
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1");
//        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
//        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
//        System.out.println(responseEntity.getBody());
//
//        // 整理邮件数据
//        List<String> list = weatherConfigMapper.getAllByType("to").stream().map(WeatherConfig::getValue).collect(Collectors.toList());
//        String[] to = list.toArray(new String[list.size()]);
//        String from = weatherConfigMapper.getOneByType("from").getValue();
//        String subject = weatherConfigMapper.getOneByType("subject").getValue();
//
//        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//        try {
//            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true,"utf-8");
//            // 设置收件人 寄件人 内容
//            messageHelper.setTo(to);
//            messageHelper.setFrom(from);
//            messageHelper.setSubject(subject);
//            messageHelper.setText(responseEntity.getBody()/*.replaceAll("<footer([\\s\\S]*?)footer>","")
//                    .replaceAll("<aside([\\s\\S]*?)aside>","")*/,true);
//            javaMailSender.send(mimeMessage);
//            System.out.println("邮件已发送");
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
    }
}
