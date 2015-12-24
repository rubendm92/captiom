package me.rubendm.captiom.mobile.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import me.rubendm.captiom.mobile.model.Eye
import java.util.*

class OptotypeView : LinearLayout {

    private val eyes: Map<Eye, EyeView>

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        orientation = HORIZONTAL;
        visibility = View.VISIBLE;
        eyes = mapOf(Eye.LEFT to createAndAddEyeView(context),
                Eye.RIGHT to createAndAddEyeView(context))
    }

    fun drawChar(character: String, heightInMeters: Float, eye: Eye) {
        clear()
        eyes[eye]?.showCharacter(character, heightInMeters)
    }

    fun clear() {
        eyes.values.forEach { it.clear() }
    }

    private fun createAndAddEyeView(context: Context?): EyeView {
        val eye = EyeView(context);
        addView(eye);
        return eye
    }
}