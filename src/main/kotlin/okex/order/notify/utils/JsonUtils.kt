package okex.order.notify.utils

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.decodeFromJsonElement
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberProperties

object JsonUtils {
    val logger: Logger = LoggerFactory.getLogger(javaClass)

    @OptIn(ExperimentalSerializationApi::class)
    val formatter = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        encodeDefaults = true
        explicitNulls = false
        allowStructuredMapKeys = true
    }

    inline fun <reified T : Any> T.objectToJson(): String {
        return formatter.encodeToString(this)
    }

    inline fun <reified T> String.jsonToObject(): T {
        return formatter.decodeFromString(this)
    }

    inline fun <reified T> String.jsonToObjectOrNull(failureReason: Boolean = true): T? {
        return kotlin.runCatching { formatter.decodeFromString<T>(this) }
            .onFailure {
                if (failureReason) {
                    logger.warn("format string to json failed !! string :{}", this, it)
                }
            }.getOrNull()
    }

    fun String.toJsonElement(): JsonElement {
        return formatter.parseToJsonElement(this)
    }

    inline fun <reified T> JsonElement.jsonToObject(): T {
        return formatter.decodeFromJsonElement(this)
    }

    inline fun <reified T> JsonElement.jsonToObjectOrNull(failureReason: Boolean = true): T? {
        return kotlin.runCatching { formatter.decodeFromJsonElement<T>(this) }
            .onFailure {
                if (failureReason) {
                    logger.warn("format string to json failed !! string :{}", this, it)
                }
            }.getOrNull()
    }

    inline fun <reified T : Any> T.toMap(): Map<String, String> {
        val props = T::class.memberProperties.associateBy { it.name }
        return props.keys.associateWith { props[it]?.get(this).toString() }
    }

    inline fun <reified T : Any> T.toSerialNameMap(): Map<String, *> {
        val props = T::class.memberProperties.associateBy { it.name }
        return props.map {
            val (k, v) = it
            val serialName = v.findAnnotation<SerialName>()?.value ?: k
            serialName to v.get(this)
        }.associate { it }
    }

    inline fun <reified T : Any> T.toSerialNameMapString(): Map<String, String> {
        val props = T::class.memberProperties.associateBy { it.name }
        return props.filter { it.value.get(this) != null }.map {
            val (k, v) = it
            val serialName = v.findAnnotation<SerialName>()?.value ?: k
            serialName to v.get(this).toString()
        }.associate { it }
    }
}