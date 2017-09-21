package gargoyle.ct.util.resource.internal

import java.net.URL

/**
 * represents [Resource] from local or remote [URL]
 */
class URLResource(url: URL) : VirtualResource<URLResource>(url.toExternalForm())
