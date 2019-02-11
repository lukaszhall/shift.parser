package com.shift.onprem;


import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.shift.onprem.web.Scraper;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import picocli.CommandLine;

import java.util.List;


public class Cli {

    private static Logger logger;

    static {
        Util.configureLogger(Level.OFF);
        logger = LogManager.getLogger(Cli.class);
    }

    static class Params {
        @CommandLine.Option(names = { "-h", "--help" },
                usageHelp = true,
                description = "display a help message")
        private boolean helpRequested;

        @CommandLine.Parameters(paramLabel = "URL",
                description = "one ore more urls to parse")
        String[] urls;
    }

    private Scraper scraper;


    @Inject
    public Cli(Scraper scraper) {
        this.scraper = scraper;
    }


    public static void main(String[] args) {

        logger.debug("Args : " + String.join(" ", args));
        Params params = new Params();
        new CommandLine(params).parse(args);
        logger.debug("URLs:" + Util.toStringJson(params.urls));

        Injector injector = Guice.createInjector(new ScraperModule());
        Cli cli = injector.getInstance(Cli.class);

        List<String> images = cli.findImagesUrls(params.urls[0]);

        for (String img : images) {
            System.out.println(img);
        }
    }



    public List<String> findImagesUrls(String url) {
        return scraper.readImageURLs(url);
    }


}
