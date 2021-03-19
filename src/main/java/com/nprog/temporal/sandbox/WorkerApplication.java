package com.nprog.temporal.sandbox;

import com.nprog.temporal.sandbox.activities.impl.SimpleActivitiesImpl;
import com.nprog.temporal.sandbox.config.IMainTaskQueue;
import com.nprog.temporal.sandbox.workflows.impl.SimpleWorkflowImpl;
import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;

public class WorkerApplication {
    public static void main(String[] args) {
        WorkflowServiceStubs service = WorkflowServiceStubs.newInstance();
        WorkflowClient client = WorkflowClient.newInstance(service);

        WorkerFactory workerFactory = WorkerFactory.newInstance(client);
        Worker worker = workerFactory.newWorker(IMainTaskQueue.MAIN_TASK_QUEUE);

        worker.registerWorkflowImplementationTypes(SimpleWorkflowImpl.class);
        worker.registerActivitiesImplementations(new SimpleActivitiesImpl());

        workerFactory.start();
    }
}
