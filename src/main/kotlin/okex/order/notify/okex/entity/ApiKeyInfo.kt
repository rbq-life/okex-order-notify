package okex.order.notify.okex.entity

import kotlinx.serialization.Serializable

@Serializable
data class ApiKeyInfo(
    var apiKey: String,
    var secret: String,
    var passphrase: String,
    var simulated: Boolean = true,
    var uid: String? = null,
)
