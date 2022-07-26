package okex.order.notify.okex.api.entity.account

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import okex.order.notify.okex.api.entity.TdMode


@Serializable
data class MaxSizeReq(
    val instId: String,
    val tdMode: TdMode,
    val ccy: String? = null,
    val px: String? = null,
    val leverage: String? = null,
)

@Serializable
data class MaxSizeRes(
    @SerialName("ccy")
    val ccy: String,
    @SerialName("instId")
    val instId: String,
    @SerialName("maxBuy")
    val maxBuy: String,
    @SerialName("maxSell")
    val maxSell: String
)