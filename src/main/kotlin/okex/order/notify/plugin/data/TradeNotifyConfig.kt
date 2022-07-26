package okex.order.notify.plugin.data

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.value
import okex.order.notify.plugin.entity.Trader

object TradeNotifyConfig :AutoSavePluginConfig("TradeNotifyConfig"){
    var owners : MutableSet<Long> by value(mutableSetOf())
    var traderSet : MutableSet<Trader> by value(mutableSetOf())
    var groupMapTrader: MutableMap<Long,MutableSet<Trader>> by value(mutableMapOf())
}