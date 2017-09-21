package gargoyle.ct.util.sound

import java.net.URL

object Audio {
    fun play(url: URL): AudioClip = load(url).also { it.play() }

    fun load(url: URL): AudioClip = AudioClipImpl(url)
}
