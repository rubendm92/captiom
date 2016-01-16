package me.rubendm.captiom.mobile.services

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager
import com.google.android.gms.gcm.GoogleCloudMessaging
import com.google.android.gms.iid.InstanceID
import com.google.android.gms.iid.InstanceIDListenerService
import me.rubendm.captiom.R
import me.rubendm.captiom.mobile.actions.RegisterDevice
import me.rubendm.captiom.mobile.infrastructure.Server
import me.rubendm.captiom.mobile.infrastructure.WebServiceDeviceRepository
import me.rubendm.captiom.mobile.model.Device
import me.rubendm.captiom.mobile.model.ScreenHeight
import java.io.IOException

class RegistrationIntentService : IntentService(RegistrationIntentService.TAG) {

    companion object {
        private val TAG = "RegIntentService"
    }

    class InstanceIDListenerService: com.google.android.gms.iid.InstanceIDListenerService() {

        override fun onTokenRefresh() {
            startService(Intent(this, RegistrationIntentService::class.java))
        }
    }

    override fun onHandleIntent(intent: Intent) {
        try {
            sendRegistrationToServer(askForToken())
        } catch (e: Exception) {
            Log.d(TAG, "Failed to complete token refresh", e)
        }
    }

    @Throws(IOException::class)
    private fun askForToken(): String {
        val instanceID = InstanceID.getInstance(this)
        return instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                GoogleCloudMessaging.INSTANCE_ID_SCOPE, null)
    }

    private fun sendRegistrationToServer(token: String) {
        val repository = WebServiceDeviceRepository(Server()) {
            Log.d(TAG, "Device could not be registered. $it")
        }
        RegisterDevice(repository).execute(buildDevice(token))
    }

    private fun buildDevice(registrationId: String): Device {
        return Device(registrationId, screen(), Build.MODEL)
    }

    private fun screen(): ScreenHeight {
        val metrics = metrics()
        if (isInLandscape()) {
            if (metrics.heightPixels > metrics.widthPixels / 2) {
                return ScreenHeight(metrics.widthPixels / 2, widthInMeters(metrics) / 2)
            }
            return ScreenHeight(metrics.heightPixels, heightInMeters(metrics))
        }
        if (metrics.widthPixels > metrics.heightPixels / 2) {
            return ScreenHeight(metrics.heightPixels / 2, heightInMeters(metrics) / 2)
        }
        return ScreenHeight(metrics.widthPixels, widthInMeters(metrics))
    }

    private fun metrics(): DisplayMetrics {
        val metrics = DisplayMetrics();
        (getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.getMetrics(metrics);
        return metrics
    }

    private fun widthInMeters(displayMetrics: DisplayMetrics): Double {
        return (displayMetrics.widthPixels / displayMetrics.xdpi) * 0.0254;
    }

    private fun heightInMeters(displayMetrics: DisplayMetrics): Double {
        return (displayMetrics.heightPixels / displayMetrics.ydpi) * 0.0254;
    }

    private fun isInLandscape(): Boolean {
        return applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE;
    }
}

