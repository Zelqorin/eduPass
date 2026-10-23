package com.edupass.app.ui.student.pass

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edupass.app.R
import com.edupass.app.ui.theme.EduPassBlue
import com.edupass.app.ui.theme.EduPassCardBorder
import com.edupass.app.ui.theme.EduPassNavy
import com.edupass.app.ui.theme.EduPassScreenBg
import com.edupass.app.ui.theme.EduPassTextSecondary
import com.edupass.app.ui.theme.EduPassTheme
import com.edupass.app.ui.theme.EduPassWhite

// ---------------------------------------------------------------------------
// Data Models
// ---------------------------------------------------------------------------

data class DigitalPassData(
    val permissionTitle: String = "Cloud Foundations Workshop",
    val statusText: String = "Approved",
    val authorizationId: String = "CP-2025-00482",
    val studentName: String = "Arjun Kumar",
    val studentId: String = "22CSE1047",
    val permissionType: String = "Workshop",
    val destination: String = "TechHub Auditorium",
    val date: String = "12 Jun 2025",
    val validTime: String = "11:00 AM–01:00 PM"
)

data class PassTimelineStage(
    val title: String,
    val subtitle: String
)

// ---------------------------------------------------------------------------
// Main Screen Composable
// ---------------------------------------------------------------------------

@Composable
fun DigitalPermissionPassScreen(
    modifier: Modifier = Modifier,
    passData: DigitalPassData = remember { DigitalPassData() },
    onNotificationClick: () -> Unit = {},
    onViewDigitalPassClick: (String) -> Unit = {}
) {
    val timelineStages = remember {
        listOf(
            PassTimelineStage("Student submitted", "Request received"),
            PassTimelineStage("Faculty", "Reviewed and Approved"),
            PassTimelineStage("HOD Approved", "Final review completed"),
            PassTimelineStage("Digital pass issued", "Ready for campus verification")
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(EduPassScreenBg)
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.Start
    ) {
        // 1. Header: Brand Logo + Notification Bell
        PassHeader(onNotificationClick = onNotificationClick)

        Spacer(modifier = Modifier.height(20.dp))

        // 2. Permission Summary Blue Banner
        PermissionSummaryBanner(passData = passData)

        Spacer(modifier = Modifier.height(16.dp))

        // 3. Digital Authorization Card (Two-column detail grid)
        DigitalAuthorizationCard(passData = passData)

        Spacer(modifier = Modifier.height(24.dp))

        // 4. Approval Timeline Section Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Approval timeline",
                color = EduPassNavy,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Complete",
                color = EduPassBlue,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 5. Approval Timeline Card
        ApprovalTimelineCard(stages = timelineStages)

        Spacer(modifier = Modifier.height(24.dp))

        // 6. Primary Action: "View digital pass" Button
        Button(
            onClick = { onViewDigitalPassClick(passData.authorizationId) },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(14.dp),
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
                Image(
                    imageVector = PassIcons.DigitalPassBadge,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "View digital pass",
                    color = EduPassWhite,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

// ---------------------------------------------------------------------------
// Header Component
// ---------------------------------------------------------------------------

@Composable
private fun PassHeader(onNotificationClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.ic_edupass_logo),
                contentDescription = "VIIT Crest",
                modifier = Modifier.size(36.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = "EduPass",
                    color = EduPassNavy,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "STUDENT ACCESS",
                    color = EduPassTextSecondary,
                    fontSize = 10.5.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.8.sp
                )
            }
        }

        Box(
            modifier = Modifier
                .size(42.dp)
                .background(EduPassWhite, RoundedCornerShape(12.dp))
                .border(1.dp, EduPassCardBorder, RoundedCornerShape(12.dp))
                .clickable { onNotificationClick() },
            contentAlignment = Alignment.Center
        ) {
            Image(
                imageVector = PassIcons.NotificationBell,
                contentDescription = "Notifications",
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

// ---------------------------------------------------------------------------
// Permission Summary Banner (Blue Card with Approved Badge)
// ---------------------------------------------------------------------------

@Composable
private fun PermissionSummaryBanner(passData: DigitalPassData) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(22.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF3882F6),
                        Color(0xFF2563EB)
                    )
                )
            )
            .padding(22.dp)
    ) {
        Column {
            Text(
                text = "PERMISSION REQUEST",
                color = Color.White.copy(alpha = 0.85f),
                fontSize = 11.5.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.8.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = passData.permissionTitle,
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 34.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Approved Badge aligned to the bottom right
            Box(
                modifier = Modifier
                    .align(Alignment.End)
                    .clip(RoundedCornerShape(50))
                    .background(Color(0xFFE0F7F6))
                    .padding(horizontal = 14.dp, vertical = 5.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = passData.statusText,
                    color = Color(0xFF0F766E),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Digital Authorization Card
// ---------------------------------------------------------------------------

@Composable
private fun DigitalAuthorizationCard(passData: DigitalPassData) {
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
            Text(
                text = "DIGITAL AUTHORIZATION",
                color = EduPassTextSecondary,
                fontSize = 11.5.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.8.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = passData.authorizationId,
                color = EduPassNavy,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Two-column layout matching reference
            Row(modifier = Modifier.fillMaxWidth()) {
                // Column 1: Student & Valid Time
                Column(modifier = Modifier.weight(1f)) {
                    // Student
                    FieldLabelWithIcon(
                        icon = PassIcons.SmallPerson,
                        label = "Student"
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${passData.studentName} ·\n${passData.studentId}",
                        color = EduPassNavy,
                        fontSize = 14.5.sp,
                        fontWeight = FontWeight.SemiBold,
                        lineHeight = 20.sp
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    // Date and valid time
                    FieldLabelWithIcon(
                        icon = PassIcons.SmallClock,
                        label = "Date and valid time"
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = passData.date,
                        color = EduPassNavy,
                        fontSize = 14.5.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = passData.validTime,
                        color = EduPassTextSecondary,
                        fontSize = 12.5.sp,
                        fontWeight = FontWeight.Normal
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                // Column 2: Permission type & Destination
                Column(modifier = Modifier.weight(1f)) {
                    // Permission type
                    FieldLabelWithIcon(
                        icon = PassIcons.SmallBadge,
                        label = "Permission type"
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = passData.permissionType,
                        color = EduPassNavy,
                        fontSize = 14.5.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(28.dp))

                    // Destination
                    FieldLabelWithIcon(
                        icon = PassIcons.SmallPin,
                        label = "Destination"
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = passData.destination,
                        color = EduPassNavy,
                        fontSize = 14.5.sp,
                        fontWeight = FontWeight.SemiBold,
                        lineHeight = 19.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun FieldLabelWithIcon(icon: ImageVector, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(13.dp)
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = label,
            color = EduPassTextSecondary,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal
        )
    }
}

// ---------------------------------------------------------------------------
// Approval Timeline Card (Vertical Milestones with Checks)
// ---------------------------------------------------------------------------

@Composable
private fun ApprovalTimelineCard(stages: List<PassTimelineStage>) {
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
            stages.forEachIndexed { index, stage ->
                TimelineStageItem(
                    stage = stage,
                    isLast = index == stages.lastIndex
                )
            }
        }
    }
}

@Composable
private fun TimelineStageItem(
    stage: PassTimelineStage,
    isLast: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        // Left Column: Circle checkmark and connecting vertical bar
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(32.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFDCFCE7)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "✓",
                    color = Color(0xFF15803D),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            if (!isLast) {
                Box(
                    modifier = Modifier
                        .width(1.5.dp)
                        .height(28.dp)
                        .background(Color(0xFFE2E8F0))
                )
            }
        }

        Spacer(modifier = Modifier.width(14.dp))

        // Right Column: Title and Status Subtitle
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = if (isLast) 0.dp else 16.dp)
        ) {
            Text(
                text = stage.title,
                color = EduPassNavy,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = stage.subtitle,
                color = EduPassTextSecondary,
                fontSize = 13.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

// ---------------------------------------------------------------------------
// Standalone Vector Icons
// ---------------------------------------------------------------------------

private object PassIcons {
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

    val DigitalPassBadge: ImageVector = ImageVector.Builder(
        name = "DigitalPassBadge",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(
        stroke = SolidColor(EduPassWhite),
        strokeLineWidth = 1.8f
    ) {
        moveTo(6f, 6f)
        curveTo(7.1f, 6f, 8f, 6.9f, 8f, 8f)
        lineTo(8f, 10f)
        lineTo(6f, 10f)
        curveTo(4.9f, 10f, 4f, 9.1f, 4f, 8f)
        curveTo(4f, 6.9f, 4.9f, 6f, 6f, 6f)
        close()
        moveTo(18f, 6f)
        curveTo(19.1f, 6f, 20f, 6.9f, 20f, 8f)
        curveTo(20f, 9.1f, 19.1f, 10f, 18f, 10f)
        lineTo(16f, 10f)
        lineTo(16f, 8f)
        curveTo(16f, 6.9f, 16.9f, 6f, 18f, 6f)
        close()
        moveTo(6f, 18f)
        curveTo(4.9f, 18f, 4f, 17.1f, 4f, 16f)
        curveTo(4f, 14.9f, 4.9f, 14f, 6f, 14f)
        lineTo(8f, 14f)
        lineTo(8f, 16f)
        curveTo(8f, 17.1f, 7.1f, 18f, 6f, 18f)
        close()
        moveTo(18f, 18f)
        curveTo(16.9f, 18f, 16f, 17.1f, 16f, 16f)
        lineTo(16f, 14f)
        lineTo(18f, 14f)
        curveTo(19.1f, 14f, 20f, 14.9f, 20f, 16f)
        curveTo(20f, 17.1f, 19.1f, 18f, 18f, 18f)
        close()
        moveTo(8f, 10f)
        lineTo(16f, 10f)
        lineTo(16f, 14f)
        lineTo(8f, 14f)
        close()
    }.build()

    val SmallPerson: ImageVector = ImageVector.Builder(
        name = "SmallPerson",
        defaultWidth = 16.dp,
        defaultHeight = 16.dp,
        viewportWidth = 16f,
        viewportHeight = 16f
    ).path(
        stroke = SolidColor(EduPassTextSecondary),
        strokeLineWidth = 1.5f
    ) {
        moveTo(8f, 7f)
        curveTo(9.1f, 7f, 10f, 6.1f, 10f, 5f)
        curveTo(10f, 3.9f, 9.1f, 3f, 8f, 3f)
        curveTo(6.9f, 3f, 6f, 3.9f, 6f, 5f)
        curveTo(6f, 6.1f, 6.9f, 7f, 8f, 7f)
        close()
        moveTo(4f, 13f)
        curveTo(4f, 10.8f, 5.8f, 9f, 8f, 9f)
        curveTo(10.2f, 9f, 12f, 10.8f, 12f, 13f)
    }.build()

    val SmallClock: ImageVector = ImageVector.Builder(
        name = "SmallClock",
        defaultWidth = 16.dp,
        defaultHeight = 16.dp,
        viewportWidth = 16f,
        viewportHeight = 16f
    ).path(
        stroke = SolidColor(EduPassTextSecondary),
        strokeLineWidth = 1.5f
    ) {
        moveTo(8f, 14f)
        curveTo(11.31f, 14f, 14f, 11.31f, 14f, 8f)
        curveTo(14f, 4.69f, 11.31f, 2f, 8f, 2f)
        curveTo(4.69f, 2f, 2f, 4.69f, 2f, 8f)
        curveTo(2f, 11.31f, 4.69f, 14f, 8f, 14f)
        close()
        moveTo(8f, 5f)
        lineTo(8f, 8f)
        lineTo(10.5f, 9.5f)
    }.build()

    val SmallBadge: ImageVector = ImageVector.Builder(
        name = "SmallBadge",
        defaultWidth = 16.dp,
        defaultHeight = 16.dp,
        viewportWidth = 16f,
        viewportHeight = 16f
    ).path(
        stroke = SolidColor(EduPassTextSecondary),
        strokeLineWidth = 1.5f
    ) {
        moveTo(5f, 3f)
        lineTo(11f, 3f)
        lineTo(13f, 7f)
        lineTo(8f, 13f)
        lineTo(3f, 7f)
        close()
    }.build()

    val SmallPin: ImageVector = ImageVector.Builder(
        name = "SmallPin",
        defaultWidth = 16.dp,
        defaultHeight = 16.dp,
        viewportWidth = 16f,
        viewportHeight = 16f
    ).path(
        stroke = SolidColor(EduPassTextSecondary),
        strokeLineWidth = 1.5f
    ) {
        moveTo(8f, 2f)
        curveTo(5.79f, 2f, 4f, 3.79f, 4f, 6f)
        curveTo(4f, 9.25f, 8f, 14f, 8f, 14f)
        curveTo(8f, 14f, 12f, 9.25f, 12f, 6f)
        curveTo(12f, 3.79f, 10.21f, 2f, 8f, 2f)
        close()
        moveTo(8f, 7.5f)
        curveTo(7.17f, 7.5f, 6.5f, 6.83f, 6.5f, 6f)
        curveTo(6.5f, 5.17f, 7.17f, 4.5f, 8f, 4.5f)
        curveTo(8.83f, 4.5f, 9.5f, 5.17f, 9.5f, 6f)
        curveTo(9.5f, 6.83f, 8.83f, 7.5f, 8f, 7.5f)
        close()
    }.build()
}

// ---------------------------------------------------------------------------
// Preview
// ---------------------------------------------------------------------------

@Preview(
    showBackground = true,
    showSystemUi = true,
    device = "spec:width=390dp,height=844dp,dpi=420"
)
@Composable
fun DigitalPermissionPassScreenPreview() {
    EduPassTheme {
        DigitalPermissionPassScreen()
    }
}