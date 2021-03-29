package com.nprog.temporal.sandbox.workflows.impl;

import com.nprog.temporal.sandbox.activities.ISimpleActivities;
import com.nprog.temporal.sandbox.workflows.IParallelWorkflow;
import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Async;
import io.temporal.workflow.Promise;
import io.temporal.workflow.Workflow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import static java.text.MessageFormat.format;

public class ParallelWorkflowImpl implements IParallelWorkflow {

    private static Logger logger = LoggerFactory.getLogger(ParallelWorkflowImpl.class);

    private ActivityOptions ACTIVITY_OPTIONS = ActivityOptions.newBuilder()
            .setScheduleToCloseTimeout(Duration.ofDays(1))
            .setHeartbeatTimeout(Duration.ofSeconds(3))
            .build();

    private final ISimpleActivities simpleActivities = Workflow.newActivityStub(ISimpleActivities.class, ACTIVITY_OPTIONS);

    @Override
    public String doWork(String param) {
        logger.info(format(
                "perform parallel workflow, param: {0}, thread: {1}", param, Thread.currentThread().getName()));

        int pause = 0;
        try {
            pause = Integer.parseInt(param);
        } catch (NumberFormatException ignore) {
        }

        Promise<String> firstPromise = Async.function(simpleActivities::firstStep, "First " + param, pause);
        Promise<String> secondPromise = Async.function(simpleActivities::secondStep, "Second " + param, pause / 2);

        logger.info("Parallel workflow invoke two activities");

        Promise.allOf(firstPromise, secondPromise).get();

        logger.info("Parallel workflow two activities completed");

        return firstPromise.get() + " " + secondPromise.get();
    }
}
