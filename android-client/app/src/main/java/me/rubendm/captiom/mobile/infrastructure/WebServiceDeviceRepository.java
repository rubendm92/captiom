package me.rubendm.captiom.mobile.infrastructure;

import android.os.AsyncTask;

import me.rubendm.captiom.mobile.infrastructure.Server.RequestMethod;
import me.rubendm.captiom.mobile.model.Device;

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
        void failed(String reason);
    }

    private class SaveDeviceTask extends AsyncTask<Device, Object, Void> {

        @Override
        protected Void doInBackground(Device... devices) {
            for (Device device : devices)
                saveDevice(device);
            return null;
        }

        private void saveDevice(Device device) {
            ServerConnection.ServerResponse response = server.connect("/devices", RequestMethod.PUT)
                    .addParameter("notificationId", device.getRegistrationId())
                    .addParameter("pixelHeight", String.valueOf(device.getScreenHeight().getPixels()))
                    .addParameter("metersHeight", String.valueOf(device.getScreenHeight().getHeightInMeters()))
                    .addParameter("modelName", device.getModelName())
                    .send();
            if (!response.succeeded()) saveFailedListener.failed(response.body());
        }
    }
}
