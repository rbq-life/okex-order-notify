package okex.order.notify.okex.entity.account

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import okex.order.notify.okex.entity.AcctLv
import okex.order.notify.okex.entity.GreeksType
import okex.order.notify.okex.entity.IsoMode
import okex.order.notify.okex.entity.PosMode


@Serializable
data class ConfigRes(
    @SerialName("acctLv")
    @Serializable(with = AcctLv.AcctLvSerializer::class)
    val acctLv: AcctLv,
    @SerialName("autoLoan")
    val autoLoan: Boolean,
    @SerialName("ctIsoMode")
    val ctIsoMode: IsoMode,
    @SerialName("greeksType")
    val greeksType: GreeksType,
    @SerialName("level")
    val level: String,
    @SerialName("levelTmp")
    val levelTmp: String,
    @SerialName("liquidationGear")
    val liquidationGear: String,
    @SerialName("mgnIsoMode")
    val mgnIsoMode: IsoMode,
    @SerialName("posMode")
    val posMode: PosMode,
    @SerialName("uid")
    val uid: String
)