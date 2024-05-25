package com.otis.play.util;

import com.otis.play.model.entity.NewsArticle;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.URI;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class NodeUtil {
    public static List<NewsArticle> extractNodeList(NodeList nodes) {
        List<NewsArticle> newsArticleList = new ArrayList<>();

        for (int i = 0; i < nodes.getLength(); i++) {
            Element element = (Element) nodes.item(i);

            NodeList childNodes = element.getChildNodes();

            NewsArticle article = extractChildNode(childNodes);

            article.setContent(extractHtmlContent(article.getUrl()));

            newsArticleList.add(article);
        }

        return newsArticleList;
    }

    private static String extractHtmlContent(String urlContent) {
        String result = "";

        try {
            HtmlCleaner cleaner = new HtmlCleaner();

            CleanerProperties props = cleaner.getProperties();
            props.setAllowHtmlInsideAttributes(true);
            props.setAllowMultiWordAttributes(true);
            props.setRecognizeUnicodeChars(true);
            props.setOmitComments(true);

            URL url = URI.create(urlContent).toURL();
            BufferedReader in = new BufferedReader( new InputStreamReader(url.openStream()));

            StringBuilder htmlContent = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                htmlContent.append(inputLine);
            }
            in.close();

            TagNode tagNode = cleaner.clean(htmlContent.toString());
            Document doc = new DomSerializer(props).createDOM(tagNode);
            XPath xpath = XPathFactory.newInstance().newXPath();
            String str = (String) xpath.evaluate("//div[contains(@class, 'post-body-content')]", doc, XPathConstants.STRING);
            result = str.trim().replaceAll("\\t","").replaceAll("\\n","");
        } catch (Exception e) {
            System.out.println("Error when extractHtmlContent: " + e.getMessage());
        }

        return result;
    }

    private static NewsArticle extractChildNode(NodeList childNodes) {
        NewsArticle article = new NewsArticle();

        for (int k = 0; k < childNodes.getLength(); k++) {
            Node child = childNodes.item(k);
            if (child.getNodeType() != Node.TEXT_NODE) {
                String childNodeName = child.getNodeName();
                String data = extractChildNodeValue(child);

                if (null != data) {
                    if (childNodeName.equalsIgnoreCase("title")) {
                        article.setTitle(child.getFirstChild().getNodeValue());
                    } else if (childNodeName.equalsIgnoreCase("link")) {
                        article.setUrl(child.getFirstChild().getNodeValue());
                    } else if (childNodeName.equalsIgnoreCase("pubDate")) {
                        Date date = DateUtil.convertStringToDate(child.getFirstChild().getNodeValue());
                        article.setPublishedDate(date);

                        Timestamp dateTimestamp = DateUtil.convertDateToTimestamp(Objects.requireNonNull(date));
                        BigInteger dateBigInteger = DateUtil.convertTimestampToBigInteger(dateTimestamp);
                        article.setArticleTs(dateBigInteger);
                    }
                }
            }
        }

        return article;
    }

    private static String extractChildNodeValue(Node child) {
        String result = null;

        if (null != child.getFirstChild() && child.getFirstChild().getNodeType() == Node.TEXT_NODE) {
            result = child.getFirstChild().getNodeValue();
        }

        return result;
    }
}
