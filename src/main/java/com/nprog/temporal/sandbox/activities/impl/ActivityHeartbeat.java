package com.nprog.temporal.sandbox.activities.impl;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.temporal.activity.ActivityExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.text.MessageFormat.format;

public class ActivityHeartbeat {

    private static Logger logger = LoggerFactory.getLogger(SimpleActivitiesImpl.class);

    public ActivityHeartbeat(ActivityExecutionContext context, int heartbeatTimeout) {
        ExecutorService heartbeatExecutor = Executors.newSingleThreadExecutor(
                new ThreadFactoryBuilder().setNameFormat("heartbeat-%d").build()
        );
        Thread activityThread = Thread.currentThread();

        heartbeatExecutor.submit(() -> {
            while (activityThread.isAlive()) {
                context.heartbeat(new Date().getTime());
                logger.info(format("Heartbeat send, activity: {0}", context.getInfo().getActivityType()));
                try {
                    Thread.sleep(heartbeatTimeout);
                } catch (InterruptedException unexpected) {
                    logger.error("Heartbeat thread was interrupted", unexpected);
                }
            }
            logger.info(format("Activity thread terminated, heartbeats stopped, activity: {0}",
                    context.getInfo().getActivityType()));
        });
    }
}
