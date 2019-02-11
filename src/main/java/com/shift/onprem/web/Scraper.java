package com.shift.onprem.web;

import com.shift.onprem.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Scraper {

    private static final Logger logger = LogManager.getLogger(Scraper.class);

    public Scraper() { }

    public List<String> readImageURLs(String url) {

        List<String> urlSrcList = new ArrayList<>();

        try {
            logger.debug("Connecting to " + url);
            Document doc = Jsoup.connect(url).get();
            Elements images = doc.select("img");

            logger.debug("Found " + images.size() + " images:" + images.size());

            for (Element img : images) {
                urlSrcList.add(img.attr("src"));
                logger.debug("Added img src: " + img.attr("src"));

                logger.debug(Util.toStringJson(img.attributes()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return urlSrcList;
    }





}
