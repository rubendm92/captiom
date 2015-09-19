package com.rubendm.captiom.mobile.model;

public class Device {

    public final String registrationId;
    public final ScreenHeight screenHeight;
    public final String modelName;

    public Device(String registrationId, ScreenHeight screenHeight, String modelName) {
        this.registrationId = registrationId;
        this.screenHeight = screenHeight;
        this.modelName = modelName;
    }
}
