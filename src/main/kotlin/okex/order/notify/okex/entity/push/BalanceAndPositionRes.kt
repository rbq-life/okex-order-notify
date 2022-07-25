package okex.order.notify.okex.entity.push

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class BalanceAndPositionRes(
    @SerialName("balData")
    val balData: List<BalData>,
    @SerialName("eventType")
    val eventType: String,
    @SerialName("pTime")
    val pTime: String,
    @SerialName("posData")
    val posData: List<PosData>
)

@Serializable
data class BalData(
    @SerialName("cashBal")
    val cashBal: String,
    @SerialName("ccy")
    val ccy: String,
    @SerialName("uTime")
    val uTime: String
)

@Serializable
data class PosData(
    @SerialName("avgPx")
    val avgPx: String,
    @SerialName("baseBal")
    val baseBal: String,
    @SerialName("ccy")
    val ccy: String,
    @SerialName("instId")
    val instId: String,
    @SerialName("instType")
    val instType: String,
    @SerialName("mgnMode")
    val mgnMode: String,
    @SerialName("pos")
    val pos: String,
    @SerialName("posCcy")
    val posCcy: String,
    @SerialName("posId")
    val posId: String,
    @SerialName("posSide")
    val posSide: String,
    @SerialName("quoteBal")
    val quoteBal: String,
    @SerialName("tradeId")
    val tradeId: String,
    @SerialName("uTime")
    val uTime: String
)