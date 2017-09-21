package gargoyle.ct.util.sound

import gargoyle.ct.util.log.Log
import java.net.URL
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip
import javax.sound.sampled.LineEvent
import javax.sound.sampled.LineListener

class AudioClipImpl(url: URL) : AudioClip {
    var isPlaying: Boolean = false
    var isReleased: Boolean = false
    private lateinit var clip: Clip

    init {
        try {
            clip = AudioSystem.getClip()
            clip.open(AudioSystem.getAudioInputStream(url))
            clip.addLineListener(AudioClipListener())
            isReleased = true
        } catch (exc: Exception) {
            Log.error(exc.localizedMessage, exc)
            isReleased = false
        }
    }

    override fun loop(): Unit = play()

    override fun play(): Unit = play(true)

    fun play(breakOld: Boolean) {
        if (isReleased) {
            if (breakOld) {
                clip.stop()
                clip.framePosition = 0
                clip.start()
                isPlaying = true
            } else if (!isPlaying) {
                clip.framePosition = 0
                clip.start()
                isPlaying = true
            }
        }
    }

    override fun stop() {
        if (isPlaying) {
            clip.stop()
        }
    }

    private inner class AudioClipListener : LineListener {
        override fun update(event: LineEvent) {
            if (event.type === LineEvent.Type.STOP) isPlaying = false
        }
    }
}
