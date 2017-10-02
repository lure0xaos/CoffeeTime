package gargoyle.ct.ui.impl;

import gargoyle.ct.pref.CTPreferences;
import gargoyle.ct.pref.impl.prop.CTPrefProperty;
import gargoyle.ct.sound.Audio;
import gargoyle.ct.sound.AudioClip;
import gargoyle.ct.ui.CTStateUpdatable;

public class CTSoundUpdatable extends CTStateUpdatable {
    private static final String LOC_SOUND = "/wav/cup.aiff";
    private final AudioClip clip;
    private final CTPrefProperty<Boolean> sound;

    public CTSoundUpdatable(CTPreferences preferences) {
        sound = preferences.sound();
        clip = Audio.load(Audio.class.getResource(LOC_SOUND));
    }

    @Override
    protected void onStateChange(State oldState, State newState) {
        if (oldState != null && sound.get()) {
            clip.play();
        }
    }
}
