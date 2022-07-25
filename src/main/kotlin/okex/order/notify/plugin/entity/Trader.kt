package okex.order.notify.plugin.entity

import kotlinx.serialization.Serializable
import okex.order.notify.okex.entity.ApiKeyInfo

@Serializable
data class Trader(
    val name:String,
    val qq:Long,
    val apiKey:ApiKeyInfo,
)
