package okex.order.notify.okex.entity.login

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import okex.order.notify.okex.entity.ApiKeyInfo
import okex.order.notify.okex.entity.OkexWSEventEnum
import okex.order.notify.utils.Utils


@Serializable
data class WsLoginReq(
    @SerialName("apiKey")
    val apiKey: String,
    @SerialName("passphrase")
    val passphrase: String? = null,
    @SerialName("sign")
    val sign: String? = null,
    @SerialName("timestamp")
    val timestamp: String? = null
) {
    constructor(apiKeyInfo: ApiKeyInfo, timestamp: String = (System.currentTimeMillis() / 1000).toString()) : this(
        apiKeyInfo.apiKey, apiKeyInfo.passphrase,
        Utils.createSignature(timestamp + "GET" + "/users/self/verify", apiKeyInfo.secret), timestamp
    )
}

@Serializable
data class WsLoginRes(
    val event: OkexWSEventEnum,
    val code: String,
    val msg: String,
    val `data`: List<WsLoginReq>? = null,
)