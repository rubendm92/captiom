package me.rubendm.captiom.mobile.activities

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager
import com.google.vrtoolkit.cardboard.CardboardActivity
import me.rubendm.captiom.R
import kotlinx.android.synthetic.main.activity_optotype.*
import me.rubendm.captiom.mobile.actions.RegisterDevice
import me.rubendm.captiom.mobile.infrastructure.RegistrationIdProvider
import me.rubendm.captiom.mobile.infrastructure.Server
import me.rubendm.captiom.mobile.infrastructure.WebServiceDeviceRepository
import me.rubendm.captiom.mobile.model.Device
import me.rubendm.captiom.mobile.model.ScreenHeight
import me.rubendm.captiom.mobile.views.FakeRenderer

class OptotypeActivity : CardboardActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_optotype)
        registerDevice()
        setUpCardboardView()
        registerReceivers(LocalBroadcastManager.getInstance(this))
    }

    private fun registerDevice() {
        val repository = WebServiceDeviceRepository(Server()) {
            Log.d("OptotypeActivity", "Device could not be registered. $it")
        }
        val action: RegisterDevice = RegisterDevice(repository)
        RegistrationIdProvider(applicationContext).loadRegistrationId { action.execute(buildDevice(it)) }
    }

    private fun setUpCardboardView() {
        cardboard.setRenderer(FakeRenderer())
        cardboardView = cardboard
    }

    private fun registerReceivers(broadcastManager: LocalBroadcastManager) {
        broadcastManager.registerReceiver(receiver({ drawChar(it) }), IntentFilter("char"));
        broadcastManager.registerReceiver(receiver({ clearOptotype() }), IntentFilter("clear"));
    }

    private fun buildDevice(registrationId: String): Device {
        return Device(registrationId, screen(), Build.MODEL)
    }

    private fun screen(): ScreenHeight {
        val metrics = metrics()
        val height = if(isInLandscape()) metrics.heightPixels else metrics.widthPixels;
        val heightInMeters = if (isInLandscape()) heightInMeters(metrics) else widthInMeters(metrics);
        return ScreenHeight(height, heightInMeters)
    }

    private fun metrics(): DisplayMetrics {
        val metrics = DisplayMetrics();
        (applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.getMetrics(metrics);
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

    private fun receiver(action: (Intent?) -> Unit): BroadcastReceiver {
        return object: BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) = action.invoke(intent)
        }
    }

    private fun drawChar(intent: Intent?) {
        if (intent != null) {
            optotype.drawChar(intent.getStringExtra("char"), intent.getIntExtra("detail", 0), intent.getStringExtra("eye"))
        }
    }

    private fun clearOptotype() {
        optotype.clear()
    }
}
