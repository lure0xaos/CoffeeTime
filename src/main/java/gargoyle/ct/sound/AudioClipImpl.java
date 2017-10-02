package gargoyle.ct.sound;

import gargoyle.ct.log.Log;
import gargoyle.ct.util.Defend;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URL;

public class AudioClipImpl implements AudioClip {
    boolean playing;
    private Clip clip;
    private boolean released;

    public AudioClipImpl(URL url) {
        Defend.notNull(url, "null audio clip");
        try {
            AudioInputStream stream = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(stream);
            clip.addLineListener(new AudioClipListener());
            released = true;
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException exc) {
            Log.error(exc.getLocalizedMessage(), exc);
            released = false;
        }
    }

    public boolean isReleased() {
        return released;
    }

    @Override
    public void loop() {
        play();
    }

    @Override
    public void play() {
        play(true);
    }

    public void play(boolean breakOld) {
        if (released) {
            if (breakOld) {
                clip.stop();
                clip.setFramePosition(0);
                clip.start();
                playing = true;
            } else if (!isPlaying()) {
                clip.setFramePosition(0);
                clip.start();
                playing = true;
            }
        }
    }

    public boolean isPlaying() {
        return playing;
    }

    @Override
    public void stop() {
        if (playing) {
            clip.stop();
        }
    }

    private class AudioClipListener implements LineListener {
        @Override
        public void update(LineEvent event) {
            if (event.getType() == LineEvent.Type.STOP) {
                playing = false;
            }
        }
    }
}

