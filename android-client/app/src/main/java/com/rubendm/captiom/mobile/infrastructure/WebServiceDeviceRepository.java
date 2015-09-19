package com.rubendm.captiom.mobile.infrastructure;

import android.os.AsyncTask;

import com.rubendm.captiom.mobile.infrastructure.Server.RequestMethod;
import com.rubendm.captiom.mobile.infrastructure.ServerConnection.ServerResponse;
import com.rubendm.captiom.mobile.model.Device;

public class WebServiceDeviceRepository implements DeviceRepository {

    private final Server server;
    private final OnSaveFailedListener saveFailedListener;

    public WebServiceDeviceRepository(Server server, OnSaveFailedListener saveFailedListener) {
        this.server = server;
        this.saveFailedListener = saveFailedListener;
    }

    @Override
    public void save(Device device) {
        new SaveDeviceTask().execute(device);
    }

    public interface OnSaveFailedListener {
        void failed(Device device, String reason);
    }

    private class SaveDeviceTask extends AsyncTask<Device, Object, Object> {

        @Override
        protected Object doInBackground(Device... devices) {
            for (Device device : devices)
                saveDevice(device);
            return null;
        }

        private void saveDevice(Device device) {
            ServerResponse response = server.connect("/devices", RequestMethod.POST)
                    .addParameter("notificationId", device.registrationId)
                    .addParameter("pixelHeight", String.valueOf(device.screenHeight.pixels))
                    .addParameter("metersHeight", String.valueOf(device.screenHeight.heightInMeters))
                    .addParameter("modelName", device.modelName)
                    .send();
            if (!response.succeeded()) saveFailedListener.failed(device, response.body());
        }
    }
}
