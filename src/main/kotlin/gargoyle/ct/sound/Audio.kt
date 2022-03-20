package gargoyle.ct.sound

import java.net.URL

object Audio {
    fun play(url: URL): AudioClip {
        val clip = load(url)
        clip.play()
        return clip
    }

    @JvmStatic
    fun load(url: URL): AudioClip {
        return AudioClipImpl(url)
    }
}
