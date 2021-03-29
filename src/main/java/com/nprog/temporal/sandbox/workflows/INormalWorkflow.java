package com.nprog.temporal.sandbox.workflows;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface INormalWorkflow {
    // To test signals for tumor/normal pair work flow synchronization
    @WorkflowMethod
    String doWork(String param, String tumorWorkflowId);
}
