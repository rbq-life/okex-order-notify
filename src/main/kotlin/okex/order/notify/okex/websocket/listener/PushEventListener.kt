package okex.order.notify.okex.websocket.listener

import okex.order.notify.okex.entity.account.BalanceRes
import okex.order.notify.okex.entity.account.PositionRes
import okex.order.notify.okex.entity.trade.OrdersEntity
import okex.order.notify.okex.entity.push.BalanceAndPositionRes

abstract class PushEventListener {
    open fun onAccount(uid: String, balance: List<BalanceRes>) {

    }

    open fun onPositions(uid: String, positions: List<PositionRes>) {

    }

    open fun onBalanceAndPosition(uid: String, balanceAndPosition: List<BalanceAndPositionRes>) {

    }

    open fun onOrders(uid: String, ordersEntities: List<OrdersEntity>) {

    }
}