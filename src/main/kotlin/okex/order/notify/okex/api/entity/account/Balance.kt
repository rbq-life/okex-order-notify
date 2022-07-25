package okex.order.notify.okex.api.entity.account

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BalanceReq(
    val ccy: String? = null
)

@Serializable
data class BalanceRes(
    @SerialName("adjEq")
    val adjEq: String? = null,
    @SerialName("details")
    val details: List<BalanceDetail>? = null,
    @SerialName("imr")
    val imr: String? = null,
    @SerialName("isoEq")
    val isoEq: String? = null,
    @SerialName("mgnRatio")
    val mgnRatio: String? = null,
    @SerialName("mmr")
    val mmr: String? = null,
    @SerialName("notionalUsd")
    val notionalUsd: String? = null,
    @SerialName("ordFroz")
    val ordFroz: String? = null,
    @SerialName("totalEq")
    val totalEq: String? = null,
    @SerialName("uTime")
    val uTime: String? = null
)

@Serializable
data class BalanceDetail(
    @SerialName("availBal")
    val availBal: String,
    @SerialName("availEq")
    val availEq: String,
    @SerialName("cashBal")
    val cashBal: String?,
    @SerialName("ccy")
    val ccy: String,
    @SerialName("coinUsdPrice")
    val coinUsdPrice: String? = null,
    @SerialName("crossLiab")
    val crossLiab: String? = null,
    @SerialName("disEq")
    val disEq: String? = null,
    @SerialName("eq")
    val eq: String? = null,
    @SerialName("eqUsd")
    val eqUsd: String? = null,
    @SerialName("frozenBal")
    val frozenBal: String? = null,
    @SerialName("interest")
    val interest: String? = null,
    @SerialName("isoEq")
    val isoEq: String? = null,
    @SerialName("isoLiab")
    val isoLiab: String? = null,
    @SerialName("isoUpl")
    val isoUpl: String? = null,
    @SerialName("liab")
    val liab: String? = null,
    @SerialName("maxLoan")
    val maxLoan: String? = null,
    @SerialName("mgnRatio")
    val mgnRatio: String? = null,
    @SerialName("notionalLever")
    val notionalLever: String? = null,
    @SerialName("ordFrozen")
    val ordFrozen: String? = null,
    @SerialName("stgyEq")
    val stgyEq: String? = null,
    @SerialName("twap")
    val twap: String? = null,
    @SerialName("uTime")
    val uTime: String? = null,
    @SerialName("upl")
    val upl: String? = null,
    @SerialName("uplLiab")
    val uplLiab: String? = null
)