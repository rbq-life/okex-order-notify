package okex.order.notify.api.entity.account

import okex.order.notify.api.entity.PosMode

@kotlinx.serialization.Serializable
data class SetPositionModeReq(
    val posMode: PosMode
)


@kotlinx.serialization.Serializable
data class SetPositionModeRes(
    val posMode: PosMode
)