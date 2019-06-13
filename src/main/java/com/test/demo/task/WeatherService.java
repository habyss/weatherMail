package com.test.demo.task;

import com.alibaba.fastjson.JSONObject;
import com.test.demo.entity.Weather;
import com.test.demo.entity.WeatherConfig;
import com.test.demo.entity.WeatherCustom;
import com.test.demo.entity.WeatherDetail;
import com.test.demo.mapper.WeatherConfigMapper;
import org.springframework.http.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * @author kun.han on 2019/6/12 16:58
 */
@Service
public class WeatherService {

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private JavaMailSenderImpl javaMailSender;

    @Resource
    private WeatherConfigMapper weatherConfigMapper;

    @Scheduled(cron = "0 30 7 * * ?")
    public void getWeather() {
        String url = "http://d1.weather.com.cn/weather_index/101020100.html?_=" + System.currentTimeMillis();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Referer", "http://www.weather.com.cn/weather1d/101020100.shtml");
        httpHeaders.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36");

        // 设置 请求头中 的  希望服务器返回给客户端的 数据类型
        List<MediaType> acceptableMediaTypes = new ArrayList<>();
        acceptableMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        httpHeaders.setAccept(acceptableMediaTypes);

        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        String data = responseEntity.getBody();
        String[] split = data.replaceAll(" ", "").split("var");

        Weather weather = JSONObject.parseObject(split[1].substring(split[1].indexOf(":") + 1, split[1].length() - 2), Weather.class);
        WeatherDetail weatherDetail = JSONObject.parseObject(split[3].substring(split[3].indexOf("=") + 1, split[3].length() - 1), WeatherDetail.class);
        WeatherCustom weatherCustom = JSONObject.parseObject(split[4].substring(split[4].indexOf(":") + 1, split[4].lastIndexOf(",")), WeatherCustom.class);

        Properties properties = new Properties();
        //登录服务器是否需要认证
        properties.put("mail.smtp.auth", "true");
        //SSL证书Socket工厂
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        //使用SMTPS协议465端口
        properties.setProperty("mail.smtp.socketFactory.port", "465");
        javaMailSender.setJavaMailProperties(properties);

        List<String> list = weatherConfigMapper.getAllByType("to").stream().map(WeatherConfig::getValue).collect(Collectors.toList());
        String[] to = list.toArray(new String[list.size()]);

        String from = weatherConfigMapper.getOneByType("from").getValue();

        String subject = weatherConfigMapper.getOneByType("subject").getValue();

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        // 设置收件人，寄件人
        simpleMailMessage.setTo(to);
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setSubject(subject);
        StringBuilder sb = new StringBuilder();
        sb      .append("\t\t").append(weather.getTempn()).append(" ").append(weather.getTemp()).append(" ").append(weather.getWeather()).append(" ").append(weather.getWs()).append("\n")
                .append("空调指数").append(" - ").append(weatherCustom.getAc_hint()).append(" - ").append(weatherCustom.getAc_des_s()).append("\n")
                .append(weatherCustom.getAg_name()).append(" - ").append(weatherCustom.getAg_hint()).append(" - ").append(weatherCustom.getAg_des_s()).append("\n")
                .append(weatherCustom.getCl_name()).append(" - ").append(weatherCustom.getCl_hint()).append(" - ").append(weatherCustom.getCl_des_s()).append("\n")
                .append("舒适指数").append(" - ").append(weatherCustom.getCo_hint()).append(" - ").append(weatherCustom.getCo_des_s()).append("\n")
                .append(weatherCustom.getCt_name()).append(" - ").append(weatherCustom.getCt_hint()).append(" - ").append(weatherCustom.getCt_des_s()).append("\n")
                .append(weatherCustom.getDy_name()).append(" - ").append(weatherCustom.getDy_hint()).append(" - ").append(weatherCustom.getDy_des_s()).append("\n")
                .append(weatherCustom.getFs_name()).append(" - ").append(weatherCustom.getFs_hint()).append(" - ").append(weatherCustom.getFs_des_s()).append("\n")
                .append(weatherCustom.getGj_name()).append(" - ").append(weatherCustom.getGj_hint()).append(" - ").append(weatherCustom.getGj_des_s()).append("\n")
                .append("太阳镜  ").append(" - ").append(weatherCustom.getGl_hint()).append(" - ").append(weatherCustom.getGl_des_s()).append("\n")
                .append(weatherCustom.getGm_name()).append(" - ").append(weatherCustom.getGm_hint()).append(" - ").append(weatherCustom.getGm_des_s()).append("\n")
                .append(weatherCustom.getHc_name()).append(" - ").append(weatherCustom.getHc_hint()).append(" - ").append(weatherCustom.getHc_des_s()).append("\n")
                .append(weatherCustom.getJt_name()).append(" - ").append(weatherCustom.getJt_hint()).append(" - ").append(weatherCustom.getJt_des_s()).append("\n")
                .append(weatherCustom.getLk_name()).append(" - ").append(weatherCustom.getLk_hint()).append(" - ").append(weatherCustom.getLk_des_s()).append("\n")
                .append(weatherCustom.getLs_name()).append(" - ").append(weatherCustom.getLs_hint()).append(" - ").append(weatherCustom.getLs_des_s()).append("\n")
                .append(weatherCustom.getMf_name()).append(" - ").append(weatherCustom.getMf_hint()).append(" - ").append(weatherCustom.getMf_des_s()).append("\n")
                .append("夜生活  ").append(" - ").append(weatherCustom.getNl_hint()).append(" - ").append(weatherCustom.getNl_des_s()).append("\n")
                .append(weatherCustom.getPj_name()).append(" - ").append(weatherCustom.getPj_hint()).append(" - ").append(weatherCustom.getPj_des_s()).append("\n")
                .append("放风筝  ").append(" - ").append(weatherCustom.getPk_hint()).append(" - ").append(weatherCustom.getPk_des_s()).append("\n")
                .append("空气污染").append(" - ").append(weatherCustom.getPl_hint()).append(" - ").append(weatherCustom.getPl_des_s()).append("\n")
                .append(weatherCustom.getPp_name()).append(" - ").append(weatherCustom.getPp_hint()).append(" - ").append(weatherCustom.getPp_des_s()).append("\n")
                .append(weatherCustom.getTr_name()).append(" - ").append(weatherCustom.getTr_hint()).append(" - ").append(weatherCustom.getTr_des_s()).append("\n")
                .append("紫外线  ").append(" - ").append(weatherCustom.getUv_hint()).append(" - ").append(weatherCustom.getUv_des_s()).append("\n")
                .append(weatherCustom.getWc_name()).append(" - ").append(weatherCustom.getWc_hint()).append(" - ").append(weatherCustom.getWc_des_s()).append("\n")
                .append(weatherCustom.getXc_name()).append(" - ").append(weatherCustom.getXc_hint()).append(" - ").append(weatherCustom.getXc_des_s()).append("\n")
                .append(weatherCustom.getXq_name()).append(" - ").append(weatherCustom.getXq_hint()).append(" - ").append(weatherCustom.getXq_des_s()).append("\n")
                .append(weatherCustom.getYd_name()).append(" - ").append(weatherCustom.getYd_hint()).append(" - ").append(weatherCustom.getYd_des_s()).append("\n")
                .append(weatherCustom.getYh_name()).append(" - ").append(weatherCustom.getYh_hint()).append(" - ").append(weatherCustom.getYh_des_s()).append("\n")
                .append(weatherCustom.getYs_name()).append(" - ").append(weatherCustom.getYs_hint()).append(" - ").append(weatherCustom.getYs_des_s()).append("\n")
                .append(weatherCustom.getZs_name()).append(" - ").append(weatherCustom.getZs_hint()).append(" - ").append(weatherCustom.getZs_des_s()).append("\n");

        simpleMailMessage.setText(sb.toString());
        // 发送邮件
        javaMailSender.send(simpleMailMessage);

        System.out.println("邮件已发送");


    }
}
