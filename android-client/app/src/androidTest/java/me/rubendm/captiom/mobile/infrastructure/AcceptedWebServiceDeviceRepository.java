package me.rubendm.captiom.mobile.infrastructure;

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import me.rubendm.captiom.mobile.model.Device;
import me.rubendm.captiom.mobile.model.ScreenHeight;

import static me.rubendm.captiom.mobile.infrastructure.Server.RequestMethod;
import static me.rubendm.captiom.mobile.infrastructure.WebServiceDeviceRepository.OnSaveFailedListener;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class AcceptedWebServiceDeviceRepository extends AndroidTestCase {

    @SmallTest
    public void test_should_call_to_server_to_register_device() throws InterruptedException {
        Server server = successfulServer();
        OnSaveFailedListener listener = mock(OnSaveFailedListener.class);
        WebServiceDeviceRepository repository = new WebServiceDeviceRepository(server, listener);
        repository.save(device());
        waitForAsyncTask();
        verify(server).connect("/devices", RequestMethod.POST);
        verifyNoMoreInteractions(listener);
    }

    @SmallTest
    public void test_should_notify_error_while_saving_device() throws InterruptedException {
        Server server = failingServer();
        OnSaveFailedListener listener = mock(OnSaveFailedListener.class);
        WebServiceDeviceRepository repository = new WebServiceDeviceRepository(server, listener);
        Device device = device();

        repository.save(device);
        waitForAsyncTask();
        verify(server).connect("/devices", RequestMethod.POST);
        verify(listener).failed("");
    }

    private Server successfulServer() {
        return server("", true);
    }

    private Server failingServer() {
        return server("", false);
    }

    private Server server(String body, boolean succeeded) {
        Server server = mock(Server.class);
        ServerConnection connection = mock(ServerConnection.class);
        ServerConnection.ServerResponse response = mock(ServerConnection.ServerResponse.class);
        when(response.body()).thenReturn(body);
        when(response.succeeded()).thenReturn(succeeded);
        when(connection.addParameter(anyString(), anyString())).thenReturn(connection);
        when(connection.send()).thenReturn(response);
        when(server.connect(anyString(), any(RequestMethod.class))).thenReturn(connection);
        return server;
    }

    private Device device() {
        return new Device("111", new ScreenHeight(1, 1), "Nexus 5");
    }

    private void waitForAsyncTask() throws InterruptedException {
        Thread.sleep(500);
    }
}
