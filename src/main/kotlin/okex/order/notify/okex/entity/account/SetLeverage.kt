package okex.order.notify.okex.entity.account

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import okex.order.notify.okex.entity.MgnMode
import okex.order.notify.okex.entity.PosSide

@Serializable
data class SetLeverageReq(
    val instId: String? = null,
    val lever: String,
    val mgnMode: MgnMode,
    val posSide: PosSide? = null,
    val ccy: String? = null,
)

@Serializable
data class SetLeverageRes(
    @SerialName("instId")
    val instId: String? = null,
    @SerialName("lever")
    val lever: String? = null,
    @SerialName("mgnMode")
    val mgnMode: String? = null,
    @SerialName("posSide")
    val posSide: String? = null
)