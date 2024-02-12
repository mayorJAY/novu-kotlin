package co.novu.dto

import com.google.gson.annotations.SerializedName

data class Job(
    @SerializedName("_id")
    var id: String? = null,
    var digest: Any? = null,
    var status: String? = null,
    var payload: Any? = null,
    var step: Step? = null,
    @SerializedName("_notificationId")
    var notificationId: String? = null,
    var type: String? = null,
    var createdAt: String? = null,
    var updatedAt: String? = null,
    var executionDetails: List<ExecutionDetails>? = null,
    var providerId: String? = null
)
