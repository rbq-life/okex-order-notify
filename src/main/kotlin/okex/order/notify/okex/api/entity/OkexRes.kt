package okex.order.notify.api.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
open class OkexRes<T>(
    val code: String,
    @SerialName("data")
    val `data`: T?,
    val msg: String
)