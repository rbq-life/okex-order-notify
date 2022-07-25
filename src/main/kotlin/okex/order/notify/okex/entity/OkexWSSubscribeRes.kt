package trade.wsure.top.websocket.entity

import kotlinx.serialization.Serializable
import trade.wsure.top.websocket.entity.channel.ChannelReq

@Serializable
data class OkexWSChannelType(
    val arg: ChannelReq
)

@Serializable
data class OkexWSEventType(
    val event: OkexWSEventEnum
)

@Serializable
data class OkexWSPushRes<V>(
    val arg: ChannelReq,
    val `data`: V? = null,
)

@Serializable
data class OkexWSSubscribeRes(
    val event: OkexWSEventEnum,
    val code: String,
    val msg: String
)