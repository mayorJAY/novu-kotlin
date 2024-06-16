package co.novu.dto.response

import co.novu.dto.Channel
import co.novu.dto.Subscriber
import com.google.gson.annotations.SerializedName
import java.math.BigInteger

class SubscriberResponse(
    subscriberId: String? = null,
    firstName: String? = null,
    lastName: String? = null,
    email: String? = null,
    phone: String? = null,
    avatar: String? = null,
    id: String? = null,
    @SerializedName("_organizationId")
    var organizationId: String? = null,
    @SerializedName("_environmentId")
    var environmentId: String? = null,
    var deleted: Boolean? = null,
    var createdAt: String? = null,
    var updatedAt: String? = null,
    var channels: List<Channel>? = null,
    var locale: Any? = null,
    @SerializedName("__v")
    var version: BigInteger? = null,
    var isOnline: Boolean? = null,
    var lastOnlineAt: String? = null,
) : Subscriber(
        id,
        subscriberId,
        firstName,
        lastName,
        email,
        phone,
        avatar,
    )
