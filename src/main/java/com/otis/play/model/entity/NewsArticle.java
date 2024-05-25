package com.otis.play.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.util.Date;

@Entity
@Table(name = "news_article")
@Getter
@Setter
@NoArgsConstructor
public class NewsArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "url")
    private String url;

    @Column(name = "content")
    private String content;

    @Column(name = "summary")
    private String summary;

    @Column(name = "article_ts")
    private BigInteger articleTs;

    @Column(name = "published_date")
    private Date publishedDate;

    @Column(name = "inserted")
    @Temporal(TemporalType.TIMESTAMP)
    private Date inserted;

    @Column(name = "updated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;
}
