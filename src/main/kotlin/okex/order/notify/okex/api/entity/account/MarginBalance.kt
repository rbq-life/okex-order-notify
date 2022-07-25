package okex.order.notify.api.entity.account

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import okex.order.notify.api.entity.MarginBalanceType
import okex.order.notify.api.entity.PosSide


@Serializable
data class MarginBalanceReq(
    val instId: String,
    val posSide: PosSide,
    val type: MarginBalanceType,
    val amt: String,
    val ccy: String? = null,
    val auto: Boolean? = null,
    val loanTrans: Boolean? = null,
)

@Serializable
data class MarginBalanceRes(
    @SerialName("amt")
    val amt: String,
    @SerialName("ccy")
    val ccy: String,
    @SerialName("instId")
    val instId: String,
    @SerialName("leverage")
    val leverage: String,
    @SerialName("posSide")
    val posSide: String,
    @SerialName("type")
    val type: String
)