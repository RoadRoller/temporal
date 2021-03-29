package com.nprog.temporal.sandbox.activities.impl;

import com.nprog.temporal.sandbox.activities.ISimpleActivities;
import io.temporal.activity.Activity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.text.MessageFormat.format;

public class SimpleActivitiesImpl implements ISimpleActivities {
    private static Logger logger = LoggerFactory.getLogger(SimpleActivitiesImpl.class);

    @Override
    public String firstStep(String param, int workingTime) {
        startHeartbeats();

        logger.info(format("firstStep, param: {0}, thread: {1}", param, Thread.currentThread().getName()));
        sleepSeconds(workingTime);
        logger.info(format("firstStep finished, param: {0}, thread: {1}", param, Thread.currentThread().getName()));

        return "First step: " + param;
    }

    @Override
    public String secondStep(String param, int workingTime) {
        startHeartbeats();

        logger.info(format("secondStep, param: {0}, thread: {1}", param, Thread.currentThread().getName()));
        sleepSeconds(workingTime);
        logger.info(format("secondStep finished, param: {0}, thread: {1}", param, Thread.currentThread().getName()));

        return "Second step: " + param;
    }

    @Override
    public String simulateTask(String taskName, int workingTime) {
        startHeartbeats();

        logger.info(format("Simulate task {0} started", taskName));
        sleepSeconds(workingTime);
        logger.info(format("Simulate task {0} completed", taskName));

        return taskName;
    }

    private void sleepSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException ignore) {
        }
    }

    private void startHeartbeats() {
        new ActivityHeartbeat(Activity.getExecutionContext(), 1000);
    }
}
