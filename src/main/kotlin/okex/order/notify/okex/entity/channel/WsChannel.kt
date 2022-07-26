package okex.order.notify.okex.entity.channel

import okex.order.notify.okex.entity.InstType


enum class WsChannel(val desc: String) {
    account("账户"),
    positions("持仓"),
    balance_and_position("账户余额和持仓频道"),
    orders("订单频道"),
    `orders-algo`("策略委托订单频道"),
    `algo-advance`("高级策略委托订单频道"),
    `liquidation-warning`("爆仓风险预警推送频道"),
    `account-greeks`("账户greeks频道"),
    rfqs("询价频道"),
    quotes("报价频道"),
    ;

    companion object {
        fun getAccountReq(ccy: String? = null): ChannelReq {
            return ChannelReq(account, ccy = ccy)
        }

        fun getPositionsReq(instType: InstType, uly: String? = null, instId: String? = null): ChannelReq {
            return ChannelReq(positions, instType, uly = uly, instId = instId)
        }

        fun getBalanceAndPositionReq(): ChannelReq {
            return ChannelReq(balance_and_position)
        }

        fun getOrdersReq(instType: InstType, uly: String? = null, instId: String? = null): ChannelReq {
            return ChannelReq(orders, instType, uly = uly, instId = instId)
        }

    }
}