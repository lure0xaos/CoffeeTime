package gargoyle.ct.util.messages

fun interface MessageProvider {
    fun getMessage(message: String, vararg args: Any): String
}
