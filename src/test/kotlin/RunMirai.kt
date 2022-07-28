package org.example.mirai.plugin

import net.mamoe.mirai.console.MiraiConsole
import net.mamoe.mirai.console.plugin.PluginManager.INSTANCE.enable
import net.mamoe.mirai.console.plugin.PluginManager.INSTANCE.load
import net.mamoe.mirai.console.terminal.MiraiConsoleTerminalLoader
import net.mamoe.mirai.console.util.ConsoleExperimentalApi
import okex.order.notify.PluginMain
import okex.order.notify.okex.api.OkexApi
import java.net.ProxySelector
import java.net.URI

@OptIn(ConsoleExperimentalApi::class)
suspend fun main() {
    MiraiConsoleTerminalLoader.startAsDaemon()
    //如果是Kotlin
    PluginMain.load()
    PluginMain.enable()

    MiraiConsole.job.join()
}