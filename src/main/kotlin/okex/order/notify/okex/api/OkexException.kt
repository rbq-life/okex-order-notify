package okex.order.notify.api

import okex.order.notify.entity.ApiKeyInfo


open class OkexException(apiKeyInfo: ApiKeyInfo, reason: String?) : RuntimeException(
    "[apiKey:${apiKeyInfo.apiKey}] $reason"
)