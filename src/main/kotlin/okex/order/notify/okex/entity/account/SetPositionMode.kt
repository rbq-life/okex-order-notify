package okex.order.notify.okex.entity.account

import okex.order.notify.okex.entity.PosMode

@kotlinx.serialization.Serializable
data class SetPositionModeReq(
    val posMode: PosMode
)


@kotlinx.serialization.Serializable
data class SetPositionModeRes(
    val posMode: PosMode
)