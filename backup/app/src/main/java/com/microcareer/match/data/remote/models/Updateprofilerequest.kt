package com.microcareer.match.data.remote.models

import com.google.gson.annotations.SerializedName

// Used by PUT /api/v1/users/me to update name, phone, location.
// Your backend UserUpdate schema already accepts all three fields as optional.
data class UpdateProfileRequest(
    @SerializedName("full_name")    val fullName    : String?  = null,
    @SerializedName("phone_number") val phoneNumber : String?  = null,
    @SerializedName("location")     val location    : String?  = null
)