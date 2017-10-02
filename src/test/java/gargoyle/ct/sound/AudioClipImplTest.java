package gargoyle.ct.sound;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class AudioClipImplTest {
    private AudioClip clip;

    @BeforeEach
    public void setUp() {
        clip = Audio.load(Audio.class.getResource("/wav/cup.aiff"));
    }

    @Test
    public void testPlay() {
        assertNotNull(clip, "clip is null");
        clip.play();
        clip.stop();
        clip.play();
    }
}
