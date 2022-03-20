package gargoyle.ct.ui

import java.net.URL

interface CTIconProvider {
    val bigIcon: URL?
    val mediumIcon: URL?
    val smallIcon: URL?
}
