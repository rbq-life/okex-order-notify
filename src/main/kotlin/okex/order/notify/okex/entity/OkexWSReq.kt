package trade.wsure.top.websocket.entity

import kotlinx.serialization.Serializable

@Serializable
data class OkexWSReq<T>(
    val op: OkexWSOPEnum,
    val args: List<T>,
)
