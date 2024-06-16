package co.novu.dto.request

data class SubscriberRequest(
    val subscriberId: String,
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val avatar: String? = null,
    val locale: String? = null,
    val data: Any? = null,
)
