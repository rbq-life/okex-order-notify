package okex.order.notify.plugin.command

import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.CompositeCommand
import net.mamoe.mirai.console.command.descriptor.ExperimentalCommandDescriptors
import net.mamoe.mirai.console.util.ConsoleExperimentalApi
import net.mamoe.mirai.contact.User
import net.mamoe.mirai.message.data.PlainText
import okex.order.notify.PluginMain
import okex.order.notify.plugin.Global
import okex.order.notify.plugin.data.TradeNotifyConfig.owners

object OwnerCommand : CompositeCommand(
    PluginMain,
    "owner",
    description = "管理",
){
    @ExperimentalCommandDescriptors
    @ConsoleExperimentalApi
    override val prefixOptional = true

    @SubCommand("list")
    @Description("owner list")
    suspend fun CommandSender.list() {
        sendMessage(PlainText(owners.joinToString("\n")))
    }

    @SubCommand("add")
    @Description("add owner")
    suspend fun CommandSender.add(user: User) {
        if(Global.promiseUtils.isMaster(this.user)) {
            val success = owners.add(user.id)
            sendMessage(PlainText("添加${user.id}${if (success) "成功" else "失败"}"))
        }
    }

    @SubCommand("del")
    @Description("del owner")
    suspend fun CommandSender.del(user: User) {
        if (Global.promiseUtils.isMaster(this.user)) {
            val success = owners.remove(user.id)
            sendMessage(PlainText("删除${user.id}${if (success) "成功" else "失败"}"))
        }
    }
}