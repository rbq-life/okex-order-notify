package trade.wsure.top.websocket.listener

import kotlinx.coroutines.*
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import trade.wsure.top.api.entity.InstType
import trade.wsure.top.api.entity.account.BalanceRes
import trade.wsure.top.api.entity.account.PositionRes
import trade.wsure.top.api.entity.trade.OrdersEntity
import trade.wsure.top.entity.ApiKeyInfo
import trade.wsure.top.utils.JsonUtils.jsonToObjectOrNull
import trade.wsure.top.utils.JsonUtils.objectToJson
import trade.wsure.top.utils.Utils.log
import trade.wsure.top.websocket.entity.*
import trade.wsure.top.websocket.entity.channel.WsChannel
import trade.wsure.top.websocket.entity.login.WsLoginReq
import trade.wsure.top.websocket.entity.login.WsLoginRes
import trade.wsure.top.websocket.entity.push.BalanceAndPositionRes
import java.util.concurrent.atomic.AtomicInteger

class OkexListener(private val apiKeyInfos: List<ApiKeyInfo>, val eventListener: PushEventListener = OkexMemoryCacheEventListener) : WebSocketListener() {
    lateinit var loginDeferred: CompletableDeferred<List<WsLoginReq>>
    lateinit var currentWebsocket: WebSocket

    @OptIn(DelicateCoroutinesApi::class)
    override fun onOpen(webSocket: WebSocket, response: Response) {
        currentWebsocket = webSocket
        log.trace("on open !")
        GlobalScope.launch {
            val failApiKeys = kotlin.runCatching { login(webSocket) }.onFailure {
                log.error("Websocket login timeout!! ")
            }.getOrNull() ?: apiKeyInfos.map { WsLoginReq(it) }
            log.trace("failApiKeys :${failApiKeys.joinToString(",") { it.apiKey }}")
            subscribeChannel(webSocket)
        }

        setHeartbeat(webSocket)

    }

    private fun subscribeChannel(webSocket: WebSocket) {
        val args = listOf(
            WsChannel.getAccountReq(),
            WsChannel.getOrdersReq(InstType.SWAP),
            WsChannel.getBalanceAndPositionReq(),
            WsChannel.getPositionsReq(InstType.SWAP)
        )
        val req = OkexWSReq(OkexWSOPEnum.subscribe, args).objectToJson()
        webSocket.send(req)
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        log.trace("on closed !")

    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        log.trace("onMessage : $text")
        if (text != "pong") {
            text.jsonToObjectOrNull<OkexWSEventType>(false)?.also {
                when (it.event) {
                    OkexWSEventEnum.login -> {
                        val res = text.jsonToObjectOrNull<WsLoginRes>()
                        when (res?.code) {
                            "0" -> {
                                loginDeferred.complete(emptyList())
                            }
                            "60022" -> {
                                loginDeferred.complete(res.data ?: emptyList())
                            }
                            else -> {
                                log.trace("unknown login error ${res?.code} ${res?.msg}")
                            }
                        }
                    }
                    OkexWSEventEnum.error -> {
                        val res = text.jsonToObjectOrNull<OkexWSSubscribeRes>()
                        log.trace("unknown login error ${res?.code} ${res?.msg}")
                    }
                    else -> {}
                }
            }
            text.jsonToObjectOrNull<OkexWSChannelType>(false)?.also {
                when (it.arg.channel) {
                    WsChannel.account -> {
                        if (it.arg.uid != null) {
                            text.jsonToObjectOrNull<OkexWSPushRes<List<BalanceRes>>>()?.`data`?.apply {
                                log.trace("push account message :${this.objectToJson()}")
                                eventListener.onAccount(it.arg.uid, this)
                            }
                        }
                    }
                    WsChannel.positions -> {
                        if (it.arg.uid != null) {
                            text.jsonToObjectOrNull<OkexWSPushRes<List<PositionRes>>>()?.`data`?.apply {
                                log.trace("push positions message :${this.objectToJson()}")
                                eventListener.onPositions(it.arg.uid, this)
                            }
                        }
                    }
                    WsChannel.balance_and_position -> {
                        if (it.arg.uid != null) {
                            text.jsonToObjectOrNull<OkexWSPushRes<List<BalanceAndPositionRes>>>()?.`data`?.apply {
                                log.trace("push positions message :${this.objectToJson()}")
                                eventListener.onBalanceAndPosition(it.arg.uid, this)
                            }
                        }
                    }
                    WsChannel.orders -> {
                        if (it.arg.uid != null) {
                            text.jsonToObjectOrNull<OkexWSPushRes<List<OrdersEntity>>>()?.`data`?.apply {
                                log.trace("push orders message :${this.objectToJson()}")
                                eventListener.onOrders(it.arg.uid, this)
                            }
                        }
                    }
                    else -> {

                    }
                }
            }
        }


        if (text.contains("close！")) {
            webSocket.close(1000, "test")
        }
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {

    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        log.trace("onClosing ,$reason")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        log.warn("onFailure", t)
    }


    suspend fun login(webSocket: WebSocket = currentWebsocket, akis: List<ApiKeyInfo> = apiKeyInfos): List<WsLoginReq> {
        val loginReq = OkexWSReq(OkexWSOPEnum.login, akis.map { WsLoginReq(it) }.toList()).objectToJson()
        log.trace("login req:$loginReq")
        try {
            loginDeferred = CompletableDeferred()
            webSocket.send(loginReq)
            return withTimeout(10000) {
                loginDeferred.await()
            }
        } catch (e: Exception) {
            log.error("sendPkg", e)
            throw RuntimeException("fail!")
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun setHeartbeat(webSocket: WebSocket) {
        GlobalScope.launch {
            while (true) {
                webSocket.send("ping")
                delay(20000)
            }
        }
    }
}