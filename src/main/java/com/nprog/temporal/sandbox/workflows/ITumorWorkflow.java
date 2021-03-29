package com.nprog.temporal.sandbox.workflows;

import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface ITumorWorkflow {
    // To test signals for tumor/normal pair work flow synchronization
    @WorkflowMethod
    String doWork(String param);

    @SignalMethod
    void notifyNormalCompleted();
}
