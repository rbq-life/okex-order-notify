package okex.order.notify.plugin.data

import net.mamoe.mirai.console.data.AutoSavePluginData
import net.mamoe.mirai.console.data.value
import okex.order.notify.okex.api.entity.account.BalanceRes
import okex.order.notify.okex.api.entity.account.PositionRes
import okex.order.notify.okex.api.entity.trade.OrdersEntity
import okex.order.notify.plugin.entity.Trader

object TradeOrderData:AutoSavePluginData("TradeOrderData") {
    var uidMapTrader : MutableMap<String,Trader> by value(mutableMapOf())
    var orderListMap : MutableMap<String,List<OrdersEntity>> by value(mutableMapOf())
    var positionsListMap : MutableMap<String,List<PositionRes>> by value(mutableMapOf())
    var balancesListMap : MutableMap<String, List<BalanceRes>> by value(mutableMapOf())
}