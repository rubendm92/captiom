package me.rubendm.captiom.mobile.views

import com.google.vrtoolkit.cardboard.CardboardView
import com.google.vrtoolkit.cardboard.Eye
import com.google.vrtoolkit.cardboard.HeadTransform
import com.google.vrtoolkit.cardboard.Viewport
import javax.microedition.khronos.egl.EGLConfig

class FakeRenderer : CardboardView.StereoRenderer {

    override fun onNewFrame(p0: HeadTransform?) {
    }

    override fun onSurfaceChanged(p0: Int, p1: Int) {
    }

    override fun onSurfaceCreated(p0: EGLConfig?) {
    }

    override fun onFinishFrame(p0: Viewport?) {
    }

    override fun onDrawEye(p0: Eye?) {
    }

    override fun onRendererShutdown() {
    }
}