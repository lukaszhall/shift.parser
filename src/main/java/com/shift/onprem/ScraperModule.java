package com.shift.onprem;

import com.google.inject.AbstractModule;
import com.shift.onprem.web.Scraper;

public class ScraperModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Scraper.class);
    }

}
