package com.test.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.test.demo.entity.Content;
import com.test.demo.entity.Title;
import com.test.demo.mapper.wf.ContentMapper;
import com.test.demo.mapper.wf.TitleMapper;
import com.test.demo.service.LeiTingService;
import com.test.demo.utils.MD5Util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    @Resource
    private TitleMapper titleMapper;
    @Resource
    private ContentMapper contentMapper;
    @Resource
    private LeiTingService leiTingService;

    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-M-d HH:mm");

    @GetMapping("leiting")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void leiting(@RequestParam("num")Integer num) throws IOException, ParseException {
        String baseUrl = "http://bbs.leiting.com/forum-292-";
        String endUrl = ".html";
        for (int i = 1; i <= num; i++) {
            List<Title> titles = new ArrayList<>();
            String url = baseUrl + i + endUrl;
            // 获取所有的titles
            getTitles(url, titles);
            if (CollectionUtils.isEmpty(titles)){
                return;
            }
            titleMapper.batchInsert(titles);
            System.out.println("插入title  -------    " + i);
            // content表 暂时不用
            // for (Title title : titles) {
            //     System.out.println(title.getUrl());
            //     List<Content> list = new ArrayList<>();
            //     getContents(title, list);
            //     if (CollectionUtils.isEmpty(list)){
            //         continue;
            //     }
            //     contentMapper.batchInsert(list);
            //     System.out.println("插入content  -------    " + title.getTitle());
            // }
        }
    }

    @GetMapping("leitingNormal")
    @Transactional(rollbackFor = Exception.class)
    public void leitingNormal(@RequestParam("num")Integer num) throws IOException, ParseException {
        String baseUrl = "http://bbs.leiting.com/forum-288-";
        String endUrl = ".html";
        for (int i = 1; i <= num; i++) {
            List<Title> titles = new ArrayList<>();
            String url = baseUrl + i + endUrl;
            // 获取所有的titles
            getTitles(url, titles);
            if (CollectionUtils.isEmpty(titles)){
                return;
            }
            titleMapper.batchInsert(titles);
            System.out.println("插入title  -------    " + i);
        }
    }

    private void updateLast(String url, List<Title> titles)throws IOException, ParseException {
        Document doc = Jsoup.connect(url)
                .header("Content-Type", "text/html; charset=utf-8")
                .get();

        Elements normal = doc.getElementsByAttributeValueStarting("id", "normalthread");
        // Elements elements = doc.select(".xst");
        for (Element element : normal) {
            element = element.select(".xst").get(0);
            // 解析contentId
            String contentIdString = element.parent().select(".showcontent").attr("id");
            int contentId = Integer.parseInt(contentIdString.split("_")[1]);
            // 解析hrefUrl
            String href = element.attr("href");
            // 解析title标题
            String text = element.text();
            // 解析创建人和最后回复人
            Elements names = element.parent().parent().select(".by");
            // 解析创建人
            String[] createNames = names.get(0).text().split(" ");
            String createName = createNames[0];
            Date createTime = format.parse(createNames[1] + " " + createNames[2]);
            // 解析最后回复人
            String[] lastNames = names.get(1).text().split(" ");
            String lastName = lastNames[0];
            Date lastTime = format.parse(lastNames[1] + " " + lastNames[2]);
            // bean整理
            Title title = new Title();
            title.setContentId(contentId);
            title.setLastName(lastName);
            title.setLastTime(lastTime);
            titles.add(title);
        }
    }

    public void getContents(Title title, List<Content> contents) throws IOException, ParseException {
        String baseUrl = title.getUrl();
        Document doc = Jsoup.connect(baseUrl)
                .header("Content-Type", "text/html; charset=utf-8")
                .get();
        // http://bbs.leiting.com/thread-452892-1-1.html
        // 获取页数
        int size = 1;
        Elements elements = doc.getElementsByAttributeValue("name", "custompage");
        if (!CollectionUtils.isEmpty(elements)){
            Element attr = elements.get(0);
            Element span = attr.parent().getElementsByTag("span").get(0);
            String titleTi = span.attr("title");
            size = Integer.parseInt(titleTi.substring(2, titleTi.length() - 2));
        }
        int num = 1;
        while (size > 0){
            String url = baseUrl.replaceAll("(\\d+-)(\\d+)(-\\d)", "$1" + num + "$3");
            Document document = Jsoup.connect(url)
                    .header("Content-Type", "text/html; charset=utf-8")
                    .get();
            Elements contentEs = document.getElementsByAttributeValueMatching("id", "post_\\d+");
            for (Element contentEle : contentEs) {
                // 判断是否locked
                if (!CollectionUtils.isEmpty(contentEle.select(".locked"))) {
                    continue;
                }
                Content content = new Content();
                // 解析postId
                String postId = contentEle.attr("id");
                // System.out.println(postId);
                // 解析uid uName
                Element nameEle = contentEle.select(".xw1").get(0);
                Long uid = Long.valueOf(nameEle.attr("href").split("uid=")[1]);
                String uName = nameEle.text();
                // titleId
                // 解析content
                String text = contentEle.select(".t_f").get(0).text();
                // 解析createTime
                Element idEle = contentEle.getElementsByAttributeValueMatching("id", "authorposton\\d+").get(0);
                String[] createTimes = idEle.text().split(" ");
                Date createTime = format.parse(createTimes[1] + " " + createTimes[2]);

                // bean
                content.setContent(text);
                content.setCreateTime(createTime);
                content.setPostId(postId);
                content.setUid(uid);
                content.setuName(uName);
                content.setTitleId(title.getId());

                contents.add(content);
            }
            num++;
            size--;
        }
    }

    /**
     * 获取 titles
     * @param url
     * @param titles
     * @throws IOException
     * @throws ParseException
     */
    private void getTitles(String url, List<Title> titles) throws IOException, ParseException {
        Document doc = Jsoup.connect(url)
                .header("Content-Type", "text/html; charset=utf-8")
                .get();

        Elements normal = doc.getElementsByAttributeValueStarting("id", "normalthread");
        // Elements elements = doc.select(".xst");
        for (Element element : normal) {
            element = element.select(".xst").get(0);
            Title title = new Title();
            // 解析contentId
            String contentIdString = element.parent().select(".showcontent").attr("id");
            int contentId = Integer.parseInt(contentIdString.split("_")[1]);
            // 解析hrefUrl
            String href = element.attr("href");
            // 解析title标题
            String text = element.text();
            // 解析创建人和最后回复人
            Elements names = element.parent().parent().select(".by");
            // 解析创建人
            String[] createNames = names.get(0).text().split(" ");
            String createName = createNames[0];
            Date createTime = format.parse(createNames[1] + " " + createNames[2]);
            // 解析最后回复人
            String[] lastNames = names.get(1).text().split(" ");
            String lastName = lastNames[0];
            Date lastTime = format.parse(lastNames[1] + " " + lastNames[2]);
            // bean整理
            title.setTitle(text);
            title.setUrl(href);
            title.setContentId(contentId);
            title.setCreateName(createName);
            title.setCreateTime(createTime);
            title.setLastName(lastName);
            title.setLastTime(lastTime);
            titles.add(title);
        }
    }

    @GetMapping("leitingUpdate")
    public void leitingUpdate(@RequestParam("num") Integer num){
        // List<Title> titles = titleMapper.selectAll();
        // contentMapper.updateBatchs(titles);
        try {
            leiTingService.updateByNum(num);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
