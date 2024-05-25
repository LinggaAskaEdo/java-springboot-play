package com.otis.play.controller.scheduler;

import com.otis.play.service.rss.RSSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RSSScheduler {
    private final RSSService rssService;

    @Autowired
    public RSSScheduler(RSSService rssService) {
        this.rssService = rssService;
    }

    @Scheduled(fixedRate = 10000, initialDelay = 5000)
    private void rssScheduler() {
        rssService.GetNews();
    }
}
