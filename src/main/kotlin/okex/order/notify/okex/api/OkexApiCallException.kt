package okex.order.notify.api

import okex.order.notify.api.entity.OkexRes
import okex.order.notify.entity.ApiKeyInfo

class OkexApiCallException(
    apiCall: OkexApiCall,
    bodyOrQuery: String,
    apiKeyInfo: ApiKeyInfo,
    res: OkexRes<*>? = null,
    `data`: String? = null
) : OkexException(
    apiKeyInfo,
    "${apiCall.method} ${apiCall.name} Fail ! ${if (apiCall.method == ApiMethod.GET) "query" else "reqBody"}:$bodyOrQuery. result: code: ${res?.code} msg: ${res?.msg}${if (`data` != null) "\n data: $`data`" else ""}"
)