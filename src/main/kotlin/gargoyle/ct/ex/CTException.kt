package gargoyle.ct.ex

import java.io.Serial

class CTException : RuntimeException {
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)

    companion object {
        @Serial
        private val serialVersionUID = -4669219915320326863L
    }
}
