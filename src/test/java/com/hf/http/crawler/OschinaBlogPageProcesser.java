package com.hf.http.crawler;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.monitor.SpiderMonitor;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;

import javax.management.JMException;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author code4crafter@gmail.com <br>
 */
public class OschinaBlogPageProcesser implements PageProcessor {

    private Site site = Site.me().setDomain("my.oschina.net").addStartUrl("https://my.oschina.net/flashsword/blog");

    @Override
    public void process(Page page) {
    	List<String> list = page.getHtml().css("div.pages.sm-hide").xpath("//li").links().all();
    	page.addTargetRequests(list);
    	List<String> links = page.getHtml().links().regex("https://my\\.oschina\\.net/flashsword/blog/\\d+").all();
        page.addTargetRequests(links);
        page.putField("title", page.getHtml().xpath("//div[@class='blog-heading']/div[@class='title']/text()").toString());
        page.putField("content", page.getHtml().xpath("//div[@class='BlogContent']/tidyText()").toString());
        page.putField("tags",page.getHtml().xpath("//div[@class='blog-opr']/div[@class='tags']/span/a/text()").all());
        if(StringUtils.isBlank(page.getResultItems().get("title")) ){
        	page.setSkip(true);
        }
    }

    @Override
    public Site getSite() {
        return site;

    }

    public static void main(String[] args) throws JMException {
        Spider spider = Spider.create(new OschinaBlogPageProcesser()).setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(2000)));
        SpiderMonitor.instance().register(spider);
        spider.
        //addPipeline(new JsonFilePipeline("D:\\tmp\\webmagic\\")).
        thread(5).run();
    }
}
