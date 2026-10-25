package com.edupass.app.ui.student.permission

import android.net.Uri

/**
 * Shared state for the 3-step permission application workflow:
 * Step 1: ApplyPermissionScreen (details, schedule, organization)
 * Step 2: DocumentUploadScreen (supporting file attachment)
 * Step 3: RequestReviewScreen (final check before submission)
 */
data class PermissionFormState(
    val permissionType: String = "Internship",
    val requestTitle: String = "",
    val selectedDate: String = "18 Jun 2025",
    val startTime: String = "10:00 AM",
    val endTime: String = "02:00 PM",
    val reason: String = "",
    val organization: String = "",
    val contactDetails: String = "",
    val selectedDocument: SelectedDocument? = null
) {
    val documentUri: Uri? get() = selectedDocument?.uri
    val documentFileName: String? get() = selectedDocument?.fileName

    fun toReviewData(studentNameWithId: String): RequestReviewData {
        val scheduleFormatted = if (selectedDate.isNotBlank()) {
            if (startTime.isNotBlank() && endTime.isNotBlank()) {
                "$selectedDate · $startTime–$endTime"
            } else if (startTime.isNotBlank()) {
                "$selectedDate · $startTime"
            } else {
                selectedDate
            }
        } else {
            "—"
        }

        return RequestReviewData(
            studentNameWithId = studentNameWithId,
            permissionType = permissionType.ifBlank { "—" },
            schedule = scheduleFormatted,
            destination = organization.ifBlank { "—" },
            reason = reason.ifBlank { "—" },
            documentName = selectedDocument?.fileName
        )
    }
}

