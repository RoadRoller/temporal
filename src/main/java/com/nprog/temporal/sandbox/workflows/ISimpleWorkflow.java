package com.nprog.temporal.sandbox.workflows;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface ISimpleWorkflow {
    @WorkflowMethod
    String doWork(String param);
}
