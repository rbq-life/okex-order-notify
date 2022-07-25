package okex.order.notify.plugin.listener

import okex.order.notify.okex.api.entity.account.BalanceRes
import okex.order.notify.okex.api.entity.account.PositionRes
import okex.order.notify.okex.api.entity.trade.OrdersEntity
import okex.order.notify.okex.websocket.listener.PushEventListener
import okex.order.notify.plugin.data.TradeOrderData

object OkexMemoryCacheEventListener : PushEventListener() {

    override fun onAccount(uid: String, balance: List<BalanceRes>) {
        searchApiKeyByUid(uid)?.let {
            TradeOrderData.balancesListMap.put(it, balance)
        }
    }

    override fun onOrders(uid: String, ordersEntities: List<OrdersEntity>) {
        searchApiKeyByUid(uid)?.let {
            TradeOrderData.orderListMap.put(it, ordersEntities)
        }
    }

    override fun onPositions(uid: String, positions: List<PositionRes>) {
        searchApiKeyByUid(uid)?.let {
            TradeOrderData.positionsListMap.put(it, positions)
        }
    }

    fun searchApiKeyByUid(uid: String):String?{
        return TradeOrderData.uidMapTrader[uid]?.apiKey?.apiKey
    }
}