package com.test.demo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.test.demo.entity.WeatherConfig;
import com.test.demo.entity.model.Weather;
import com.test.demo.entity.model.WeatherConfigCommand;
import com.test.demo.entity.model.WeatherCustom;
import com.test.demo.entity.model.WeatherDetail;
import com.test.demo.mapper.wf.WeatherConfigMapper;
import com.test.demo.service.WeatherService;
import com.test.demo.utils.Constant;
import com.test.demo.utils.DebugUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author kun.han on 2019/6/14 11:04
 */
@Service
public class WeatherServiceImpl implements WeatherService {

    private Logger logger = LoggerFactory.getLogger(WeatherServiceImpl.class);

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private JavaMailSenderImpl javaMailSender;

    @Resource
    private WeatherConfigMapper weatherConfigMapper;

    @Value("${steal-url}")
    private String stealUrl;

    /**
     * 发送邮件
     *
     * @return string
     */
    @Override
    public String sendWeatherMail() {
        // 设置获取天气途径
        logger.info("设置天气途径");
        String url = "http://d1.weather.com.cn/weather_index/101020100.html?_=" + System.currentTimeMillis();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Referer", "http://www.weather.com.cn/weather1d/101020100.shtml");
        httpHeaders.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36");

        // 设置 请求头中 的  希望服务器返回给客户端的 数据类型
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
        String[] to = weatherConfigMapper.getAllByType(Constant.TYPE_TO).stream().map(WeatherConfig::getValue).toArray(String[]::new);
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
        logger.info(Constant.SUCCESS_SEND);
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
        boolean boo = DayOfWeek.SATURDAY.equals(dayOfWeek) || DayOfWeek.SUNDAY.equals(dayOfWeek);
        if (boo) {
            sb.append("周末呢~ 看看今天能不能去逛街\uD83D\uDEB6\u200D♀\uD83E\uDDD0 ");
        } else {
            sb.append(dayOfWeek.toString().toLowerCase()).append("\uD83E\uDD2A，不能出去逛街，但是也看看呗\uD83D\uDC12，");
        }
        sb.append(weatherCustom.getGj_des_s()).append("\n\n")
                .append("不能喝酒的人，还老是想喝酒，今天").append(weatherCustom.getPj_des_s().replaceAll("。", "，")).append("但是啤酒\uD83C\uDF7B不好喝噻\n\n")
                .append("身体是革命的本钱呢\uD83D\uDCAA，").append(weatherCustom.getGm_des_s()).append("\n\n")
                .append("今天洗不洗衣服呢，");
        if (boo) {
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
    private String getSubject() {
        Date nowDate = new Date();
        WeatherConfig subject = weatherConfigMapper.getSubject(Constant.TYPE_SUBJECT);
        subject.setUpdateTime(nowDate);
        weatherConfigMapper.updateByPrimaryKey(subject);
        return subject.getValue();
    }

    public String getSubjectFromUrl(){
        return restTemplate.getForObject(stealUrl, String.class);
    }

    @Resource
    DataSourceTransactionManager wfTransactionManager;
    /**
     * 增加subject
     *
     * @param subject subject
     * @return string
     */
    @Override
    @Transactional(rollbackFor = Exception.class, value = "wfTransactionManager")
    public String addSubject(String subject) {
        DebugUtils.showTransactionStatus("WeatherServiceImpl.addSubject");
        Date now = new Date();
        WeatherConfig weather = new WeatherConfig();
        weather.setUpdateTime(now);
        weather.setStatus(1);
        weather.setType(Constant.TYPE_SUBJECT);
        weather.setValue(subject);
        weatherConfigMapper.insert(weather);
        logger.info(Constant.SUCCESS_ADD + " -- subject:" + weather.getValue());
        int i = 1 / 0;
        return Constant.SUCCESS_ADD;
    }

    /**
     * 删除subject
     *
     * @param id id
     * @return string
     */
    @Override
    public String deleteSubject(Long id) {
        WeatherConfig weatherConfig = new WeatherConfig();
        weatherConfig.setStatus(0);
        weatherConfig.setId(id);
        weatherConfigMapper.updateByPrimaryKeySelective(weatherConfig);
        logger.info(Constant.SUCCESS_DELETE + " -- id:" + id);
        return Constant.SUCCESS_DELETE;
    }

    /**
     * 获取所有的subject
     *
     * @return list
     */
    @Override
    public List<WeatherConfig> getAllSubject() {
        return weatherConfigMapper.getAllByType(Constant.TYPE_SUBJECT);
    }

    @Override
    public List<Map> test() {
        return weatherConfigMapper.test(Constant.TYPE_SUBJECT);
    }

    public String stealSubject(){
        Date now = new Date();
        String subject = getSubjectFromUrl();
        List<WeatherConfig> allByValue = weatherConfigMapper.getAllByValue(subject);
        if (!CollectionUtils.isEmpty(allByValue)) {
            return "已存在 : " + subject;
        }
        WeatherConfig weatherConfig = new WeatherConfig();
        weatherConfig.setStatus(1);
        weatherConfig.setUpdateTime(now);
        weatherConfig.setType(Constant.TYPE_SUBJECT);
        weatherConfig.setValue(subject);
        weatherConfigMapper.insertSelective(weatherConfig);
        return subject;
    }

    /**
     * 清理重复的subject
     *
     * @return
     */
    @Override
    public String clear() {
        List<WeatherConfigCommand> weatherConfigs = weatherConfigMapper.getAllRepeat();
        if (CollectionUtils.isEmpty(weatherConfigs)) {
            return Constant.NO_REPEAT_DATA;
        }
        List<Long> ids = weatherConfigs.stream().map(WeatherConfig::getId).collect(Collectors.toList());
        int result = weatherConfigMapper.deleteByIdIn(ids);
        if (result >= 1){
            return Constant.SUCCESS_DELETE;
        }else {
            return Constant.FAIL_DELETE;
        }
    }
}
