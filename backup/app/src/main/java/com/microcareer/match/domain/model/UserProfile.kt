package com.microcareer.match.domain.model

// FIX (Bug 4): The original UserProfile only had id, name, email, and skills.
// ProfileViewModel was using getDeclaredField() reflection to read phoneNumber,
// location, profilePhotoUrl, rating, and completedJobs — which means those
// fields didn't exist on the class, so the profile screen showed blanks for
// phone, location, and the stats card.
//
// Add the missing fields here so ProfileViewModel can access them directly
// and ProfileRepositoryImpl can map them from UserEntity without hacks.

data class UserProfile(
    val id              : String,
    val name            : String,
    val email           : String,
    val skills          : List<String>       = emptyList(),
    // Fields that were previously accessed via reflection (always returned null)
    val phoneNumber     : String?            = null,
    val location        : String?            = null,
    val profilePhotoUrl : String?            = null,
    val rating          : Float              = 0f,
    val completedJobs   : Int                = 0,
    val bio             : String?            = null
)