package com.nprog.temporal.sandbox.workflows.impl;

import com.nprog.temporal.sandbox.config.IMainTaskQueue;
import com.nprog.temporal.sandbox.workflows.ISimpleWorkflow;
import com.nprog.temporal.sandbox.workflows.IWorkflowFactory;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WorkflowFactory implements IWorkflowFactory {
    private static final WorkflowOptions WORKFLOW_OPTIONS = WorkflowOptions.newBuilder()
            .setTaskQueue(IMainTaskQueue.MAIN_TASK_QUEUE)
            .build();

    @Autowired
    WorkflowClient workflowClient;

    @Override
    public ISimpleWorkflow createSimpleWorkflow() {
        return workflowClient.newWorkflowStub(ISimpleWorkflow.class, WORKFLOW_OPTIONS);
    }
}
