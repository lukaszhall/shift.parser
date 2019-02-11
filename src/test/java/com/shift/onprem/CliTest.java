package com.shift.onprem;

import static org.junit.Assert.*;
import static org.mockito.hamcrest.MockitoHamcrest.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.CoreMatchers.*;

import com.shift.onprem.web.Scraper;
import org.apache.logging.log4j.Level;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;


@RunWith(MockitoJUnitRunner.class)
public class CliTest {

    @Mock
    Scraper scraper;

    @InjectMocks
    private Cli cli;

    @BeforeClass
    public static void setup() {
        Util.configureLogger(Level.OFF);
    }

    @Test
    public void findImagesUrls() {
        MockitoAnnotations.initMocks(this);

        List<String> srcs = new ArrayList<>();
        srcs.add("test");
        when(scraper.readImageURLs(anyString())).thenReturn(srcs);

        List<String> cliSrcs = cli.findImagesUrls("https://www.google.com");
        verify(scraper, times(1)).readImageURLs("https://www.google.com");

        assertThat(srcs, is(cliSrcs));
    }
}