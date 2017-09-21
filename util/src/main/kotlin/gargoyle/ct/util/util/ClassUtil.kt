package gargoyle.ct.util.util

import java.io.InputStream
import java.net.URL
import java.util.*
import kotlin.reflect.KClass

inline val KClass<*>.className: String
    get() = this.qualifiedName!!
inline val KClass<*>.simpleClassName: String
    get() = this.simpleName!!

inline fun KClass<*>.getResource(
    name: String,
    onError: () -> Nothing = { error("NOT FOUND $name as ${getResourceName(name)}") }
): URL =
    java.getResource(name) ?: java.classLoader.getResource(getResourceName(name)) ?: onError()

inline fun KClass<*>.getResourceAsStream(
    name: String,
    onError: () -> Nothing = { error("NOT FOUND $name as ${getResourceName(name)}") }
): InputStream =
    java.getResource(name)?.openStream() ?: java.classLoader.getResource(getResourceName(name))?.openStream()
    ?: onError()

fun KClass<*>.getResourceName(name: String): String =
    if (name.startsWith('/'))
        name.trimStart('/')
    else
        "${qualifiedName!!.replace('.', '/').substringBeforeLast('/')}/$name"

@Suppress("NOTHING_TO_INLINE")
inline fun KClass<*>.getResourceBundle(name: String, locale: Locale = Locale.getDefault()): ResourceBundle =
    ResourceBundle.getBundle(getResourceName(name), locale, classLoader)

inline val KClass<*>.classLoader: ClassLoader
    get() = this.java.classLoader
