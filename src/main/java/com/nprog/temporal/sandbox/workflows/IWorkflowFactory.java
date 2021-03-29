package com.nprog.temporal.sandbox.workflows;

public interface IWorkflowFactory {
    ISimpleWorkflow createSimpleWorkflow();

    IParallelWorkflow createParallelWorkflow();

    ITumorWorkflow createTumorWorkflow();

    INormalWorkflow createNormalWorkflow();
}
