package trade.wsure.top.websocket

import okhttp3.*
import okio.ByteString
import trade.wsure.top.entity.ApiKeyInfo
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicBoolean

class OkexWebsocketWrapper(listener: WebSocketListener, url: String? = null) {
    val closeFlag = AtomicBoolean(false)

    private val listenerObject = object : WebSocketListener() {
        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            listener.onClosed(webSocket, code, reason)
            if(!closeFlag.get()){
                reSetWebsocket(createNewWebsocket(OkexWebsocketClient, createWebsocketRequest(url)))
            }
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            listener.onFailure(webSocket, t, response)
            reSetWebsocket(createNewWebsocket(OkexWebsocketClient, createWebsocketRequest(url)))
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            listener.onClosing(webSocket, code, reason)
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            listener.onMessage(webSocket, bytes)
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            listener.onMessage(webSocket, text)
        }

        override fun onOpen(webSocket: WebSocket, response: Response) {
            listener.onOpen(webSocket, response)
        }
    }

    var websocket = createNewWebsocket(OkexWebsocketClient, createWebsocketRequest(url))

    private fun createNewWebsocket(client: OkHttpClient, request: Request): WebSocket {
        return client.newWebSocket(request, listenerObject)
    }

    fun reSetWebsocket(ws: WebSocket) {
        this.websocket = ws
    }

    fun closeWebsocket(){
        closeFlag.getAndSet(true)
        this.websocket.close(1000,"close")
    }

    companion object {
        val SimulatedUrl = "wss://wspap.okx.com:8443/ws/v5/private?brokerId=999"
        val RealUrl = "wss://ws.okx.com:8443/ws/v5/public"
        val OkexWebsocketClient by lazy {
            OkHttpClient.Builder().build()
        }

        fun createWebsocketRequest(url:String? ): Request{
            return Request.Builder()
                .get()
                .url(if(url.isNullOrBlank()) SimulatedUrl else url)
                .build()
        }
    }
}