package trade.wsure.top.websocket.entity.channel

import kotlinx.serialization.Serializable
import trade.wsure.top.api.entity.InstType

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