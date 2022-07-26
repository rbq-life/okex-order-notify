package okex.order.notify.okex.api.entity.account

import okex.order.notify.okex.api.entity.PosMode

@kotlinx.serialization.Serializable
data class SetPositionModeReq(
    val posMode: PosMode
)


@kotlinx.serialization.Serializable
data class SetPositionModeRes(
    val posMode: PosMode
)