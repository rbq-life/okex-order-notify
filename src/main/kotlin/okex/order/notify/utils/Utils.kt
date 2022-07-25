package okex.order.notify.utils

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

object Utils {
    val TIMESTAMP_FORMAT: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    val DATE_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    fun createSignature(data: String, key: String): String {
        val sha256Hmac = Mac.getInstance("HmacSHA256")
        val secretKey = SecretKeySpec(key.toByteArray(), "HmacSHA256")
        sha256Hmac.init(secretKey)
        // For base64
        return Base64.getEncoder().encodeToString(sha256Hmac.doFinal(data.toByteArray()))
    }

    fun getTimestamp(): String {
        val currentDateTime: ZonedDateTime = ZonedDateTime.now()
        val zdt: ZonedDateTime = currentDateTime.withZoneSameInstant(ZoneOffset.UTC) //you might use a different zone
        return zdt.format(TIMESTAMP_FORMAT)
    }

    fun String.toLocalDateTime(): LocalDateTime {
        return LocalDateTime.parse(this, DATE_FORMATTER)
    }

    fun LocalDateTime.toFormatString(): String {
        return this.format(DATE_FORMATTER)
    }

    val <reified T> T.log: Logger
        inline get() = LoggerFactory.getLogger(T::class.java)

    object LocalDateTimeSerializer : KSerializer<LocalDateTime> {
        override fun deserialize(decoder: Decoder): LocalDateTime {
            return kotlin.runCatching { decoder.decodeString().toLocalDateTime() }.getOrElse { LocalDateTime.now() }
        }

        override val descriptor: SerialDescriptor =
            PrimitiveSerialDescriptor("LocalDateTimeFormatString", PrimitiveKind.STRING)

        override fun serialize(encoder: Encoder, value: LocalDateTime) {
            encoder.encodeString(value.toFormatString())
        }

    }

    fun LocalDateTime.toEpochMilli(): Long {
        return this.toInstant(OffsetDateTime.now().offset).toEpochMilli()
    }

    fun LocalDateTime.toEpochSecond(): Long {
        return this.toEpochSecond(OffsetDateTime.now().offset)
    }

    fun Long.toLocalDateTime(): LocalDateTime {
        val instant = if (this.toString().length > 10) Instant.ofEpochMilli(this)
        else Instant.ofEpochSecond(this)
        return LocalDateTime.ofInstant(
            instant,
            TimeZone.getDefault().toZoneId()
        )
    }

    inline fun <reified T : Enum<T>> valueOf(type: String, default: T): T {
        return try {
            java.lang.Enum.valueOf(T::class.java, type)
        } catch (e: Exception) {
            default
        }
    }
}
