package me.rubendm.captiom.mobile.views

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.ViewGroup

class EyeView(context: Context?, attrs: AttributeSet?) : ViewGroup(context, attrs) {

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
    }

    fun showImage(image: Drawable) {
        background = image
    }

    fun clear() {
        background = null
    }
}