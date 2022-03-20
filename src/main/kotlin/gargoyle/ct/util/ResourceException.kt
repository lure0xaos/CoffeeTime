package gargoyle.ct.util

import java.io.Serial

class ResourceException : RuntimeException {
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)

    companion object {
        @Serial
        private val serialVersionUID = -695314450946813395L
    }
}
