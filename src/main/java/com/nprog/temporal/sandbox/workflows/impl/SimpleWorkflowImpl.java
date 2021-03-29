package com.nprog.temporal.sandbox.workflows.impl;

import com.nprog.temporal.sandbox.activities.ISimpleActivities;
import com.nprog.temporal.sandbox.workflows.ISimpleWorkflow;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import static java.text.MessageFormat.format;

public class SimpleWorkflowImpl implements ISimpleWorkflow {

    private static Logger logger = LoggerFactory.getLogger(SimpleWorkflowImpl.class);

    private ActivityOptions ACTIVITY_OPTIONS = ActivityOptions.newBuilder()
            .setScheduleToCloseTimeout(Duration.ofDays(1))
            .setHeartbeatTimeout(Duration.ofSeconds(3))
            .setRetryOptions(RetryOptions.newBuilder()
                    .setMaximumAttempts(3)
                    .build())
            .build();

    private final ISimpleActivities simpleActivities = Workflow.newActivityStub(ISimpleActivities.class, ACTIVITY_OPTIONS);

    @Override
    public String doWork(String param) {
        logger.info(format(
                "perform simple workflow, param: {0}, thread: {1}", param, Thread.currentThread().getName()));

        int pause = 0;
        try {
            pause = Integer.parseInt(param);
        } catch (NumberFormatException ignore) {
        }

        String afterFirst = simpleActivities.firstStep(param, pause);
        logger.info(format(
                "Simple workflow after first step: {0}, thread: {1}", afterFirst, Thread.currentThread().getName()));
        String afterSecond = simpleActivities.secondStep(afterFirst, pause / 2);
        logger.info(format(
                "Simple workflow after second step: {0}, thread: {1}", afterSecond, Thread.currentThread().getName()));

        return afterSecond;
    }
}
