package me.rubendm.captiom.mobile.infrastructure

import android.content.Context
import android.os.AsyncTask
import com.google.android.gms.gcm.GoogleCloudMessaging
import com.google.android.gms.iid.InstanceID
import me.rubendm.captiom.R

class RegistrationIdProvider {

    private val context: Context;

    constructor(context: Context) {
        this.context = context
    }

    fun loadRegistrationId(callback: (String) -> Unit) {
        RegistrationTask(context, callback).execute();
    }

    private class RegistrationTask(val context: Context, val callback: (String) -> Unit) : AsyncTask<Void, Void, String>() {

        override fun doInBackground(vararg params: Void?): String? {
            val instance = InstanceID.getInstance(context)
            val sender = context.getString(R.string.gcm_defaultSenderId)
            return instance.getToken(sender, GoogleCloudMessaging.INSTANCE_ID_SCOPE, null)

        }

        override fun onPostExecute(result: String?) {
            if (result != null) {
                callback.invoke(result)
            }
        }
    }
}