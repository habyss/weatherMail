package com.test.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.test.demo.entity.User;
import com.test.demo.entity.WeatherConfig;
import com.test.demo.entity.WeatherConfigCommand;
import com.test.demo.mapper.wf.WeatherConfigMapper;
import com.test.demo.service.TestServiceYes;
import com.test.demo.service.impl.TestServiceImpl;
import com.test.demo.service.impl.TestServiceNo;
import com.test.demo.service.impl.TestServiceYesImpl;
import com.test.demo.utils.MD5Util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.http.*;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Resource
    TestServiceNo testServiceNo;

    @Resource
    TestServiceYesImpl testServiceYesImpl;

    @Resource
    TestServiceImpl testServiceImpl;

    @Resource
    TestServiceYes testServiceYes;

    @GetMapping("testNo")
    public void testNo() {
        testServiceNo.testNo();
    }

    @GetMapping("testYes")
    public void testYes() {
        testServiceYesImpl.testYes();
    }

    @GetMapping("testYesNo")
    public void testYesNo() {
        testServiceYesImpl.testNo();
    }

    @GetMapping("getAllTest")
    public List<WeatherConfigCommand> getAllTest() {
        List<WeatherConfigCommand> test = weatherConfigMapper.getAllTest();
        for (WeatherConfigCommand command : test) {
            System.out.println(command);
        }
        return test;
    }

    @GetMapping("getAllByType")
    public List<WeatherConfig> getAllByType(@RequestParam("type") String type) {
        return weatherConfigMapper.getAllByType(type);
    }

    @GetMapping("testInsert")
    public User testInsert() {
        return testServiceImpl.testInsert();
    }

    @GetMapping("getUser")
    public User getUser(@RequestParam("name") String name) {
        return testServiceYes.getUser(name);

    }

    @GetMapping("getList")
    public List<Map<String, Object>> getList() {
        return testServiceImpl.getList();
    }


    /**
     * http 调用第三方接口 json格式post Jsoup
     */
    @GetMapping("sendMessage")
    public void singleSend() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMddHHmmss");
        String userId = "J23364";
        String pwd = "090400";
        String mobile = "15224970925";
        String content = "同事您好，感谢您对此次测试的配合。";
        try {
            content = URLEncoder.encode("同事您好，感谢您对此次测试的配合。", "GB2312").toLowerCase();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String timestamp = simpleDateFormat.format(new Date());
        String field = "00000000";
        String pwdMd = userId + field + pwd + timestamp;

        String s = MD5Util.GetMD5Code(pwdMd).toLowerCase();
        String url = "http://TSC3.800CT.COM:8086/sms/v2/std/single_send";
//        String url = "http://TSC3.800CT.COM:8086/sms/v2/std/get_balance";

//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36");
//        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        Map<String, String> map = new HashMap<>();
        map.put("userid", userId);
        map.put("pwd", s);
        map.put("mobile", mobile);
        map.put("content", content);
        map.put("timestamp", timestamp);
//        map.put("validtime","20190904150000");
//        map.put("svrtype", "SMS");
        map.put("custid", "20190904150000");
        String o = JSONObject.toJSONString(map);
//        HttpEntity<String> requestEntity = new HttpEntity<String>(o, httpHeaders);
//        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
//        System.out.println(responseEntity.getBody());

        try {
            Document body = Jsoup.connect(url)
                    .header("Content-Type", "application/json")
                    .requestBody(o)
                    .post();
            System.out.println(body.body());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * http 调用第三方接口 json格式post restTemplate
     */
    @GetMapping("sendMessage1")
    public void singleSend1() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMddHHmmss");
        String userId = "J23364";
        String pwd = "090400";
        String mobile = "15224970925";
        String content = "同事您好，感谢您对此次测试的配合。";
        try {
            content = URLEncoder.encode("同事您好，感谢您对此次测试的配合。", "GB2312").toLowerCase();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String timestamp = simpleDateFormat.format(new Date());
        String field = "00000000";
        String pwdMd = userId + field + pwd + timestamp;

        String s = MD5Util.GetMD5Code(pwdMd).toLowerCase();

        // 请求地址
        String url = "http://TSC3.800CT.COM:8086/sms/v2/std/single_send";
        // 参数
        Map<String, String> params = new HashMap<>(6);
        params.put("userid", userId);
        params.put("pwd", s);
        params.put("mobile", mobile);
        params.put("content", content);
        params.put("timestamp", timestamp);
        params.put("custid", "20190904150000");
        // 请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // 封装
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(params, headers);
        // 发送请求
        String result = restTemplate.postForObject(url, entity, String.class);
        System.out.println(result);

        /*httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//      封装参数，千万不要替换为Map与HashMap，否则参数无法传递
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//      添加请求的参数
        params.add("hello", "hello");             //必传
        params.add("world", "world");           //选传
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, httpHeaders);
//      执行HTTP请求
        ResponseEntity<String> response = restTemplate.exchange("此处为url地址", HttpMethod.POST, requestEntity, String.class);
        String body = response.getBody();*/

        // String o = JSONObject.toJSONString(map);
    }
}
