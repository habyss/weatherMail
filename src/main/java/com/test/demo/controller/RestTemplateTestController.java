package com.test.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.test.demo.utils.MD5Util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author kun.han on 2019/6/14 11:12
 */
@RestController
public class RestTemplateTestController {

    /**
     * config 类中配置 restTemplate
     * 或者在启动类中配置 Bean
     */
    @Resource
    private RestTemplate restTemplate;

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
        Map<String, String> map = new HashMap<>();
        map.put("userid", userId);
        map.put("pwd", s);
        map.put("mobile", mobile);
        map.put("content", content);
        map.put("timestamp", timestamp);
        map.put("custid", "20190904150000");
        String o = JSONObject.toJSONString(map);
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
     * http 调用第三方接口  restTemplate
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

        // 请求地址  json格式post
        String url = "http://TSC3.800CT.COM:8086/sms/v2/std/single_send";
        // 参数
        Map<String, String> params = new HashMap<>(6);
        params.put("userid", userId);
        params.put("pwd", s);
        params.put("mobile", mobile);
        params.put("content", content);
        params.put("timestamp", timestamp);
        params.put("custid", "20190904150000");
        // String params = JSONObject.toJSONString(params);
        // 请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // 封装
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(params, headers);
        // 发送请求
        String result = restTemplate.postForObject(url, entity, String.class);
        System.out.println(result);


        // 参数 xxx-form-格式 post
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("userid", userId);
        param.add("pwd", s);
        param.add("mobile", mobile);
        param.add("content", content);
        param.add("timestamp", timestamp);
        param.add("custid", "20190904150000");
        // 请求头
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        // 封装
        HttpEntity<MultiValueMap<String, String>> entity1 = new HttpEntity<>(param, headers);
        // 发送请求
        String result1 = restTemplate.postForObject(url, entity1, String.class);

    }
}
