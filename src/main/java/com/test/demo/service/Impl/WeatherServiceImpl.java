package com.test.demo.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.test.demo.entity.Weather;
import com.test.demo.entity.WeatherConfig;
import com.test.demo.entity.WeatherCustom;
import com.test.demo.entity.WeatherDetail;
import com.test.demo.mapper.WeatherConfigMapper;
import com.test.demo.service.WeatherService;
import org.springframework.http.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author kun.han on 2019/6/14 11:04
 */
@Service
public class WeatherServiceImpl implements WeatherService {

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private JavaMailSenderImpl javaMailSender;

    @Resource
    private WeatherConfigMapper weatherConfigMapper;

    /**
     * sendWeatherMail
     */
    @Override
    public void sendWeatherMail() {
        // 设置获取天气途径
        String url = "http://d1.weather.com.cn/weather_index/101020100.html?_=" + System.currentTimeMillis();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Referer", "http://www.weather.com.cn/weather1d/101020100.shtml");
        httpHeaders.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36");

        // 设置 请求头中 的  希望服务器返回给客户端的 数据类型
        List<MediaType> acceptableMediaTypes = new ArrayList<>();
        acceptableMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        httpHeaders.setAccept(acceptableMediaTypes);

        // 获取天气接口返回数据
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        //整理数据
        String data = responseEntity.getBody();
        String[] split = data.replaceAll(" ", "").split("var");
        Weather weather = JSONObject.parseObject(split[1].substring(split[1].indexOf(":") + 1, split[1].length() - 2), Weather.class);
        WeatherDetail weatherDetail = JSONObject.parseObject(split[3].substring(split[3].indexOf("=") + 1, split[3].length() - 1), WeatherDetail.class);
        WeatherCustom weatherCustom = JSONObject.parseObject(split[4].substring(split[4].indexOf(":") + 1, split[4].lastIndexOf(",")), WeatherCustom.class);

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        // 整理邮件数据
        List<String> list = weatherConfigMapper.getAllByType("to").stream().map(WeatherConfig::getValue).collect(Collectors.toList());
        String[] to = list.toArray(new String[list.size()]);
        String from = weatherConfigMapper.getOneByType("from").getValue();
        String subject = weatherConfigMapper.getOneByType("subject").getValue();

        // 设置收件人 寄件人 内容
        simpleMailMessage.setTo(to);
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(getTextBody(weather,weatherCustom));
        // 发送邮件
        javaMailSender.send(simpleMailMessage);
        System.out.println("邮件已发送");

    }
    //🧐
    //👻[得意][骷髅][衰][西瓜][啤酒][太阳][月亮][捂脸][奸笑][机智][耶]😝💪🌂🙈🙊🐒🙉☀️🌤⛅️🌥☁️🌦🌧⛈🌩🌨❄️☃️⛄️🌬💨☔️☂️🌫🌪🌈🍻🍺🚶‍♀️🚶‍♂️🕢

    private String getTextBody(Weather weather, WeatherCustom weatherCustom) {
        StringBuilder sb = new StringBuilder();
        sb      .append("\n\uD83D\uDC7B 嘻嘻，崽崽天气来了，今天的温度是").append(weather.getTempn()).append("-").append(weather.getTemp()).append("，天气").append(weather.getWeather()).append("\uD83C\uDF24，风力").append(weather.getWs()).append("️\uD83C\uDF2C\n\n")
                .append("还有今天").append(weatherCustom.getCo_des_s()).append("\n\n")
                .append("紫外线呢，[太阳]").append(weatherCustom.getUv_des_s().replaceAll("。","，")).append("貌似这个也不用我提醒了\uD83E\uDD14️\n\n")
                .append("看看今天能不能去逛街\uD83D\uDEB6\u200D♀呢\uD83E\uDDD0 ").append(weatherCustom.getGj_des_s()).append("\n\n")
                .append("不能喝酒的人，还老是想喝酒，今天").append(weatherCustom.getPj_des_s().replaceAll("。","，")).append("但是啤酒\uD83C\uDF7B不好喝噻\n\n")
                .append("身体是革命的本钱呢\uD83D\uDCAA，").append(weatherCustom.getGm_des_s()).append(weatherCustom.getZs_des_s()).append("\n\n")
                .append("今天洗不洗衣服呢，周末的早晨，应该可以洗一洗吧\uD83D\uDC12，").append(weatherCustom.getLs_des_s()).append("\n\n")
                .append("老是忘记带伞\uD83C\uDF02的小柔柔，").append(weatherCustom.getYs_des_s()).append("\n")
                ;
        return sb.toString();
    }

    public String getSubject(){
        List<String> subject = weatherConfigMapper.getAllByType("subject").stream().map(WeatherConfig::getValue).collect(Collectors.toList());

//        LocalDateTime now = LocalDateTime.now();
//        LocalDateTime.
//        if (now.getHour() < 18 && now.getMinute()<30){
//            return "还有" +
//        }

        return null;
    }
}
