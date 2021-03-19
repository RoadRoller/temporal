package com.nprog.temporal.sandbox.controllers;

import com.nprog.temporal.sandbox.workflows.ISimpleWorkflow;
import com.nprog.temporal.sandbox.workflows.IWorkflowFactory;
import io.temporal.client.WorkflowClient;
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

    @GetMapping("/simple/{payload}")
    String invokeSimpleWorkflow(@PathVariable String payload) {
        ISimpleWorkflow simpleWorkflow = workflowFactory.createSimpleWorkflow();
        WorkflowClient.start(simpleWorkflow::doWork, payload);

        return "OK";
    }
}
