package com.nprog.temporal.sandbox.workflows.impl;

import com.nprog.temporal.sandbox.activities.ISimpleActivities;
import com.nprog.temporal.sandbox.workflows.ITumorWorkflow;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class TumorWorkflowImpl implements ITumorWorkflow {

    private static Logger logger = LoggerFactory.getLogger(TumorWorkflowImpl.class);

    private ActivityOptions ACTIVITY_OPTIONS = ActivityOptions.newBuilder()
            .setScheduleToCloseTimeout(Duration.ofDays(1))
            .setHeartbeatTimeout(Duration.ofSeconds(3))
            .setRetryOptions(RetryOptions.newBuilder()
                    .setMaximumAttempts(3)
                    .build())
            .build();

    private final ISimpleActivities simpleActivities = Workflow.newActivityStub(ISimpleActivities.class, ACTIVITY_OPTIONS);

    private boolean isNormalCompleted = false;

    @Override
    public String doWork(String param) {
        logger.info("Started tumor workflow");

        int pause = 0;
        try {
            pause = Integer.parseInt(param);
        } catch (NumberFormatException ignore) {
        }

        simpleActivities.simulateTask("TUMOR_CLEANUP", pause);
        simpleActivities.simulateTask("TUMOR_ALIGNMENT", pause);
        simpleActivities.simulateTask("TUMOR_VARIANT_DISCOVERY_PREPROCESSING", pause);

        logger.info("Tumor preprocessing completed, wait for normal");
        Workflow.await(() -> isNormalCompleted);
        simpleActivities.simulateTask("TUMOR_VARIANT_DISCOVERY", pause);

        return "Tumor workflow completed";
    }

    @Override
    public void notifyNormalCompleted() {
        logger.info("Received signal that normal completed");
        isNormalCompleted = true;
    }
}
