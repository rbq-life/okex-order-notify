package okex.order.notify.api.entity.trade

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import okex.order.notify.api.entity.*


@Serializable
data class OrderReq(
    val instId: String,
    val tdMode: TdMode,
    val side: TradeSide,
    val ordType: OrdType,
    val sz: String,
    val px: String? = null,
    val posSide: PosSide? = null,
    val ccy: String? = null,
    val tag: String? = null,
    val clOrdId: String? = null,
    val reduceOnly: Boolean? = null,
    val tgtCcy: String? = null,
    val banAmend: Boolean? = null,
)

@Serializable
data class AmendOrderReq(
    val instId: String,
    val ordId: String? = null,
    val newSz: Long? = null,
    val newPx: Long? = null,
    val cxlOnFail: Boolean? = null,
    val clOrdId: String? = null,
    val reqId: String? = null,

    )

@Serializable
data class GetOrCancelOrderReq(
    val instId: String,
    val ordId: String? = null,
    val clOrdId: String? = null,
)

@Serializable
data class OrderRes(
    @SerialName("clOrdId")
    val clOrdId: String,
    @SerialName("ordId")
    val ordId: String,
    @SerialName("sCode")
    val sCode: String,
    @SerialName("sMsg")
    val sMsg: String,
    @SerialName("tag")
    val tag: String? = null
)

@Serializable
data class OrdersQueryReq(
    val instType: InstType? = null,
    val uly: String? = null,
    val instId: String? = null,
    val ordType: OrdType? = null,
    val state: OrderState? = null,
    val category: Category? = null,
    val after: String? = null,
    val before: String? = null,
    val limit: String? = null,
)


@Serializable
data class OrdersEntity(
    @SerialName("accFillSz")
    val accFillSz: String,
    @SerialName("avgPx")
    val avgPx: String,
    @SerialName("cTime")
    val cTime: String,
    @SerialName("category")
    val category: String,
    @SerialName("ccy")
    val ccy: String,
    @SerialName("clOrdId")
    val clOrdId: String,
    @SerialName("fee")
    val fee: String,
    @SerialName("feeCcy")
    val feeCcy: String,
    @SerialName("fillPx")
    val fillPx: String,
    @SerialName("fillSz")
    val fillSz: String,
    @SerialName("fillTime")
    val fillTime: String,
    @SerialName("instId")
    val instId: String,
    @SerialName("instType")
    val instType: InstType,
    @SerialName("lever")
    val lever: String,
    @SerialName("ordId")
    val ordId: String,
    @SerialName("ordType")
    val ordType: String,
    @SerialName("pnl")
    val pnl: String,
    @SerialName("posSide")
    val posSide: PosSide,
    @SerialName("px")
    val px: String,
    @SerialName("rebate")
    val rebate: String,
    @SerialName("rebateCcy")
    val rebateCcy: String,
    @SerialName("side")
    val side: String,
    @SerialName("slOrdPx")
    val slOrdPx: String,
    @SerialName("slTriggerPx")
    val slTriggerPx: String,
    @SerialName("slTriggerPxType")
    val slTriggerPxType: String,
    @SerialName("source")
    val source: String,
    @SerialName("state")
    val state: String,
    @SerialName("sz")
    val sz: String,
    @SerialName("tag")
    val tag: String,
    @SerialName("tdMode")
    val tdMode: String,
    @SerialName("tgtCcy")
    val tgtCcy: String,
    @SerialName("tpOrdPx")
    val tpOrdPx: String,
    @SerialName("tpTriggerPx")
    val tpTriggerPx: String,
    @SerialName("tpTriggerPxType")
    val tpTriggerPxType: String,
    @SerialName("tradeId")
    val tradeId: String,
    @SerialName("uTime")
    val uTime: String,
    @SerialName("amendResult")
    val amendResult: String? = null,
    @SerialName("code")
    val code: String? = null,
    @SerialName("execType")
    val execType: String? = null,
    @SerialName("fillFee")
    val fillFee: String? = null,
    @SerialName("fillFeeCcy")
    val fillFeeCcy: String? = null,
    @SerialName("fillNotionalUsd")
    val fillNotionalUsd: String? = null,
    @SerialName("msg")
    val msg: String? = null,
    @SerialName("notionalUsd")
    val notionalUsd: String? = null,
    @SerialName("reduceOnly")
    val reduceOnly: String? = null,
    @SerialName("reqId")
    val reqId: String? = null,
)

@Serializable
data class FillsRes(
    @SerialName("billId")
    val billId: String,
    @SerialName("clOrdId")
    val clOrdId: String,
    @SerialName("execType")
    val execType: String,
    @SerialName("fee")
    val fee: String,
    @SerialName("feeCcy")
    val feeCcy: String,
    @SerialName("fillPx")
    val fillPx: String,
    @SerialName("fillSz")
    val fillSz: String,
    @SerialName("instId")
    val instId: String,
    @SerialName("instType")
    val instType: String,
    @SerialName("ordId")
    val ordId: String,
    @SerialName("posSide")
    val posSide: String,
    @SerialName("side")
    val side: String,
    @SerialName("tag")
    val tag: String,
    @SerialName("tradeId")
    val tradeId: String,
    @SerialName("ts")
    val ts: String
)

@Serializable
data class ClosePositionReq(
    val instId: String,
    val mgnMode: MgnMode,
    val posSide: PosSide? = null,
    val ccy: String? = null,
    val autoCxl: Boolean? = null,
)

@Serializable
data class ClosePositionRes(
    val instId: String,
    val posSide: PosSide,
)