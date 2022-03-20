package gargoyle.ct.ui.impl

import gargoyle.ct.pref.CTPreferences
import gargoyle.ct.pref.impl.prop.CTPrefProperty
import gargoyle.ct.sound.Audio
import gargoyle.ct.sound.AudioClip
import gargoyle.ct.ui.CTStateUpdatable

class CTSoundUpdatable(preferences: CTPreferences) : CTStateUpdatable() {
    private val clip: AudioClip
    private val sound: CTPrefProperty<Boolean>

    init {
        sound = preferences.sound()
        clip = Audio.load(Audio::class.java.getResource(LOC_SOUND)!!)
    }

    override fun onStateChange(oldState: State?, newState: State?) {
        if (oldState != null && sound.get()) {
            clip.play()
        }
    }

    companion object {
        private const val LOC_SOUND = "/wav/cup.aiff"
    }
}
