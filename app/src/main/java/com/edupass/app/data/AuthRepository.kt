package com.edupass.app.data

import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.postgrest.from
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserProfile(
    val id: String,
    @SerialName("college_id")
    val collegeId: String,
    @SerialName("full_name")
    val fullName: String,
    val role: String
)

sealed interface LoginResult {
    data class Success(val profile: UserProfile) : LoginResult
    data class Failure(val message: String) : LoginResult
}

object AuthRepository {
    var currentProfile: UserProfile? = null
        private set

    private val supabase = SupabaseProvider.client

    private fun rollNumberToEmail(rollNumber: String): String {
        return "${rollNumber.trim().lowercase()}@users.edupass.invalid"
    }

    suspend fun login(
        rollNumber: String,
        password: String,
        selectedRole: String
    ): LoginResult {
        val normalizedRollNumber = rollNumber.trim().lowercase()

        if (normalizedRollNumber.isBlank() || password.isBlank()) {
            return LoginResult.Failure("Enter your roll number and password.")
        }

        try {
            supabase.auth.signInWith(Email) {
                email = rollNumberToEmail(normalizedRollNumber)
                this.password = password
            }

            val userId = supabase.auth.currentUserOrNull()?.id
                ?: return LoginResult.Failure("Login failed. Please try again.")

            val profiles = supabase.from("profiles")
                .select {
                    filter {
                        eq("id", userId)
                        eq("college_id", normalizedRollNumber)
                    }
                }
                .decodeList<UserProfile>()

            val profile = profiles.firstOrNull()
                ?: run {
                    supabase.auth.signOut()
                    currentProfile = null
                    return LoginResult.Failure(
                        "Your account profile could not be found."
                    )
                }

            if (profile.role != selectedRole.lowercase()) {
                supabase.auth.signOut()
                currentProfile = null
                return LoginResult.Failure(
                    "The selected role does not match your account."
                )
            }

            currentProfile = profile
            return LoginResult.Success(profile)

        } catch (e: Exception) {
            return LoginResult.Failure(
                "Unable to sign in. Check your credentials and connection."
            )
        }
    }

    suspend fun logout() {
        supabase.auth.signOut()
        currentProfile = null
    }
}