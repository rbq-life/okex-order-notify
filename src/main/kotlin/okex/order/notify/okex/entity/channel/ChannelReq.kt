package okex.order.notify.okex.entity.channel

import kotlinx.serialization.Serializable
import okex.order.notify.okex.api.entity.InstType

@Serializable
data class ChannelReq(
    val channel: WsChannel,
    val instType: InstType? = null,
    val ccy: String? = null,
    val uly: String? = null,
    val instId: String? = null,
    val algoId: String? = null,
    val uid: String? = null,
)