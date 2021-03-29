package com.nprog.temporal.sandbox.workflows.impl;

import com.nprog.temporal.sandbox.config.IMainTaskQueue;
import com.nprog.temporal.sandbox.workflows.INormalWorkflow;
import com.nprog.temporal.sandbox.workflows.IParallelWorkflow;
import com.nprog.temporal.sandbox.workflows.ISimpleWorkflow;
import com.nprog.temporal.sandbox.workflows.ITumorWorkflow;
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
        return workflowClient.newWorkflowStub(ISimpleWorkflow.class, buildOptions());
    }

    @Override
    public IParallelWorkflow createParallelWorkflow() {
        return workflowClient.newWorkflowStub(IParallelWorkflow.class, buildOptions());
    }

    @Override
    public ITumorWorkflow createTumorWorkflow() {
        return workflowClient.newWorkflowStub(ITumorWorkflow.class, buildOptions());
    }

    @Override
    public INormalWorkflow createNormalWorkflow() {
        return workflowClient.newWorkflowStub(INormalWorkflow.class, buildOptions());
    }

    private WorkflowOptions buildOptions() {
        return WorkflowOptions.newBuilder()
                .setTaskQueue(IMainTaskQueue.MAIN_TASK_QUEUE)
                .setWorkflowId(UUID.randomUUID().toString())
                .build();
    }
}
