package okex.order.notify.okex.api

import okex.order.notify.okex.entity.ApiKeyInfo


open class OkexException(apiKeyInfo: ApiKeyInfo, reason: String?) : RuntimeException(
    "[apiKey:${apiKeyInfo.apiKey}] $reason"
)