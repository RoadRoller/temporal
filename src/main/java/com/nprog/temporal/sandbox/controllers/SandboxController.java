package com.nprog.temporal.sandbox.controllers;

import com.nprog.temporal.sandbox.activities.impl.SimpleActivitiesImpl;
import com.nprog.temporal.sandbox.config.IMainTaskQueue;
import com.nprog.temporal.sandbox.workflows.ISimpleWorkflow;
import com.nprog.temporal.sandbox.workflows.IWorkflowFactory;
import com.nprog.temporal.sandbox.workflows.impl.SimpleWorkflowImpl;
import io.temporal.client.WorkflowClient;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SandboxController {
    @Autowired
    IWorkflowFactory workflowFactory;

    @Autowired
    WorkerFactory workerFactory;

    @GetMapping("/simple/{payload}")
    String invokeSimpleWorkflow(@PathVariable String payload) {
        ISimpleWorkflow simpleWorkflow = workflowFactory.createSimpleWorkflow();
        WorkflowClient.start(simpleWorkflow::doWork, payload);
        return "OK";
    }

    @GetMapping("/add-simple-worker")
    String addSimpleWorker() {
        Worker worker = workerFactory.newWorker(IMainTaskQueue.MAIN_TASK_QUEUE);
        worker.registerWorkflowImplementationTypes(SimpleWorkflowImpl.class);
        worker.registerActivitiesImplementations(new SimpleActivitiesImpl());
        if (!workerFactory.isStarted()) {
            workerFactory.start();
        }
        return "OK";
    }
}
