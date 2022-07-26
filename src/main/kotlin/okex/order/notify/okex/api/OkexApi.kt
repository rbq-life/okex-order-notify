package okex.order.notify.okex.api

import okhttp3.Headers
import okex.order.notify.okex.entity.OkexRes
import okex.order.notify.okex.entity.ApiKeyInfo
import okex.order.notify.utils.JsonUtils.jsonToObjectOrNull
import okex.order.notify.utils.JsonUtils.objectToJson
import okex.order.notify.utils.OkHttpUtils
import okex.order.notify.utils.OkHttpUtils.toSerialNameUrlParams
import okex.order.notify.utils.Utils.createSignature
import okex.order.notify.utils.Utils.getTimestamp
import okex.order.notify.okex.entity.account.*
import okex.order.notify.okex.entity.trade.*


object OkexApi {

    const val OKEX_HOST = "https://www.okx.com"

    fun createHeader(apiKeyInfo: ApiKeyInfo, signData: String): Headers {
        val timestamp = getTimestamp()
        val sign = createSignature(timestamp + signData, apiKeyInfo.secret)
        return Headers.Builder()
            .add("OK-ACCESS-KEY", apiKeyInfo.apiKey)
            .add("OK-ACCESS-SIGN", sign)
            .add("OK-ACCESS-TIMESTAMP", timestamp)
            .add("OK-ACCESS-PASSPHRASE", apiKeyInfo.passphrase)
            .add(if (apiKeyInfo.simulated) "x-simulated-trading:1" else "")
            .build()
    }

    open class OkexApiCategory(private val pathPrefix: String) {
        private val rootPath: String = "/api/v5/"

        fun OkexApiCall.createCurrentHeader(apiKeyInfo: ApiKeyInfo, path: String, `data`: String): Headers {
            return createHeader(apiKeyInfo, this.method.name + path + `data`)
        }

        fun OkexApiCall.getPath(): String {
            return rootPath + pathPrefix + "/" + this.path
        }

        inline fun <reified T : Any, reified D> getMethod(apiCall: OkexApiCall, query: T?, apiKeyInfo: ApiKeyInfo): D {
            val params = query?.toSerialNameUrlParams() ?: ""
            val path = apiCall.getPath() + params
            val headers = apiCall.createCurrentHeader(apiKeyInfo, path, "")
            val res = OkHttpUtils.getJson(OKEX_HOST + path, headers).jsonToObjectOrNull<OkexRes<D>>()
            if (res?.`data` == null || res.code != "0") {
                throw OkexApiCallException(apiCall, params, apiKeyInfo, res, res?.data?.objectToJson())
            }
            return res.`data`
        }

        inline fun <reified T : Any, reified D> postMethod(apiCall: OkexApiCall, body: T?, apiKeyInfo: ApiKeyInfo): D {
            val bodyMap = body?.objectToJson() ?: "{}"
            val path = apiCall.getPath()
            val headers = apiCall.createCurrentHeader(apiKeyInfo, path, bodyMap)
            val reqBody = OkHttpUtils.addJson(bodyMap)
            val res = OkHttpUtils.postJson(OKEX_HOST + path, reqBody, headers).jsonToObjectOrNull<OkexRes<D>>()
            if (res?.`data` == null || res.code != "0") {
                throw OkexApiCallException(apiCall, bodyMap, apiKeyInfo, res, res?.data?.objectToJson())
            }
            return res.`data`
        }

        inline fun <reified T : Any, reified D> sendRequest(apiCall: OkexApiCall, req: T?, apiKeyInfo: ApiKeyInfo): D {
            return if (apiCall.method == ApiMethod.GET) getMethod(apiCall, req, apiKeyInfo)
            else postMethod(apiCall, req, apiKeyInfo)
        }
    }


    object Account : OkexApiCategory("account") {

        fun config(apiKeyInfo: ApiKeyInfo): List<ConfigRes> {
            return sendRequest(OkexApiCall.ACCOUNT_CONFIG, null, apiKeyInfo)
        }

        fun balance(apiKeyInfo: ApiKeyInfo, req: BalanceReq? = null): List<BalanceRes> {
            return sendRequest(OkexApiCall.ACCOUNT_BALANCE, req, apiKeyInfo)
        }

        fun positions(apiKeyInfo: ApiKeyInfo, req: PositionsReq? = null): List<PositionRes> {
            return sendRequest(OkexApiCall.ACCOUNT_POSITIONS, req, apiKeyInfo)
        }

        fun leverageInfo(apiKeyInfo: ApiKeyInfo, req: LeverageInfoReq): List<LeverageInfoRes> {
            return sendRequest(OkexApiCall.ACCOUNT_LEVERAGE_INFO, req, apiKeyInfo)
        }

        fun setLeverage(apiKeyInfo: ApiKeyInfo, req: SetLeverageReq): List<SetLeverageRes> {
            return sendRequest(OkexApiCall.ACCOUNT_STE_LEVERAGE, req, apiKeyInfo)
        }

        fun setPositionMode(apiKeyInfo: ApiKeyInfo, req: SetPositionModeReq): List<SetPositionModeRes> {
            return sendRequest(OkexApiCall.ACCOUNT_SET_POSITION_MODE, req, apiKeyInfo)
        }

        fun maxSize(apiKeyInfo: ApiKeyInfo, req: MaxSizeReq): List<MaxSizeRes> {
            return sendRequest(OkexApiCall.ACCOUNT_MAX_SIZE, req, apiKeyInfo)
        }

        fun maxAvailSize(apiKeyInfo: ApiKeyInfo, req: MaxAvailSizeReq): List<MaxAvailSizeRes> {
            return sendRequest(OkexApiCall.ACCOUNT_MAX_AVAIL_SIZE, req, apiKeyInfo)
        }

        fun marginBalance(apiKeyInfo: ApiKeyInfo, req: MarginBalanceReq): List<MarginBalanceRes> {
            return sendRequest(OkexApiCall.ACCOUNT_MARGIN_BALANCE, req, apiKeyInfo)
        }

    }

    object Trade : OkexApiCategory("trade") {


        fun order(apiKeyInfo: ApiKeyInfo, req: OrderReq): List<OrderRes> {
            return sendRequest(OkexApiCall.TRADE_ORDER, req, apiKeyInfo)
        }

        fun batchOrders(apiKeyInfo: ApiKeyInfo, req: List<OrderReq>): List<OrderRes> {
            return sendRequest(OkexApiCall.TRADE_BATCH_ORDERS, req, apiKeyInfo)
        }

        fun amendOrder(apiKeyInfo: ApiKeyInfo, req: AmendOrderReq): List<OrderRes> {
            return sendRequest(OkexApiCall.TRADE_AMEND_ORDER, req, apiKeyInfo)
        }

        fun amendBatchOrders(apiKeyInfo: ApiKeyInfo, req: List<AmendOrderReq>): List<OrderRes> {
            return sendRequest(OkexApiCall.TRADE_AMEND_BATCH_ORDERS, req, apiKeyInfo)
        }

        fun cancelOrder(apiKeyInfo: ApiKeyInfo, req: GetOrCancelOrderReq): List<OrderRes> {
            return sendRequest(OkexApiCall.TRADE_CANCEL_ORDER, req, apiKeyInfo)
        }

        fun cancelBatchOrders(apiKeyInfo: ApiKeyInfo, req: List<GetOrCancelOrderReq>): List<OrderRes> {
            return sendRequest(OkexApiCall.TRADE_CANCEL_BATCH_ORDERS, req, apiKeyInfo)
        }

        fun closePosition(apiKeyInfo: ApiKeyInfo, req: ClosePositionReq): List<ClosePositionRes> {
            return sendRequest(OkexApiCall.TRADE_CLOSE_POSITION, req, apiKeyInfo)
        }

        // query

        fun getOrder(apiKeyInfo: ApiKeyInfo, req: GetOrCancelOrderReq): List<OrdersEntity> {
            return sendRequest(OkexApiCall.TRADE_GET_ORDER, req, apiKeyInfo)
        }

        fun ordersPending(apiKeyInfo: ApiKeyInfo, req: OrdersQueryReq): List<OrdersEntity> {
            return sendRequest(OkexApiCall.TRADE_ORDERS_PENDING, req, apiKeyInfo)
        }

        fun ordersHistory(apiKeyInfo: ApiKeyInfo, req: OrdersQueryReq): List<OrdersEntity> {
            return sendRequest(OkexApiCall.TRADE_ORDERS_HISTORY, req, apiKeyInfo)
        }

        fun fills(apiKeyInfo: ApiKeyInfo, req: OrdersQueryReq): List<FillsRes> {
            return sendRequest(OkexApiCall.TRADE_FILLS, req, apiKeyInfo)
        }

    }
}

enum class OkexApiCall(
    val method: ApiMethod,
    val path: String,
    val desc: String,
) {
    // account
    ACCOUNT_CONFIG(ApiMethod.GET, "config", "查看账户配置"),
    ACCOUNT_BALANCE(ApiMethod.GET, "balance", "查看账户余额"),
    ACCOUNT_POSITIONS(ApiMethod.GET, "positions", "查看持仓信息"),
    ACCOUNT_LEVERAGE_INFO(ApiMethod.GET, "leverage-info", "获取杠杆倍数"),
    ACCOUNT_STE_LEVERAGE(ApiMethod.POST, "set-leverage", "设置杠杆倍数"),
    ACCOUNT_SET_POSITION_MODE(ApiMethod.POST, "set-position-mode", "设置持仓模式"),
    ACCOUNT_MAX_SIZE(ApiMethod.GET, "max-size", "获取最大可买卖/开仓数量"),
    ACCOUNT_MAX_AVAIL_SIZE(ApiMethod.GET, "max-avail-size", "获取最大可用数量"),
    ACCOUNT_MARGIN_BALANCE(ApiMethod.POST, "position/margin-balance", "调整保证金"),

    // trade
    TRADE_GET_ORDER(ApiMethod.GET, "order", "获取订单信息"),
    TRADE_ORDER(ApiMethod.POST, "order", "下单"),
    TRADE_BATCH_ORDERS(ApiMethod.POST, "batch-orders", "批量下单"),
    TRADE_CANCEL_ORDER(ApiMethod.POST, "cancel-order", "撤单"),
    TRADE_CANCEL_BATCH_ORDERS(ApiMethod.POST, "cancel-batch-orders", "批量撤单"),
    TRADE_AMEND_ORDER(ApiMethod.POST, "amend-order", "修改订单"),
    TRADE_AMEND_BATCH_ORDERS(ApiMethod.POST, "amend-batch-orders", "批量修改订单"),
    TRADE_CLOSE_POSITION(ApiMethod.POST, "close-position", "市价仓位全平"),
    TRADE_ORDERS_PENDING(ApiMethod.GET, "orders-pending", "获取未成交订单列表"),
    TRADE_ORDERS_HISTORY(ApiMethod.GET, "orders-history", "获取历史订单记录（近七天）"),
    TRADE_FILLS(ApiMethod.GET, "fills", "获取成交明细（近三天）"),

    ;
}

enum class ApiMethod {
    GET,
    POST
}