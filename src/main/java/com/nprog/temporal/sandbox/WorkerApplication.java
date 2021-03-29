package com.nprog.temporal.sandbox;

import com.nprog.temporal.sandbox.activities.impl.SimpleActivitiesImpl;
import com.nprog.temporal.sandbox.config.IMainTaskQueue;
import com.nprog.temporal.sandbox.workflows.impl.NormalWorkflowImpl;
import com.nprog.temporal.sandbox.workflows.impl.ParallelWorkflowImpl;
import com.nprog.temporal.sandbox.workflows.impl.SimpleWorkflowImpl;
import com.nprog.temporal.sandbox.workflows.impl.TumorWorkflowImpl;
import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import io.temporal.worker.WorkerOptions;

public class WorkerApplication {

    private static final WorkerOptions WORKER_OPTIONS = WorkerOptions.newBuilder()
            .setMaxConcurrentActivityExecutionSize(1)
            .validateAndBuildWithDefaults();

    public static void main(String[] args) {
        WorkflowServiceStubs service = WorkflowServiceStubs.newInstance();
        WorkflowClient client = WorkflowClient.newInstance(service);

        WorkerFactory workerFactory = WorkerFactory.newInstance(client);
        Worker worker = workerFactory.newWorker(IMainTaskQueue.MAIN_TASK_QUEUE, WORKER_OPTIONS);

        worker.registerActivitiesImplementations(new SimpleActivitiesImpl());
        worker.registerWorkflowImplementationTypes(
                SimpleWorkflowImpl.class,
                ParallelWorkflowImpl.class,
                TumorWorkflowImpl.class,
                NormalWorkflowImpl.class
        );

        workerFactory.start();
    }
}
