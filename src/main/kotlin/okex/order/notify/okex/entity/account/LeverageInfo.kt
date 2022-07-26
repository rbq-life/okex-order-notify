package okex.order.notify.okex.entity.account

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import okex.order.notify.okex.entity.MgnMode
import okex.order.notify.okex.entity.PosSide


@Serializable
data class LeverageInfoReq(
    val instId: String,
    val mgnMode: MgnMode
)

@Serializable
data class LeverageInfoRes(
    @SerialName("instId")
    val instId: String,
    @SerialName("lever")
    val lever: String,
    @SerialName("mgnMode")
    val mgnMode: MgnMode,
    @SerialName("posSide")
    val posSide: PosSide
)