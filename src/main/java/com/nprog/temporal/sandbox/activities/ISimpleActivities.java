package com.nprog.temporal.sandbox.activities;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface ISimpleActivities {
    @ActivityMethod
    String firstStep(String param);

    @ActivityMethod
    String secondStep(String param);
}
