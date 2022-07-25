package okex.order.notify.api.entity.account

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import okex.order.notify.api.entity.InstType
import okex.order.notify.api.entity.MgnMode
import okex.order.notify.api.entity.PosSide


/*
instType	String	否	"""产品类型
MARGIN：币币杠杆
SWAP：永续合约
FUTURES：交割合约
OPTION：期权
instType和instId同时传入的时候会校验instId与instType是否一致，结果返回instId的持仓信息"""
instId	String	否	交易产品ID，如：BTC-USD-190927-5000-C
支持多个instId查询（不超过10个），半角逗号分隔
posId	String	否	持仓ID
支持多个posId查询（不超过20个），半角逗号分割
*/
@Serializable
data class PositionsReq(
    val instType: InstType? = null,
    val instId: String? = null,
    val posId: String? = null,
)

@Serializable
data class PositionRes(
    @SerialName("adl")
    val adl: String? = null,
    @SerialName("availPos")
    val availPos: String? = null,
    @SerialName("avgPx")
    val avgPx: String? = null,
    @SerialName("baseBal")
    val baseBal: String? = null,
    @SerialName("cTime")
    val cTime: String? = null,
    @SerialName("ccy")
    val ccy: String,
    @SerialName("deltaBS")
    val deltaBS: String? = null,
    @SerialName("deltaPA")
    val deltaPA: String? = null,
    @SerialName("gammaBS")
    val gammaBS: String? = null,
    @SerialName("gammaPA")
    val gammaPA: String? = null,
    @SerialName("imr")
    val imr: String? = null,
    @SerialName("instId")
    val instId: String,
    @SerialName("instType")
    val instType: InstType,
    @SerialName("interest")
    val interest: String? = null,
    @SerialName("last")
    val last: String? = null,
    @SerialName("lever")
    val lever: String? = null,
    @SerialName("liab")
    val liab: String? = null,
    @SerialName("liabCcy")
    val liabCcy: String? = null,
    @SerialName("liqPx")
    val liqPx: String? = null,
    @SerialName("margin")
    val margin: String? = null,
    @SerialName("markPx")
    val markPx: String? = null,
    @SerialName("mgnMode")
    val mgnMode: MgnMode,
    @SerialName("mgnRatio")
    val mgnRatio: String? = null,
    @SerialName("mmr")
    val mmr: String? = null,
    @SerialName("notionalUsd")
    val notionalUsd: String? = null,
    @SerialName("optVal")
    val optVal: String? = null,
    @SerialName("pTime")
    val pTime: String? = null,
    @SerialName("pos")
    val pos: String,
    @SerialName("posCcy")
    val posCcy: String,
    @SerialName("posId")
    val posId: String? = null,
    @SerialName("posSide")
    val posSide: PosSide,
    @SerialName("quoteBal")
    val quoteBal: String? = null,
    @SerialName("thetaBS")
    val thetaBS: String? = null,
    @SerialName("thetaPA")
    val thetaPA: String? = null,
    @SerialName("tradeId")
    val tradeId: String? = null,
    @SerialName("uTime")
    val uTime: String? = null,
    @SerialName("upl")
    val upl: String? = null,
    @SerialName("uplRatio")
    val uplRatio: String? = null,
    @SerialName("usdPx")
    val usdPx: String? = null,
    @SerialName("vegaBS")
    val vegaBS: String? = null,
    @SerialName("vegaPA")
    val vegaPA: String? = null
)

