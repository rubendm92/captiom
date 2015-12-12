package me.rubendm.captiom.mobile.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout

class OptotypeView : LinearLayout {

    private val leftEye: EyeView;
    private val rightEye: EyeView;

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        orientation = HORIZONTAL;
        leftEye = createAndAddEyeView(context, attrs)
        rightEye = createAndAddEyeView(context, attrs)
        visibility = View.VISIBLE;
    }

    fun drawChar(character: String, detail: Int, eye: String) {

    }

    fun clear() {
        leftEye.clear()
        rightEye.clear()
    }

    private fun createAndAddEyeView(context: Context?, attrs: AttributeSet?): EyeView {
        val eye = EyeView(context, attrs);
        eye.layoutParams = layoutParams();
        addView(eye);
        return eye
    }

    private fun layoutParams(): LayoutParams {
        val params = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f);
        params.setMargins(0, 0, 0, 0);
        return params
    }
}