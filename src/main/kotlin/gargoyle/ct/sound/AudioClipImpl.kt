package gargoyle.ct.sound

import gargoyle.ct.log.Log
import gargoyle.ct.util.Defend
import java.io.IOException
import java.net.URL
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip
import javax.sound.sampled.LineEvent
import javax.sound.sampled.LineListener
import javax.sound.sampled.LineUnavailableException
import javax.sound.sampled.UnsupportedAudioFileException

class AudioClipImpl(url: URL) : AudioClip {
    var isPlaying = false
    private lateinit var clip: Clip
    var isReleased = false

    init {
        Defend.notNull(url, "null audio clip")
        try {
            val stream = AudioSystem.getAudioInputStream(url)
            clip = AudioSystem.getClip()
            clip.open(stream)
            clip.addLineListener(AudioClipListener())
            isReleased = true
        } catch (exc: IOException) {
            Log.error(exc.localizedMessage, exc)
            isReleased = false
        } catch (exc: UnsupportedAudioFileException) {
            Log.error(exc.localizedMessage, exc)
            isReleased = false
        } catch (exc: LineUnavailableException) {
            Log.error(exc.localizedMessage, exc)
            isReleased = false
        }
    }

    override fun loop() {
        play()
    }

    override fun play() {
        play(true)
    }

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
            if (event.type === LineEvent.Type.STOP) {
                isPlaying = false
            }
        }
    }
}
