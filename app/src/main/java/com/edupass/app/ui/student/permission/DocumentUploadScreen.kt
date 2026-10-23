package com.edupass.app.ui.student.permission

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edupass.app.R
import com.edupass.app.ui.theme.EduPassBlue
import com.edupass.app.ui.theme.EduPassCardBorder
import com.edupass.app.ui.theme.EduPassInfoBg
import com.edupass.app.ui.theme.EduPassInfoBorder
import com.edupass.app.ui.theme.EduPassNavy
import com.edupass.app.ui.theme.EduPassRed
import com.edupass.app.ui.theme.EduPassScreenBg
import com.edupass.app.ui.theme.EduPassTextMuted
import com.edupass.app.ui.theme.EduPassTextSecondary
import com.edupass.app.ui.theme.EduPassTheme
import com.edupass.app.ui.theme.EduPassWhite
import com.edupass.app.ui.components.EduPassTopHeader
import java.util.Locale

// ---------------------------------------------------------------------------
// Document Model
// ---------------------------------------------------------------------------

data class SelectedDocument(
    val uri: Uri,
    val fileName: String,
    val formattedSize: String,
    val sizeBytes: Long,
    val statusText: String = "Ready to upload"
)

private const val MAX_FILE_SIZE_BYTES = 10 * 1024 * 1024 // 10 MB

// ---------------------------------------------------------------------------
// Main Document Upload Screen
// ---------------------------------------------------------------------------

@Composable
fun DocumentUploadScreen(
    modifier: Modifier = Modifier,
    initialDocument: SelectedDocument? = null,
    onBackClick: () -> Unit = {},
    onContinueClick: (SelectedDocument?) -> Unit = {},
    onNotificationClick: () -> Unit = {}
) {
    val context = LocalContext.current
    var selectedDoc by remember { mutableStateOf(initialDocument) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var selectedNavTab by remember { mutableIntStateOf(1) } // 1 = Requests selected

    // Activity Result Launcher for Document Picking
    val documentPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        if (uri != null) {
            val resolvedDoc = resolveDocumentDetails(context, uri)
            if (resolvedDoc == null) {
                errorMessage = "Unable to read the chosen file. Please try again."
            } else if (resolvedDoc.sizeBytes > MAX_FILE_SIZE_BYTES) {
                errorMessage = "File exceeds 10 MB limit (${resolvedDoc.formattedSize}). Please choose a smaller file."
            } else {
                errorMessage = null
                selectedDoc = resolvedDoc
            }
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = EduPassScreenBg,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .statusBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.Start
        ) {
            // 1. Header Bar: College Logo + App Name + Notification Bell
            EduPassTopHeader(
                showBackButton = true,
                onBackClick = onBackClick,
                onNotificationClick = onNotificationClick
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 2. Section Subtitle and Screen Heading
            Text(
                text = "NEW PERMISSION",
                color = EduPassTextSecondary,
                fontSize = 11.5.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.8.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Supporting document",
                color = EduPassNavy,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = (-0.5).sp
            )

            Spacer(modifier = Modifier.height(18.dp))

            // 3. Three-Segment Progress Indicator (Steps 1 & 2 Completed)
            ThreeStepProgressIndicator(completedSteps = 2)

            Spacer(modifier = Modifier.height(10.dp))

            // Step counter subtext
            Text(
                text = "2 of 3 · Documents",
                color = EduPassTextSecondary,
                fontSize = 13.5.sp,
                fontWeight = FontWeight.Normal
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 4. "Add a document" Card
            AddDocumentCard(
                onChooseFileClick = {
                    documentPickerLauncher.launch(
                        arrayOf("application/pdf", "image/jpeg", "image/png")
                    )
                }
            )

            // Validation Error Message (if any)
            if (errorMessage != null) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = errorMessage.orEmpty(),
                    color = EduPassRed,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
            }

            // 5. Selected Document Card (Shown when file is chosen or in preview)
            if (selectedDoc != null) {
                Spacer(modifier = Modifier.height(16.dp))
                SelectedDocumentCard(
                    document = selectedDoc!!,
                    onDeleteClick = {
                        selectedDoc = null
                        errorMessage = null
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 6. Supporting Documents Informational Banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(EduPassInfoBg, RoundedCornerShape(16.dp))
                    .border(1.dp, EduPassInfoBorder, RoundedCornerShape(16.dp))
                    .padding(horizontal = 16.dp, vertical = 14.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .border(1.5.dp, EduPassBlue, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "i",
                            color = EduPassBlue,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Supporting documents help faculty verify your request.",
                        color = EduPassTextSecondary,
                        fontSize = 13.5.sp,
                        fontWeight = FontWeight.Normal,
                        lineHeight = 18.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 7. Navigation Buttons: Back and Continue
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, EduPassCardBorder),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = EduPassWhite,
                        contentColor = EduPassBlue
                    )
                ) {
                    Text(
                        text = "Back",
                        color = EduPassBlue,
                        fontSize = 14.5.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Button(
                    onClick = { onContinueClick(selectedDoc) },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = EduPassBlue,
                        contentColor = EduPassWhite
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Continue",
                            color = EduPassWhite,
                            fontSize = 14.5.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "→",
                            color = EduPassWhite,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}


// ---------------------------------------------------------------------------
// 3-Segment Progress Indicator (Steps 1 & 2 completed, step 3 incomplete)
// ---------------------------------------------------------------------------

@Composable
private fun ThreeStepProgressIndicator(completedSteps: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (step in 1..3) {
            val isCompleted = step <= completedSteps
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(5.dp)
                    .clip(RoundedCornerShape(50))
                    .background(if (isCompleted) EduPassBlue else Color(0xFFDCE4EE))
            )
        }
    }
}

// ---------------------------------------------------------------------------
// Card: "Add a document"
// ---------------------------------------------------------------------------

@Composable
private fun AddDocumentCard(onChooseFileClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = EduPassWhite),
        border = BorderStroke(1.dp, EduPassCardBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Blue container document icon
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .background(Color(0xFFEBF3FE), RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        imageVector = UploadScreenIcons.DocumentFilledLines,
                        contentDescription = null,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column {
                    Text(
                        text = "Add a document",
                        color = EduPassNavy,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "PDF, JPG or PNG · Max 10 MB",
                        color = EduPassTextSecondary,
                        fontSize = 13.5.sp,
                        fontWeight = FontWeight.Normal
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // "Choose file" Outlined Button
            OutlinedButton(
                onClick = onChooseFileClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, EduPassCardBorder),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = EduPassWhite,
                    contentColor = EduPassBlue
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        imageVector = UploadScreenIcons.UploadArrow,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Choose file",
                        color = EduPassBlue,
                        fontSize = 14.5.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Card: Selected Document
// ---------------------------------------------------------------------------

@Composable
private fun SelectedDocumentCard(
    document: SelectedDocument,
    onDeleteClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = EduPassInfoBg),
        border = BorderStroke(1.dp, EduPassInfoBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                imageVector = UploadScreenIcons.DocumentOutline,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = document.fileName,
                    color = EduPassNavy,
                    fontSize = 14.5.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = document.formattedSize,
                    color = EduPassTextSecondary,
                    fontSize = 12.5.sp,
                    fontWeight = FontWeight.Normal
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = document.statusText,
                    color = Color(0xFF0284C7), // Teal/cyan "Ready to upload"
                    fontSize = 12.5.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Delete trash icon
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { onDeleteClick() },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    imageVector = UploadScreenIcons.TrashBinOutline,
                    contentDescription = "Remove file",
                    modifier = Modifier.size(19.dp)
                )
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Persistent Bottom Navigation Bar (Requests Tab Selected)
// ---------------------------------------------------------------------------

@Composable
private fun PermissionBottomNavBar(
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    Surface(
        color = EduPassWhite,
        border = BorderStroke(1.dp, EduPassCardBorder),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val tabs = listOf("Home", "Requests", "Passes", "Profile")
            tabs.forEachIndexed { index, title ->
                val isSelected = index == selectedTabIndex
                val color = if (isSelected) EduPassBlue else EduPassTextMuted

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { onTabSelected(index) }
                        .padding(horizontal = 14.dp, vertical = 4.dp)
                ) {
                    val icon = when (index) {
                        0 -> UploadScreenIcons.NavHome
                        1 -> UploadScreenIcons.NavRequests
                        2 -> UploadScreenIcons.NavPasses
                        else -> UploadScreenIcons.NavProfile
                    }
                    Image(
                        imageVector = icon,
                        contentDescription = title,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = title,
                        color = color,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium
                    )
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Utility: Content Resolver File Details Resolver
// ---------------------------------------------------------------------------

private fun resolveDocumentDetails(context: Context, uri: Uri): SelectedDocument? {
    return try {
        context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            val nameIdx = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            val sizeIdx = cursor.getColumnIndex(OpenableColumns.SIZE)
            if (cursor.moveToFirst()) {
                val fileName = if (nameIdx != -1) cursor.getString(nameIdx) ?: "document" else "document"
                val sizeBytes = if (sizeIdx != -1) cursor.getLong(sizeIdx) else 0L
                val formattedSize = formatFileSize(sizeBytes)
                SelectedDocument(
                    uri = uri,
                    fileName = fileName,
                    formattedSize = formattedSize,
                    sizeBytes = sizeBytes
                )
            } else null
        }
    } catch (_: Exception) {
        null
    }
}

private fun formatFileSize(bytes: Long): String {
    return when {
        bytes >= 1024 * 1024 -> String.format(Locale.getDefault(), "%.1f MB", bytes / (1024f * 1024f))
        bytes >= 1024 -> "${bytes / 1024} KB"
        else -> "$bytes B"
    }
}

// ---------------------------------------------------------------------------
// Standalone Vector Icon Definitions
// ---------------------------------------------------------------------------

private object UploadScreenIcons {
    val NotificationBell: ImageVector = ImageVector.Builder(
        name = "NotificationBell",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(
        stroke = SolidColor(EduPassNavy),
        strokeLineWidth = 1.8f
    ) {
        moveTo(18f, 8f)
        curveTo(18f, 5.79f, 16.21f, 4f, 14f, 4f)
        lineTo(10f, 4f)
        curveTo(7.79f, 4f, 6f, 5.79f, 6f, 8f)
        curveTo(6f, 13f, 4f, 15f, 4f, 17f)
        lineTo(20f, 17f)
        curveTo(20f, 15f, 18f, 13f, 18f, 8f)
        close()
        moveTo(10f, 19f)
        curveTo(10f, 20.1f, 10.9f, 21f, 12f, 21f)
        curveTo(13.1f, 21f, 14f, 20.1f, 14f, 19f)
    }.build()

    val DocumentFilledLines: ImageVector = ImageVector.Builder(
        name = "DocumentFilledLines",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(
        stroke = SolidColor(EduPassBlue),
        strokeLineWidth = 1.8f
    ) {
        moveTo(6f, 4f)
        lineTo(14f, 4f)
        lineTo(19f, 9f)
        lineTo(19f, 20f)
        curveTo(19f, 20.6f, 18.6f, 21f, 18f, 21f)
        lineTo(6f, 21f)
        curveTo(5.4f, 21f, 5f, 20.6f, 5f, 20f)
        lineTo(5f, 5f)
        curveTo(5f, 4.4f, 5.4f, 4f, 6f, 4f)
        close()
        moveTo(14f, 4f)
        lineTo(14f, 9f)
        lineTo(19f, 9f)
        moveTo(9f, 13f)
        lineTo(15f, 13f)
        moveTo(9f, 17f)
        lineTo(13f, 17f)
    }.build()

    val UploadArrow: ImageVector = ImageVector.Builder(
        name = "UploadArrow",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(
        stroke = SolidColor(EduPassBlue),
        strokeLineWidth = 1.8f
    ) {
        moveTo(4f, 17f)
        lineTo(4f, 19f)
        curveTo(4f, 19.6f, 4.4f, 20f, 5f, 20f)
        lineTo(19f, 20f)
        curveTo(19.6f, 20f, 20f, 19.6f, 20f, 19f)
        lineTo(20f, 17f)
        moveTo(12f, 4f)
        lineTo(12f, 15f)
        moveTo(7f, 9f)
        lineTo(12f, 4f)
        lineTo(17f, 9f)
    }.build()

    val DocumentOutline: ImageVector = ImageVector.Builder(
        name = "DocumentOutline",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(
        stroke = SolidColor(EduPassBlue),
        strokeLineWidth = 1.8f
    ) {
        moveTo(6f, 4f)
        lineTo(14f, 4f)
        lineTo(19f, 9f)
        lineTo(19f, 20f)
        curveTo(19f, 20.6f, 18.6f, 21f, 18f, 21f)
        lineTo(6f, 21f)
        curveTo(5.4f, 21f, 5f, 20.6f, 5f, 20f)
        lineTo(5f, 5f)
        curveTo(5f, 4.4f, 5.4f, 4f, 6f, 4f)
        close()
        moveTo(14f, 4f)
        lineTo(14f, 9f)
        lineTo(19f, 9f)
    }.build()

    val TrashBinOutline: ImageVector = ImageVector.Builder(
        name = "TrashBinOutline",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(
        stroke = SolidColor(EduPassTextSecondary),
        strokeLineWidth = 1.7f
    ) {
        moveTo(4f, 7f)
        lineTo(20f, 7f)
        moveTo(10f, 11f)
        lineTo(10f, 17f)
        moveTo(14f, 11f)
        lineTo(14f, 17f)
        moveTo(5f, 7f)
        lineTo(6f, 19f)
        curveTo(6f, 19.6f, 6.4f, 20f, 7f, 20f)
        lineTo(17f, 20f)
        curveTo(17.6f, 20f, 18f, 19.6f, 18f, 19f)
        lineTo(19f, 7f)
        moveTo(9f, 7f)
        lineTo(9f, 4f)
        lineTo(15f, 4f)
        lineTo(15f, 7f)
    }.build()

    val NavHome: ImageVector = ImageVector.Builder(
        name = "NavHome",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(
        stroke = SolidColor(EduPassTextMuted),
        strokeLineWidth = 1.8f
    ) {
        moveTo(4f, 11f)
        lineTo(12f, 4f)
        lineTo(20f, 11f)
        lineTo(20f, 19f)
        curveTo(20f, 19.6f, 19.6f, 20f, 19f, 20f)
        lineTo(5f, 20f)
        curveTo(4.4f, 20f, 4f, 19.6f, 4f, 19f)
        close()
    }.build()

    val NavRequests: ImageVector = ImageVector.Builder(
        name = "NavRequests",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(
        stroke = SolidColor(EduPassBlue), // Active Blue on Requests
        strokeLineWidth = 1.8f
    ) {
        moveTo(7f, 4f)
        lineTo(17f, 4f)
        lineTo(17f, 19f)
        lineTo(7f, 19f)
        close()
        moveTo(10f, 8f)
        lineTo(14f, 8f)
        moveTo(10f, 12f)
        lineTo(14f, 12f)
    }.build()

    val NavPasses: ImageVector = ImageVector.Builder(
        name = "NavPasses",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(
        stroke = SolidColor(EduPassTextMuted),
        strokeLineWidth = 1.8f
    ) {
        moveTo(4f, 4f)
        lineTo(10f, 4f)
        lineTo(10f, 10f)
        lineTo(4f, 10f)
        close()
        moveTo(14f, 4f)
        lineTo(20f, 4f)
        lineTo(20f, 10f)
        lineTo(14f, 10f)
        close()
        moveTo(4f, 14f)
        lineTo(10f, 14f)
        lineTo(10f, 20f)
        lineTo(4f, 20f)
        close()
        moveTo(14f, 14f)
        lineTo(20f, 14f)
        lineTo(20f, 20f)
        lineTo(14f, 20f)
        close()
    }.build()

    val NavProfile: ImageVector = ImageVector.Builder(
        name = "NavProfile",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(
        stroke = SolidColor(EduPassTextMuted),
        strokeLineWidth = 1.8f
    ) {
        moveTo(12f, 11f)
        curveTo(13.65f, 11f, 15f, 9.65f, 15f, 8f)
        curveTo(15f, 6.35f, 13.65f, 5f, 12f, 5f)
        curveTo(10.35f, 5f, 9f, 6.35f, 9f, 8f)
        curveTo(9f, 9.65f, 10.35f, 11f, 12f, 11f)
        close()
        moveTo(5f, 19f)
        curveTo(5f, 15.7f, 8.1f, 14f, 12f, 14f)
        curveTo(15.9f, 14f, 19f, 15.7f, 19f, 19f)
    }.build()
}

// ---------------------------------------------------------------------------
// Preview (Matches Figma Mockup State)
// ---------------------------------------------------------------------------

@Preview(
    showBackground = true,
    showSystemUi = true,
    device = "spec:width=390dp,height=844dp,dpi=420"
)
@Composable
fun DocumentUploadScreenPreview() {
    EduPassTheme {
        DocumentUploadScreen(
            initialDocument = SelectedDocument(
                uri = Uri.EMPTY,
                fileName = "Nexora_Internship_Letter.pdf",
                formattedSize = "248 KB",
                sizeBytes = 248 * 1024L
            )
        )
    }
}