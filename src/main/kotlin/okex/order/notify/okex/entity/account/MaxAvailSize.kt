package okex.order.notify.okex.api.entity.account

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import okex.order.notify.okex.api.entity.TdMode


@Serializable
data class MaxAvailSizeReq(
    val instId: String,
    val tdMode: TdMode,
    val ccy: String? = null,
    val reduceOnly: Boolean? = null,
    val px: String? = null,
)

@Serializable
data class MaxAvailSizeRes(
    @SerialName("availBuy")
    val availBuy: String,
    @SerialName("availSell")
    val availSell: String,
    @SerialName("instId")
    val instId: String
)