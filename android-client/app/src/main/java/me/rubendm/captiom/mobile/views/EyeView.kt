package me.rubendm.captiom.mobile.views

import android.content.Context
import android.graphics.*
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

class EyeView : ViewGroup {

    private val optotypeFont: Typeface
    private var currentChar: String? = null
    private var charSize: Float? = null

    constructor(context: Context?) : super(context) {
        visibility = View.VISIBLE
        layoutParams = layoutParams()
        optotypeFont = Typeface.createFromAsset(context!!.assets, "fonts/Optotypes.ttf")
        setWillNotDraw(false)
    }

    private fun layoutParams(): LinearLayout.LayoutParams {
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f)
        params.setMargins(0, 0, 0, 0)
        return params
    }

    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)
        if (canvas != null) {
            updateView(canvas)
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
    }

    fun showCharacter(character: String, size: Float) {
        currentChar = character
        charSize = size
        redraw()
    }

    fun clear() {
        currentChar = null
        charSize = null
        redraw()
    }

    private fun updateView(canvas: Canvas) {
        if (currentChar != null) {
            val paint = fillBackground(canvas, Color.WHITE)
            drawCharacter(canvas, paint)
        } else {
            fillBackground(canvas, Color.BLACK)
        }
    }

    private fun fillBackground(canvas: Canvas, color: Int): Paint {
        val paint = Paint()
        paint.color = color
        paint.style = Paint.Style.FILL
        canvas.drawPaint(paint)
        return paint
    }

    private fun drawCharacter(canvas: Canvas, paint: Paint) {
        paint.setTypeface(optotypeFont)
        paint.color = Color.BLACK
        paint.textSize = charSize!!
        val bounds = Rect()
        paint.getTextBounds(currentChar, 0, 1, bounds)
        val x = (canvas.width / 2) - (bounds.width() / 2).toFloat()
        val y = ((canvas.height / 2) - ((paint.descent() + paint.ascent()) / 2))
        canvas.drawText(currentChar, x, y, paint)
    }

    private fun redraw() {
        invalidate()
    }
}