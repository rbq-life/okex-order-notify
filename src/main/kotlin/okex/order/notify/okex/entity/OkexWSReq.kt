package okex.order.notify.okex.entity

import kotlinx.serialization.Serializable

@Serializable
data class OkexWSReq<T>(
    val op: OkexWSOPEnum,
    val args: List<T>,
)
