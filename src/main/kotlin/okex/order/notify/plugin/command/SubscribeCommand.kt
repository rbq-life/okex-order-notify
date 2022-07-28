package okex.order.notify.plugin.command

import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.CompositeCommand
import net.mamoe.mirai.console.command.descriptor.ExperimentalCommandDescriptors
import net.mamoe.mirai.console.util.ConsoleExperimentalApi
import net.mamoe.mirai.contact.Group
import net.mamoe.mirai.message.data.PlainText
import net.mamoe.mirai.message.data.buildForwardMessage
import okex.order.notify.PluginMain
import okex.order.notify.plugin.Global
import okex.order.notify.plugin.data.TradeNotifyConfig

object SubscribeCommand : CompositeCommand(
    PluginMain,
    "subscribe",
    "sub","订阅",
    description = "订阅",
){
    @ExperimentalCommandDescriptors
    @ConsoleExperimentalApi
    override val prefixOptional = true

    @SubCommand("list")
    @Description("subscription list")
    suspend fun CommandSender.list() {
        if(this.subject != null && this.subject is Group){
            val list = TradeNotifyConfig.groupMapTrader[(this.subject as Group).id]
            if(list.isNullOrEmpty()){
                sendMessage("subscription list is empty !")
            } else {
                val messages = list.map { it.toMessage() }
                sendMessage(buildForwardMessage(this.subject!!){
                    messages.onEach {
                        add(this@list.bot!!,it)
                    }
                })
            }
        }
    }

    @SubCommand("del")
    @Description("del subscribed trader")
    suspend fun CommandSender.del(trader: String) {
        if(Global.promiseUtils.isMaster(this.user)
            && this.subject != null
            && this.subject is Group
        ) {
            val set = TradeNotifyConfig.groupMapTrader[(this.subject as Group).id]
            if(set.isNullOrEmpty()) {
                sendMessage("subscription list is empty !")
                return
            }
            val newTraders = TradeNotifyConfig.traderSet.filter { it.qq.toString() == trader || it.name == trader }
            if(newTraders.isEmpty()){
                sendMessage("The entered trader is Invalid !")
                return
            }
            set.removeIf{ newTraders.contains(it) }
            TradeNotifyConfig.groupMapTrader[(this.subject as Group).id] = set
            val messages = newTraders.map { it.toMessage() }
            sendMessage(buildForwardMessage(this.subject!!){
                add(this@del.bot!!,PlainText("delete these trader from current group :"))
                messages.onEach {
                    add(this@del.bot!!,it)
                }
            })
        }
    }


    @SubCommand("add")
    @Description("subscribe trader")
    suspend fun CommandSender.add(trader: String) {
        if(Global.promiseUtils.isMaster(this.user)
            && this.subject != null
            && this.subject is Group
        ) {
            val set = TradeNotifyConfig.groupMapTrader[(this.subject as Group).id] ?: mutableSetOf()
            val newTraders = TradeNotifyConfig.traderSet.filter { it.qq.toString() == trader || it.name == trader }
            if(newTraders.isEmpty()){
                sendMessage("The entered trader is Invalid !")
                return
            }
            set.addAll(newTraders)
            TradeNotifyConfig.groupMapTrader[(this.subject as Group).id] = set
            val messages = newTraders.map { it.toMessage() }
            sendMessage(buildForwardMessage(this.subject!!){
                add(this@add.bot!!,PlainText("subscribe these trader for current group :"))
                messages.onEach {
                    add(this@add.bot!!,it)
                }
            })
        }
    }

}