package com.rubendm.captiom.mobile.infrastructure;

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.rubendm.captiom.mobile.model.Device;
import com.rubendm.captiom.mobile.model.ScreenHeight;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AcceptedWebServiceDeviceRepository extends AndroidTestCase {

    @SmallTest
    public void test_should_call_to_server_to_register_device() throws InterruptedException {
        Server server = successfulServer();
        WebServiceDeviceRepository repository = new WebServiceDeviceRepository(server, null);
        repository.save(device());
        waitForAsyncTask();
        verify(server).connect("/devices", Server.RequestMethod.POST);
    }

    private Server successfulServer() {
        return server(true);
    }

    private Server server(boolean succeeded) {
        Server server = mock(Server.class);
        ServerConnection connection = mock(ServerConnection.class);
        ServerConnection.ServerResponse response = mock(ServerConnection.ServerResponse.class);
        when(response.succeeded()).thenReturn(succeeded);
        when(connection.addParameter(anyString(), anyString())).thenReturn(connection);
        when(connection.send()).thenReturn(response);
        when(server.connect(anyString(), any(Server.RequestMethod.class))).thenReturn(connection);
        return server;
    }

    private Device device() {
        return new Device("111", new ScreenHeight(1, 1), "Nexus 5");
    }

    private void waitForAsyncTask() throws InterruptedException {
        Thread.sleep(500);
    }
}
