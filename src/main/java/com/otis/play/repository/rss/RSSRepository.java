package com.otis.play.repository.rss;

import com.otis.play.model.entity.NewsArticle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RSSRepository extends JpaRepository<NewsArticle, Long> {
    List<NewsArticle> findByUrl(String url);
}
