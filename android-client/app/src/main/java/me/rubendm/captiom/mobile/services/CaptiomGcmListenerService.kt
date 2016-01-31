package me.rubendm.captiom.mobile.services

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import com.google.android.gms.gcm.GcmListenerService

class CaptiomGcmListenerService : GcmListenerService {

    private val actions: Map<String, (Bundle) -> Unit>

    constructor() : super() {
        actions = mapOf("draw" to { bundle -> sendIntent(drawIntent(bundle)) },
                "clear" to { bundle -> sendIntent(Intent("clear")) })
    }

    private fun drawIntent(bundle: Bundle): Intent {
        return Intent("draw")
                .putExtra("character", bundle.getString("character"))
                .putExtra("charHeight", bundle.getString("charHeight").toFloat())
                .putExtra("eye", bundle.getString("eye"))
    }

    override fun onMessageReceived(from: String?, data: Bundle?) {
        actions[data!!.getString("action")]?.invoke(data)
    }

    private fun sendIntent(intent: Intent) {
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}