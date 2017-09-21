package gargoyle.ct.ui.impl

import gargoyle.ct.preferences.CTPreferences
import gargoyle.ct.ui.CTApp
import gargoyle.ct.ui.CTStateUpdatable
import gargoyle.ct.util.pref.prop.CTPrefProperty
import gargoyle.ct.util.sound.Audio
import gargoyle.ct.util.sound.AudioClip
import gargoyle.ct.util.util.getResource

class CTSoundUpdatable(preferences: CTPreferences) : CTStateUpdatable() {
    private val clip: AudioClip
    private val sound: CTPrefProperty<Boolean>

    init {
        sound = preferences.sound()
        clip = Audio.load(CTApp::class.getResource(LOC_SOUND))
    }

    override fun onStateChange(oldState: State?, newState: State?) {
        if (oldState != null && sound.get()) {
            clip.play()
        }
    }

    companion object {
        private const val LOC_SOUND = "cup.aiff"
    }
}
