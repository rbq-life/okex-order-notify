package okex.order.notify.plugin

import okex.order.notify.plugin.data.TradeNotifyConfig
import okex.order.notify.utils.PromiseUtils

object Global {
    val promiseUtils = PromiseUtils(TradeNotifyConfig.owners)
}