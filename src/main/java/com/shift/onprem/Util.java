package com.shift.onprem;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;

public class Util {

    private static Gson gsonPrettyPrinter = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

    public static String toStringReflect(Object obj) {
        return ReflectionToStringBuilder.toString(obj);
    }

    public static String toStringJson(Object obj) {
        return gsonPrettyPrinter.toJson(obj);
    }



    public static void configureLogger(Level filterLevel) {

        ConfigurationBuilder<BuiltConfiguration> builder = ConfigurationBuilderFactory.newConfigurationBuilder();
        builder.setStatusLevel(Level.ERROR);
        builder.setConfigurationName("ParserConfig");
        builder.add(builder.newFilter("ThresholdFilter", Filter.Result.ACCEPT, Filter.Result.NEUTRAL)
                           .addAttribute("level", filterLevel));

        AppenderComponentBuilder appenderBuilder =
                builder.newAppender("Stdout", "CONSOLE")
                       .addAttribute("target", ConsoleAppender.Target.SYSTEM_OUT);
        appenderBuilder.add(builder.newLayout("PatternLayout")
                                   .addAttribute("pattern", "%d [%t] %-5level: %msg%n%throwable"));
        appenderBuilder.add(builder.newFilter("MarkerFilter", Filter.Result.DENY, Filter.Result.NEUTRAL)
                                   .addAttribute("marker", "FLOW"));
        builder.add(appenderBuilder);

        builder.add(builder.newLogger("org.apache.logging.log4j", Level.DEBUG)
                           .add(builder.newAppenderRef("Stdout"))
                           .addAttribute("additivity", false));
        builder.add(builder.newRootLogger(Level.ERROR)
                           .add(builder.newAppenderRef("Stdout")));
        LoggerContext ctx = Configurator.initialize(builder.build());
    }

}
