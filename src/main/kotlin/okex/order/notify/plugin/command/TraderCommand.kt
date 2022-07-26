package okex.order.notify.plugin.command

import kotlinx.serialization.ExperimentalSerializationApi
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.RawCommand
import net.mamoe.mirai.console.util.ConsoleExperimentalApi
import net.mamoe.mirai.message.data.*
import net.mamoe.mirai.message.data.MessageChain.Companion.serializeToJsonString
import net.mamoe.mirai.message.data.MessageChain.Companion.serializeToString
import okex.order.notify.PluginMain
import okex.order.notify.okex.api.OkexApi

import okex.order.notify.plugin.data.TradeNotifyConfig
import okex.order.notify.plugin.entity.Trader
import okex.order.notify.utils.JsonUtils.jsonToObjectOrNull
import okex.order.notify.utils.Utils.log

val jsonEditorUrl = "https://json-editor.github.io/json-editor/?data=N4Ig9gDgLglmB2BnEAuUMDGCA2MBGqIAZglAIYDuApomALZUCsIANOHgFZUZQD62ZAJ5gArlELwwAJzplsrEIgwALKrNShYUbFUKBv20AFSoAA5QLLyAFSlkAJlSkKbSqTGhx4hQO/RgMcVA4JqBfTQAEAPIA0gCiABr+AIIACgCS/oAQ/4BXyoAA6YCBkcmA39GAwuaACmmAkP8KUIIQuijsXDwKUlQAjiIwtVaoANog8GQMCnV1CmQQMMFUgiAAumwQUpC2sDQaHV3lmqXlilBO8ADmxTDaa4CAAYAQeoCwKoAPniAAvmy9CyVlEiJ0eLa7+4THt9cgDE5QAEJgAAed1WhDIUksozYWh0hEA+UqAdv1AI3e9hoGCcLgQhEA98qAU7lDIAsTUAhuaAGH/koBfgMQGxg20A3j6AaPUrmwBkMRqCHhUwJxuOIYXs4RVWcNoSBag0mlQWih2sL2WxENxanyQBAyIhEBBlJZFQpEDA6CIBFApeNJtMylI5sg0CA5aNbfc1tTNjt+e8hYMRczFEqqOJHWCKi7aW6QLDnX7xN81RqtTrluGg+tXW9Bar1ZrterdN99YbjabA5yQHgwGAdGQ3O704B6M0AZCq1wCEVoB9o0AG25oohkI0BrvYRWXQeDhXKMAUXi2aZSG0gWkmyw8VzFVTdCplsBQF0DAAsV0uQA==="
/*
object TraderCommand : CompositeCommand(
    PluginMain,
    "trader",
    description = "带单老师",
) {
    @ExperimentalCommandDescriptors
    @ConsoleExperimentalApi
    override val prefixOptional = true

    private val promiseUtils = PromiseUtils(TradeNotifyConfig.owners)


    @SubCommand("list")
    @Description("trader list")
    suspend fun CommandSender.list() {
        val messages = TradeNotifyConfig.traderSet.map { it.toMessage() }
        if(messages.isNotEmpty()){
            if(this.bot != null) {
                sendMessage(buildForwardMessage(this.subject!!){
                    messages.onEach {
                        add(this@list.bot!!,it)
                    }
                })
            }else{
                sendMessage(messageChainOf(*messages.toTypedArray()))
            }
        } else {
            sendMessage("trader list is empty !")
        }

    }
//
//
//    @SubCommand("add")
//    @Description("add trader ")
//    suspend fun CommandSender.add() {
//        sendMessage("$jsonEditorUrl \n请填写表格并复制表格右侧json，发送`trade add <json>`")
//    }


}
*/
@ConsoleExperimentalApi
object AddTrader : RawCommand(
    PluginMain,
    "trader",
    description = "add trader",
    usage = """trader add
trader add <json>
trader list
        """
) {

    override val prefixOptional = true


    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun CommandSender.onCommand(args: MessageChain) {
        val content = args.drop(1).joinToString ("") {
            if(it.contentToString().contains("\n")){
                "\"${it.contentToString().replace("\n","")}\""
            } else {
                it.contentToString()
            }
        }
        val first = args.firstOrNull()
        when(first?.content){
             null -> {

             }
            "add" -> {
                edit(content,true)
            }
            "list" -> {
                list()
            }
            "update" -> {
                edit(content,false)
            }
        }


    }

    suspend fun CommandSender.list(){
        val messages = TradeNotifyConfig.traderSet.map { it.toMessage() }
        if(messages.isNotEmpty()){
            if(this.bot != null) {
                sendMessage(buildForwardMessage(this.subject!!){
                    messages.onEach {
                        add(this@list.bot!!,it)
                    }
                })
            }else{
                sendMessage(messageChainOf(*messages.toTypedArray()))
            }
        } else {
            sendMessage("trader list is empty !")
        }
    }

    suspend fun CommandSender.edit( json:String,add:Boolean){
        val operation = if(add) "add" else "update"
//        val json = args.serializeToMiraiCode()
//            .replace(Regex("\\s/g"),"")

        log.info("edit trader :{}",json)
        if(json.isBlank()){
            sendMessage("$jsonEditorUrl \n请填写表格并复制表格右侧json，发送`trade $operation <json>`")
        } else {
            json.jsonToObjectOrNull<Trader>().also { trader ->
                if(trader == null) sendMessage("粘贴的信息无法识别")
                else {
                    kotlin.runCatching { OkexApi.Account.config(trader.apiKey) }
                        .onFailure { e -> sendMessage(e.message?:"load apiKey error !") }
                        .onSuccess {
                            if(!add){
                                TradeNotifyConfig.traderSet.removeIf { it.qq == trader.qq || it.name == trader.name }
                            }
                            TradeNotifyConfig.traderSet.add(trader)
                            sendMessage("$operation trader success")
                        }
                }
            }
        }
    }

}