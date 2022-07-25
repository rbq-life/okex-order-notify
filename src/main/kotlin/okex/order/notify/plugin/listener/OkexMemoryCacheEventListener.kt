package okex.order.notify.okex.websocket.listener

import okex.order.notify.okex.api.entity.account.BalanceRes
import okex.order.notify.okex.api.entity.account.PositionRes
import okex.order.notify.okex.api.entity.trade.OrdersEntity

object OkexMemoryCacheEventListener : PushEventListener() {

    override fun onAccount(uid: String, balance: List<BalanceRes>) {
        searchApiKeyByUid(uid)?.let {
            BALANCE_CACHE.put(it, balance)
        }
    }

    override fun onOrders(uid: String, ordersEntities: List<OrdersEntity>) {
        searchApiKeyByUid(uid)?.let {
            ORDERS_CACHE.put(it, ordersEntities)
        }
    }

    override fun onPositions(uid: String, positions: List<PositionRes>) {
        searchApiKeyByUid(uid)?.let {
            POSITION_CACHE.put(it, positions)
        }
    }

    fun searchApiKeyByUid(uid: String):String?{
        return API_KEY_CACHE.get(uid)?.apiKey
    }
}