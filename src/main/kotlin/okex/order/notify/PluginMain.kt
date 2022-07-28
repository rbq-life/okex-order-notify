package okex.order.notify

import kotlinx.coroutines.launch
import net.mamoe.mirai.console.command.CommandManager
import net.mamoe.mirai.console.command.CommandManager.INSTANCE.register
import net.mamoe.mirai.console.command.CommandSender.Companion.toCommandSender
import net.mamoe.mirai.console.command.descriptor.ExperimentalCommandDescriptors
import net.mamoe.mirai.console.permission.AbstractPermitteeId
import net.mamoe.mirai.console.permission.PermissionService.Companion.permit
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.console.util.ConsoleExperimentalApi
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.events.*
import net.mamoe.mirai.event.globalEventChannel
import net.mamoe.mirai.utils.info
import okex.order.notify.plugin.command.TraderCommand
import okex.order.notify.plugin.command.OwnerCommand
import okex.order.notify.plugin.command.SubscribeCommand
import okex.order.notify.plugin.data.TradeNotifyConfig
import okex.order.notify.plugin.data.TradeOrderData


object PluginMain : KotlinPlugin(
    JvmPluginDescription(
        id = "okex.order.notify.PluginMain",
        name = "okex.order.notify",
        version = "0.1.0"
    ) {
        author("@rbq.life")
        info(
            """
            Okex带单机器人, 
            配置你的ApiKey,带领学员们一起赚米吧~
        """.trimIndent()
        )
        // author 和 info 可以删除.
    }
) {
    @OptIn(ExperimentalCommandDescriptors::class, ConsoleExperimentalApi::class)
    override fun onEnable() {
        logger.info { "[loading] >>>[${this.description.name} v${this.description.version}]<<<  start " }
        TradeNotifyConfig.reload()
        TradeOrderData.reload()
        //配置文件目录 "${dataFolder.absolutePath}/"
        SubscribeCommand.register()
        OwnerCommand.register()
        TraderCommand.register()
        val eventChannel = GlobalEventChannel.parentScope(this)
        eventChannel.subscribeAlways<GroupMessageEvent>{
            //群消息
        }
        eventChannel.subscribeAlways<FriendMessageEvent>{
            //好友信息
//            sender.sendMessage("hi")
        }
        eventChannel.subscribeAlways<NewFriendRequestEvent>{
            //自动同意好友申请
            accept()
        }
        eventChannel.subscribeAlways<BotInvitedJoinGroupRequestEvent>{
            //自动同意加群申请
            accept()
        }
        AbstractPermitteeId.AnyContact.permit(this.parentPermission)
        globalEventChannel().subscribeAlways<MessageEvent> {
            val sender = kotlin.runCatching {
                this.toCommandSender()
            }.getOrNull() ?: return@subscribeAlways

            launch { // Async
                runCatching {
                    CommandManager.executeCommand(sender, message)
                }
            }
        }
    }
}
