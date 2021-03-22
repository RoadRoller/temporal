package com.nprog.temporal.sandbox.activities.impl;

import com.nprog.temporal.sandbox.activities.ISimpleActivities;
import io.temporal.activity.Activity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.text.MessageFormat.format;

public class SimpleActivitiesImpl implements ISimpleActivities {
    protected static Logger logger = LoggerFactory.getLogger(SimpleActivitiesImpl.class);

    @Override
    public String firstStep(String param) {
        logger.info(format(
                "firstStep, param: {0}, thread: {1}", param, Thread.currentThread().getName()));

        try {
            int pause = Integer.parseInt(param);
            for (int i = 0; i < pause; i++) {
                Activity.getExecutionContext().heartbeat(i);
            }
            Thread.sleep(pause * 1000);
        } catch (NumberFormatException | InterruptedException ignore) {
        }

        logger.info(format(
                "firstStep finished, param: {0}, thread: {1}", param, Thread.currentThread().getName()));

        return "First step: " + param;
    }

    @Override
    public String secondStep(String param) {
        logger.info(format(
                "secondStep, param: {0}, thread: {1}", param, Thread.currentThread().getName()));

        return "Second step: " + param;
    }
}
