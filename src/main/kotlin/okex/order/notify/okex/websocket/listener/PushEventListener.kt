package trade.wsure.top.websocket.listener

import trade.wsure.top.api.entity.account.BalanceRes
import trade.wsure.top.api.entity.account.PositionRes
import trade.wsure.top.api.entity.trade.OrdersEntity
import trade.wsure.top.websocket.entity.push.BalanceAndPositionRes

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