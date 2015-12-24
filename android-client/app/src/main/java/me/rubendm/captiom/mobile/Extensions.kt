package me.rubendm.captiom.mobile

import android.content.Intent
import me.rubendm.captiom.mobile.model.Eye

public fun Intent.getOptotypeChar(): String {
    return getStringExtra("character");
}

public fun Intent.getCharHeight(): Float {
    return getFloatExtra("charHeight", 0.0f);
}

public fun Intent.getEye(): Eye {
    return Eye.valueOf(getStringExtra("eye"));
}