package okex.order.notify.plugin.entity

import kotlinx.serialization.Serializable
import net.mamoe.mirai.message.data.Message
import net.mamoe.mirai.message.data.PlainText
import net.mamoe.mirai.message.data.messageChainOf
import okex.order.notify.okex.entity.ApiKeyInfo

@Serializable
data class Trader(
    val name:String,
    val qq:Long,
    val meritBox:List<String>? = emptyList(),
    val apiKey:ApiKeyInfo,
){
    fun toMessage(): Message{
        return messageChainOf(
            PlainText("老师: ${this.name}\n"),
            PlainText("qq: ${this.qq}\n"),
            PlainText("apiKey: ${this.apiKey.apiKey.slice(0..7)}\n"),
            PlainText("功德箱: ${(this.meritBox?: emptyList<String>()).joinToString("\n") { it }}"),
        )
    }
}
