package com.nprog.temporal.sandbox.workflows.impl;

import com.nprog.temporal.sandbox.config.IMainTaskQueue;
import com.nprog.temporal.sandbox.workflows.ISimpleWorkflow;
import com.nprog.temporal.sandbox.workflows.IWorkflowFactory;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class WorkflowFactory implements IWorkflowFactory {
    @Autowired
    WorkflowClient workflowClient;

    @Override
    public ISimpleWorkflow createSimpleWorkflow() {
        WorkflowOptions workflowOptions = WorkflowOptions.newBuilder()
                .setTaskQueue(IMainTaskQueue.MAIN_TASK_QUEUE)
                .setWorkflowId(UUID.randomUUID().toString())
                .build();
        return workflowClient.newWorkflowStub(ISimpleWorkflow.class, workflowOptions);
    }
}
