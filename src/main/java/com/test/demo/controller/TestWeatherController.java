package com.test.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.test.demo.entity.Weather;
import com.test.demo.entity.WeatherConfig;
import com.test.demo.entity.WeatherCustom;
import com.test.demo.entity.WeatherDetail;
import com.test.demo.mapper.wf.WeatherConfigMapper;
import com.test.demo.service.impl.WeatherServiceImpl;
import com.test.demo.utils.Constant;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author kun.han on 2019/6/12 17:07
 */
@RestController
public class TestWeatherController {
    @Resource
    private WeatherServiceImpl weatherService;

    @GetMapping("sendWeatherMail")
    public String sendWeatherMail(){
        return weatherService.sendWeatherMail();
    }

    @GetMapping("addSubject")
    public String addSubject(@RequestParam("subject")String subject){
        return weatherService.addSubject(subject);
    }

    @GetMapping("deleteSubject")
    public String deleteSubject(@RequestParam("id")Long id){
        return weatherService.deleteSubject(id);
    }

    @GetMapping("getAllSubject")
    public List<WeatherConfig> getAllSubject(){
        return weatherService.getAllSubject();
    }

    Logger logger = LoggerFactory.getLogger(WeatherServiceImpl.class);

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private JavaMailSenderImpl javaMailSender;

    @Resource
    private WeatherConfigMapper weatherConfigMapper;

    @GetMapping("sendTest")
    public String sendTest() {
        // 设置获取天气途径
        logger.info("设置天气途径");
        String url = "http://d1.weather.com.cn/weather_index/101020100.html?_=" + System.currentTimeMillis();
        Document doc = null;
        try {
            doc = Jsoup.connect(url)
                    .header("Referer", "http://www.weather.com.cn/weather1d/101020100.shtml")
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36")
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (doc != null){
            System.out.println(doc.body());
        }

        HttpHeaders httpHeaders = new HttpHeaders();

        /*// 设置 请求头中 的  希望服务器返回给客户端的 数据类型
        List<MediaType> acceptableMediaTypes = new ArrayList<>();
        acceptableMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        httpHeaders.setAccept(acceptableMediaTypes);

        // 获取天气接口返回数据
        logger.info("获取天气数据");
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        //整理数据
        logger.info("整理天气数据");
        String data = responseEntity.getBody();
        String[] split = data.replaceAll(" ", "").split("var");
        Weather weather = JSONObject.parseObject(split[1].substring(split[1].indexOf(":") + 1, split[1].length() - 2), Weather.class);
        WeatherDetail weatherDetail = JSONObject.parseObject(split[3].substring(split[3].indexOf("=") + 1, split[3].length() - 1), WeatherDetail.class);
        WeatherCustom weatherCustom = JSONObject.parseObject(split[4].substring(split[4].indexOf(":") + 1, split[4].lastIndexOf(",")), WeatherCustom.class);

        // 整理邮件数据
        logger.info("整理邮件数据");
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        List<String> list = weatherConfigMapper.getAllByType(Constant.TYPE_TO).stream().map(WeatherConfig::getValue).collect(Collectors.toList());
        String[] to = list.toArray(new String[list.size()]);
        String from = weatherConfigMapper.getOneByType(Constant.TYPE_FROM).getValue();

        // 设置收件人 寄件人 内容
        logger.info("收件人 :[{}]", JSONObject.toJSON(to));
        simpleMailMessage.setTo(to);
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setSubject(getSubject());
        simpleMailMessage.setText(getTextBody(weather, weatherCustom));
        // 发送邮件
        logger.info("发送天气邮件");
        javaMailSender.send(simpleMailMessage);
        logger.info(Constant.SUCCESS_SEND);*/
        return Constant.SUCCESS_SEND;

    }
    //🧐  🤪
    //👻[得意][骷髅][衰][西瓜][啤酒][太阳][月亮][捂脸][奸笑][机智][耶]😝💪🌂🙈🙊🐒🙉☀️🌤⛅️🌥☁️🌦🌧⛈🌩🌨❄️☃️⛄️🌬💨☔️☂️🌫🌪🌈🍻🍺🚶‍♀️🚶‍♂️🕢

    /**
     * 获得邮件体
     *
     * @param weather       天气
     * @param weatherCustom 天气
     * @return string
     */
    private String getTextBody(Weather weather, WeatherCustom weatherCustom) {

        LocalDateTime now = LocalDateTime.now();
        DayOfWeek dayOfWeek = now.getDayOfWeek();
        StringBuilder sb = new StringBuilder();

        sb.append("\n\uD83D\uDC7B 嘻嘻，崽崽天气来了，今天的温度是").append(weather.getTempn()).append("-").append(weather.getTemp()).append("，天气").append(weather.getWeather()).append("\uD83C\uDF24，风力").append(weather.getWs()).append("️\uD83C\uDF2C\n\n")
                .append("还有今天").append(weatherCustom.getCo_des_s()).append("\n\n")
                .append("紫外线呢，[太阳]").append(weatherCustom.getUv_des_s().replaceAll("。", "，")).append("貌似这个也不用我提醒了\uD83E\uDD14️\n\n");
        if (DayOfWeek.SATURDAY.equals(dayOfWeek) || DayOfWeek.SUNDAY.equals(dayOfWeek)) {
            sb.append("周末呢~ 看看今天能不能去逛街\uD83D\uDEB6\u200D♀\uD83E\uDDD0 ");
        } else {
            sb.append(dayOfWeek.toString().toLowerCase()).append("\uD83E\uDD2A，不能出去逛街，但是也看看呗\uD83D\uDC12，");
        }
        sb.append(weatherCustom.getGj_des_s()).append("\n\n")
                .append("不能喝酒的人，还老是想喝酒，今天").append(weatherCustom.getPj_des_s().replaceAll("。", "，")).append("但是啤酒\uD83C\uDF7B不好喝噻\n\n")
                .append("身体是革命的本钱呢\uD83D\uDCAA，").append(weatherCustom.getGm_des_s()).append(weatherCustom.getZs_des_s()).append("\n\n")
                .append("今天洗不洗衣服呢，");
        if (DayOfWeek.SATURDAY.equals(dayOfWeek) || DayOfWeek.SUNDAY.equals(dayOfWeek)) {
            sb.append("周末的早晨，应该可以洗一洗吧\uD83D\uDC12，");
        } else {
            sb.append("工作日呢，不能洗衣服噻\uD83D\uDC12，");
        }
        sb.append(weatherCustom.getLs_des_s()).append("\n\n")
                .append("老是忘记带伞\uD83C\uDF02的小柔柔，").append(weatherCustom.getYs_des_s()).append("\n")
        ;
        return sb.toString();
    }

    /**
     * 获取一个subject
     *
     * @return string
     */
    public String getSubject() {

        LocalDateTime now = LocalDateTime.now();
        Date nowDate = new Date();
        DayOfWeek dayOfWeek = now.getDayOfWeek();

        WeatherConfig subject = weatherConfigMapper.getSubject(Constant.TYPE_SUBJECT);
        subject.setUpdateTime(nowDate);
        weatherConfigMapper.updateByPrimaryKey(subject);

        return subject.getValue();
    }
}
