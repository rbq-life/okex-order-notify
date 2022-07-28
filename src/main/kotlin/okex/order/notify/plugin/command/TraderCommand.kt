package okex.order.notify.plugin.command

import kotlinx.serialization.ExperimentalSerializationApi
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.RawCommand
import net.mamoe.mirai.console.util.ConsoleExperimentalApi
import net.mamoe.mirai.message.data.*
import okex.order.notify.PluginMain
import okex.order.notify.okex.api.OkexApi
import okex.order.notify.plugin.Global
import okex.order.notify.plugin.command.OwnerCommand.add

import okex.order.notify.plugin.data.TradeNotifyConfig
import okex.order.notify.plugin.entity.Trader
import okex.order.notify.utils.JsonUtils.jsonToObjectOrNull
import okex.order.notify.utils.Utils.log


@ConsoleExperimentalApi
object TraderCommand : RawCommand(
    PluginMain,
    "trader",
    description = "add trader",
    usage = """trader add
trader add <json>
trader list
        """
) {
    private const val jsonEditorUrl = "https://json-editor.github.io/json-editor/?data=N4Ig9gDgLglmB2BnEAuUMDGCA2MBGqIAZglAIYDuApomALZUCsIANOHgFZUZQD62ZAJ5gArlELwwAJzplsrEIgwALKrNShYUbFUKBv20AFSoAA5QLLyAFSlkAJlSkKbSqTGhx4hQO/RgMcVA4JqBfTQAEAPIA0gCiABr+AIIACgCS/oAQ/4BXyoAA6YCBkcmA39GAwuaACmmAkP8KUIIQuijsXDwKUlQAjiIwtVaoANog8GQMCnV1CmQQMMFUgiAAumwQUpC2sDQaHV3lmqXlilBO8ADmxTDaa4CAAYAQeoCwKoAPniAAvmy9CyVlEiJ0eLa7+4THt9cgDE5QAEJgAAed1WhDIUksozYWh0hEA+UqAdv1AI3e9hoGCcLgQhEA98qAU7lDIAsTUAhuaAGH/koBfgMQGxg20A3j6AaPUrmwBkMRqCHhUwJxuOIYXs4RVWcNoSBag0mlQWih2sL2WxENxanyQBAyIhEBBlJZFQpEDA6CIBFApeNJtMylI5sg0CA5aNbfc1tTNjt+e8hYMRczFEqqOJHWCKi7aW6QLDnX7xN81RqtTrluGg+tXW9Bar1ZrterdN99YbjabA5yQHgwGAdGQ3O704B6M0AZCq1wCEVoB9o0AG25oohkI0BrvYRWXQeDhXKMAUXi2aZSG0gWkmyw8VzFVTdCplsBQF0DAAsV0uQA==="

    override val prefixOptional = true

    override suspend fun CommandSender.onCommand(args: MessageChain) {
        val master = Global.promiseUtils.isMaster(this.user)

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
                if(master) edit(content,true)
            }
            "list" -> {
                list()
            }
            "update" -> {
                if(master) edit(content,false)
            }
            "del" -> {
                if(master) del(content)
            }
        }


    }

    fun del(content: String) {
        val delList = TradeNotifyConfig.traderSet.filter { it.qq.toString() == content || it.name == content }
        
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

        log.info("$operation trader :  {}",json)
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