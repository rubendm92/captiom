package me.rubendm.captiom.mobile.activities

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import com.google.vrtoolkit.cardboard.CardboardActivity
import kotlinx.android.synthetic.main.activity_optotype.*
import me.rubendm.captiom.R
import me.rubendm.captiom.mobile.getCharHeight
import me.rubendm.captiom.mobile.getEye
import me.rubendm.captiom.mobile.getOptotypeChar
import me.rubendm.captiom.mobile.model.Eye
import me.rubendm.captiom.mobile.services.RegistrationIntentService
import me.rubendm.captiom.mobile.views.FakeRenderer

class OptotypeActivity : CardboardActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_optotype)
        startService(Intent(this, RegistrationIntentService::class.java))
        setUpCardboardView()
        registerReceivers(LocalBroadcastManager.getInstance(this))
    }

    private fun setUpCardboardView() {
        cardboard.setRenderer(FakeRenderer())
        cardboardView = cardboard
    }

    private fun registerReceivers(broadcastManager: LocalBroadcastManager) {
        broadcastManager.registerReceiver(receiver({ drawChar(it) }), IntentFilter("draw"));
        broadcastManager.registerReceiver(receiver({ clearOptotype() }), IntentFilter("clear"));
    }

    private fun receiver(action: (Intent?) -> Unit): BroadcastReceiver {
        return object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) = action.invoke(intent)
        }
    }

    private fun drawChar(intent: Intent?) {
        if (intent != null) {
            optotype.drawChar(intent.getOptotypeChar(), intent.getCharHeight(), intent.getEye())
        }
    }

    private fun clearOptotype() {
        optotype.clear()
    }
}
