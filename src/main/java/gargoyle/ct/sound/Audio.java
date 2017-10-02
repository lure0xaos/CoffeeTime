package gargoyle.ct.sound;

import java.net.URL;

public class Audio {
    public static AudioClip play(URL url) {
        AudioClip clip = load(url);
        clip.play();
        return clip;
    }

    public static AudioClip load(URL url) {
        return new AudioClipImpl(url);
    }
}
