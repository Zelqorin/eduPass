package com.edupass.app.ui.student.permission

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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
// Request Review Data Models
// ---------------------------------------------------------------------------

data class RequestReviewData(
    val studentNameWithId: String = "Arjun Kumar 22CSE1047",
    val permissionType: String = "Internship",
    val schedule: String = "18 Jun 2025·10:00 AM–02:00 PM",
    val destination: String = "Nexora Labs, Koramangala",
    val reason: String = "Summer internship orientation at Nexora Labs",
    val documentName: String? = null
)

// ---------------------------------------------------------------------------
// Main Review Screen Composable
// ---------------------------------------------------------------------------

@Composable
fun RequestReviewScreen(
    modifier: Modifier = Modifier,
    reviewData: RequestReviewData = RequestReviewData(),
    onNotificationClick: () -> Unit = {},
    onEditDetailsClick: () -> Unit = {},
    onSubmitRequestClick: () -> Unit = {}
) {
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
        // 1. Top Header: College Brand + Notification Bell
        ReviewHeader(onNotificationClick = onNotificationClick)

        Spacer(modifier = Modifier.height(24.dp))

        // 2. Screen Title & Subtitle
        Text(
            text = "NEW PERMISSION",
            color = EduPassTextSecondary,
            fontSize = 11.5.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 0.8.sp
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "Review your request",
            color = EduPassNavy,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = (-0.5).sp
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "Check the details before sending it to faculty.",
            color = EduPassTextSecondary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal
        )

        Spacer(modifier = Modifier.height(18.dp))

        // 3. Three-Segment Progress Indicator (All 3 segments active/blue on final step)
        ThreeStepProgressIndicator(completedSteps = 3)

        Spacer(modifier = Modifier.height(20.dp))

        // 4. Five-Stage Approval Workflow Card (Preserved workflow)
        CurrentStageCard()

        Spacer(modifier = Modifier.height(16.dp))

        // 5. Request Summary Details Card
        RequestDetailsSummaryCard(data = reviewData)

        Spacer(modifier = Modifier.height(20.dp))

        // 6. Review Notice Text
        Text(
            text = "You will be notified when faculty reviews this request.",
            color = EduPassTextSecondary,
            fontSize = 13.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        // 7. Action Buttons
        OutlinedButton(
            onClick = onEditDetailsClick,
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
            Text(
                text = "Edit details",
                color = EduPassBlue,
                fontSize = 14.5.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = onSubmitRequestClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = EduPassBlue,
                contentColor = EduPassWhite
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
        ) {
            Text(
                text = "Submit request",
                color = EduPassWhite,
                fontSize = 14.5.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

// ---------------------------------------------------------------------------
// Header Component
// ---------------------------------------------------------------------------

@Composable
private fun ReviewHeader(onNotificationClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.ic_edupass_logo),
                contentDescription = "VIIT Logo",
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
                imageVector = ReviewScreenIcons.NotificationBell,
                contentDescription = "Notifications",
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

// ---------------------------------------------------------------------------
// 3-Segment Progress Indicator
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
// Card: Five-Stage Approval Timeline
// ---------------------------------------------------------------------------

@Composable
private fun CurrentStageCard() {
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
            // Stage title and 1/5 counter badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "CURRENT STAGE",
                        color = EduPassTextSecondary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.8.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Request submitted",
                        color = EduPassNavy,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Box(
                    modifier = Modifier
                        .background(Color(0xFFE0F7F6), RoundedCornerShape(50))
                        .padding(horizontal = 10.dp, vertical = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "1 / 5",
                        color = Color(0xFF0D9488),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 5-Stage Step Timeline
            FiveStageTimeline()
        }
    }
}

@Composable
private fun FiveStageTimeline() {
    val stages = listOf(
        "Request\nsubmitted",
        "Faculty\napproved",
        "HOD\napproved",
        "Principal\nreview",
        "Digital\npass issued"
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            // Horizontal connecting line running through the centers of circles
            HorizontalDivider(
                color = Color(0xFFE2E8F0),
                thickness = 1.5.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            )

            // 5 Milestone Circles
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                stages.indices.forEach { index ->
                    if (index == 0) {
                        // First step: Completed with checkmark
                        Box(
                            modifier = Modifier
                                .size(22.dp)
                                .background(EduPassWhite, CircleShape)
                                .border(1.5.dp, Color(0xFF0284C7), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "✓",
                                color = Color(0xFF0284C7),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    } else {
                        // Upcoming steps: Empty circle outline
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .background(EduPassWhite, CircleShape)
                                .border(1.5.dp, Color(0xFFDCE4EE), CircleShape)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Step labels underneath
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            stages.forEachIndexed { index, title ->
                Text(
                    text = title,
                    color = if (index == 0) EduPassNavy else EduPassTextSecondary,
                    fontSize = 10.5.sp,
                    lineHeight = 13.sp,
                    fontWeight = if (index == 0) FontWeight.SemiBold else FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(62.dp)
                )
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Card: Request Summary Details
// ---------------------------------------------------------------------------

@Composable
private fun RequestDetailsSummaryCard(data: RequestReviewData) {
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
                .padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
            SummaryItemRow(label = "Student", value = data.studentNameWithId)
            HorizontalDivider(color = Color(0xFFF1F5F9), thickness = 1.dp)

            SummaryItemRow(label = "Permission", value = data.permissionType)
            HorizontalDivider(color = Color(0xFFF1F5F9), thickness = 1.dp)

            SummaryItemRow(label = "Schedule", value = data.schedule)
            HorizontalDivider(color = Color(0xFFF1F5F9), thickness = 1.dp)

            SummaryItemRow(label = "Destination", value = data.destination)
            HorizontalDivider(color = Color(0xFFF1F5F9), thickness = 1.dp)

            SummaryItemRow(label = "Reason", value = data.reason)

            if (!data.documentName.isNullOrBlank()) {
                HorizontalDivider(color = Color(0xFFF1F5F9), thickness = 1.dp)
                SummaryItemRow(label = "Document", value = data.documentName)
            }
        }
    }
}

@Composable
private fun SummaryItemRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = label,
            color = EduPassNavy,
            fontSize = 14.5.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(105.dp)
        )
        Text(
            text = value,
            color = EduPassTextSecondary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Start
        )
    }
}

// ---------------------------------------------------------------------------
// Standalone Vector Icon
// ---------------------------------------------------------------------------

private object ReviewScreenIcons {
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
fun RequestReviewScreenPreview() {
    EduPassTheme {
        RequestReviewScreen()
    }
}