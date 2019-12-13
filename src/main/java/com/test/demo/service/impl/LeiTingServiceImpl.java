package com.test.demo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.test.demo.entity.Content;
import com.test.demo.entity.Title;
import com.test.demo.entity.model.Page;
import com.test.demo.mapper.wf.ContentMapper;
import com.test.demo.mapper.wf.TitleMapper;
import com.test.demo.service.LeiTingService;
import com.test.demo.utils.Constant;
import com.test.demo.utils.JsonResult;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.Console;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author kun.han on 2019/12/13 9:15
 */
@Service
public class LeiTingServiceImpl implements LeiTingService {


    @Resource
    private TitleMapper titleMapper;
    @Resource
    private ContentMapper contentMapper;

    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-M-d HH:mm");


    /**
     * 定时更新雷霆论坛数据
     *
     * @param num
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateByNum(Integer num) throws Exception {
        String baseUrl = "http://bbs.leiting.com/forum-292-";
        String endUrl = ".html";
        for (int i = 1; i <= num; i++) {
            List<Title> titles = new ArrayList<>();
            String url = baseUrl + i + endUrl;
            // 获取所有的titles
            getTitles(url, titles);
            if (CollectionUtils.isEmpty(titles)){
                continue;
            }
            // 清空前num页的数据库数据
            List<Integer> contentIds = titles.stream().map(Title::getContentId).collect(Collectors.toList());
            titleMapper.deleteByContentIdIn(contentIds);
            List<Long> titleIds = titles.stream().map(Title::getId).filter(Objects::nonNull).collect(Collectors.toList());
            contentMapper.deleteByTitleIdIn(titleIds);
            // 插入title数据
            titleMapper.batchInsert(titles);
            System.out.println("插入title  -------    " + i);
            for (Title title : titles) {
                List<Content> list = new ArrayList<>();
                getContents(title, list);
                if (CollectionUtils.isEmpty(list)){
                    continue;
                }
                contentMapper.batchInsert(list);
                System.out.println("插入content  -------    " + title.getTitle());
            }
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
                Date createTime = FORMAT.parse(createTimes[1] + " " + createTimes[2]);

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
            Date createTime = FORMAT.parse(createNames[1] + " " + createNames[2]);
            // 解析最后回复人
            String[] lastNames = names.get(1).text().split(" ");
            String lastName = lastNames[0];
            Date lastTime = FORMAT.parse(lastNames[1] + " " + lastNames[2]);

            Title title = new Title();
            title.setTitle(text);
            title.setContentId(contentId);
            title.setCreateName(createName);
            title.setLastName(lastName);
            title.setCreateTime(createTime);
            title.setLastTime(lastTime);
            title.setUrl(href);
            titles.add(title);
        }
    }

    @Override
    public PageInfo<Title> getTitles(Page page, String title) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<Title> titles = titleMapper.selectAllByTitle(title);
        PageInfo<Title> info = new PageInfo<>(titles);
        return info;
    }
}
