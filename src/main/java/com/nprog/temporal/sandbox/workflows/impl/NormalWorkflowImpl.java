package com.nprog.temporal.sandbox.workflows.impl;

import com.nprog.temporal.sandbox.activities.ISimpleActivities;
import com.nprog.temporal.sandbox.workflows.INormalWorkflow;
import com.nprog.temporal.sandbox.workflows.ITumorWorkflow;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class NormalWorkflowImpl implements INormalWorkflow {

    private static Logger logger = LoggerFactory.getLogger(NormalWorkflowImpl.class);

    private ActivityOptions ACTIVITY_OPTIONS = ActivityOptions.newBuilder()
            .setScheduleToCloseTimeout(Duration.ofDays(1))
            .setHeartbeatTimeout(Duration.ofSeconds(3))
            .setRetryOptions(RetryOptions.newBuilder()
                    .setMaximumAttempts(3)
                    .build())
            .build();

    private final ISimpleActivities simpleActivities = Workflow.newActivityStub(ISimpleActivities.class, ACTIVITY_OPTIONS);

    @Override
    public String doWork(String param, String tumorWorkflowId) {
        logger.info("Started tumor workflow");

        int pause = 0;
        try {
            pause = Integer.parseInt(param);
        } catch (NumberFormatException ignore) {
        }

        simpleActivities.simulateTask("NORMAL_CLEANUP", pause);
        simpleActivities.simulateTask("NORMAL_ALIGNMENT", pause);
        simpleActivities.simulateTask("NORMAL_VARIANT_DISCOVERY_PREPROCESSING", pause);

        logger.info("Normal preprocessing completed, send signal to tumor");
        ITumorWorkflow tumorWorkflow = Workflow.newExternalWorkflowStub(ITumorWorkflow.class, tumorWorkflowId);
        tumorWorkflow.notifyNormalCompleted();

        simpleActivities.simulateTask("NORMAL_VARIANT_DISCOVERY", pause);

        return "Tumor workflow completed";
    }


}
