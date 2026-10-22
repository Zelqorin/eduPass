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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.edupass.app.ui.theme.EduPassFieldBg
import com.edupass.app.ui.theme.EduPassFieldBorder
import com.edupass.app.ui.theme.EduPassNavy
import com.edupass.app.ui.theme.EduPassScreenBg
import com.edupass.app.ui.theme.EduPassTextMuted
import com.edupass.app.ui.theme.EduPassTextPrimary
import com.edupass.app.ui.theme.EduPassTextSecondary
import com.edupass.app.ui.theme.EduPassTheme
import com.edupass.app.ui.theme.EduPassWhite

// ---------------------------------------------------------------------------
// Sample Data Model
// ---------------------------------------------------------------------------

data class ApplicantIdentity(
    val studentName: String = "Arjun Kumar",
    val studentId: String = "22CSE1047",
    val department: String = "Computer Science & Engineering",
    val year: String = "3rd Year",
    val section: String = "B"
)

// ---------------------------------------------------------------------------
// Main Screen Composable
// ---------------------------------------------------------------------------

@Composable
fun ApplyPermissionScreen(
    modifier: Modifier = Modifier,
    applicant: ApplicantIdentity = remember { ApplicantIdentity() },
    onNotificationClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
    onNextClick: () -> Unit = {}
) {
    // Interactive Form State (Step 1)
    var permissionType by rememberSaveable { mutableStateOf("Internship") }
    var selectedDate by rememberSaveable { mutableStateOf("18 Jun 2025") }
    var startTime by rememberSaveable { mutableStateOf("10:00 AM") }
    var endTime by rememberSaveable { mutableStateOf("02:00 PM") }

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
        // 1. Header Bar: College Logo + App Title + Notification Button
        ApplyPermissionHeader(
            onNotificationClick = onNotificationClick,
            onBackClick = onBackClick)

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
            text = "Request permission",
            color = EduPassNavy,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = (-0.5).sp
        )

        Spacer(modifier = Modifier.height(18.dp))

        // 3. Three-Segment Progress Indicator (Step 1 of 3 active)
        ThreeStepProgressIndicator(currentStep = 1)

        Spacer(modifier = Modifier.height(20.dp))

        // 4. Progress Card
        ProgressCard()

        Spacer(modifier = Modifier.height(16.dp))

        // 5. Applicant Identity Card
        ApplicantIdentityCard(applicant = applicant)

        Spacer(modifier = Modifier.height(16.dp))

        // 6. Purpose and Schedule Form Card
        PermissionDetailsCard(
            permissionType = permissionType,
            onPermissionTypeChange = { permissionType = it },
            selectedDate = selectedDate,
            onDateChange = { selectedDate = it },
            startTime = startTime,
            onStartTimeChange = { startTime = it },
            endTime = endTime,
            onEndTimeChange = { endTime = it }
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}

// ---------------------------------------------------------------------------
// Header Component
// ---------------------------------------------------------------------------

@Composable
private fun ApplyPermissionHeader(
    onNotificationClick: () -> Unit,
    onBackClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .background(EduPassWhite, RoundedCornerShape(12.dp))
                    .border(1.dp, EduPassCardBorder, RoundedCornerShape(12.dp))
                    .clickable { onBackClick() },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    imageVector = PermissionIcons.BackArrow,
                    contentDescription = "Back",
                    modifier = Modifier.size(20.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
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

        // Notification Bell Icon Button
        Box(
            modifier = Modifier
                .size(42.dp)
                .background(EduPassWhite, RoundedCornerShape(12.dp))
                .border(1.dp, EduPassCardBorder, RoundedCornerShape(12.dp))
                .clickable { onNotificationClick() },
            contentAlignment = Alignment.Center
        ) {
            Image(
                imageVector = PermissionIcons.NotificationBell,
                contentDescription = "Notifications",
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

// ---------------------------------------------------------------------------
// 3-Segment Progress Bar
// ---------------------------------------------------------------------------

@Composable
private fun ThreeStepProgressIndicator(currentStep: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (step in 1..3) {
            val isActive = step <= currentStep
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(5.dp)
                    .clip(RoundedCornerShape(50))
                    .background(if (isActive) EduPassBlue else Color(0xFFDCE4EE))
            )
        }
    }
}

// ---------------------------------------------------------------------------
// Card 1: Progress Card
// ---------------------------------------------------------------------------

@Composable
private fun ProgressCard() {
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
                text = "PROGRESS",
                color = EduPassTextSecondary,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.8.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Request details",
                color = EduPassNavy,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "1 of 3 · Details first, review before submission",
                color = EduPassTextSecondary,
                fontSize = 13.5.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

// ---------------------------------------------------------------------------
// Card 2: Applicant Identity Card
// ---------------------------------------------------------------------------

@Composable
private fun ApplicantIdentityCard(applicant: ApplicantIdentity) {
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
                text = "APPLICANT",
                color = EduPassTextSecondary,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.8.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Student identity",
                color = EduPassNavy,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Student Name & ID Row
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Student",
                        color = EduPassTextSecondary,
                        fontSize = 12.5.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = applicant.studentName,
                        color = EduPassNavy,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Student ID",
                        color = EduPassTextSecondary,
                        fontSize = 12.5.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = applicant.studentId,
                        color = EduPassNavy,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Department Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Department",
                    color = EduPassNavy,
                    fontSize = 14.5.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = applicant.department,
                    color = EduPassTextSecondary,
                    fontSize = 13.5.sp
                )
            }

            Spacer(modifier = Modifier.height(14.dp))
            HorizontalDivider(thickness = 1.dp, color = Color(0xFFF1F5F9))
            Spacer(modifier = Modifier.height(14.dp))

            // Year & Section Row
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Year",
                        color = EduPassNavy,
                        fontSize = 14.5.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = applicant.year,
                        color = EduPassTextSecondary,
                        fontSize = 13.5.sp
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Section",
                        color = EduPassNavy,
                        fontSize = 14.5.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = applicant.section,
                        color = EduPassTextSecondary,
                        fontSize = 13.5.sp
                    )
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Card 3: Permission Details Form Card
// ---------------------------------------------------------------------------

@Composable
private fun PermissionDetailsCard(
    permissionType: String,
    onPermissionTypeChange: (String) -> Unit,
    selectedDate: String,
    onDateChange: (String) -> Unit,
    startTime: String,
    onStartTimeChange: (String) -> Unit,
    endTime: String,
    onEndTimeChange: (String) -> Unit
) {
    var dropdownExpanded by remember { mutableStateOf(false) }
    val permissionTypes = listOf(
        "Internship",
        "On-Duty (OD)",
        "Workshop / Seminar",
        "Hackathon",
        "Medical / Emergency"
    )

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
                text = "PURPOSE AND SCHEDULE",
                color = EduPassTextSecondary,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.8.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Permission details",
                color = EduPassNavy,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(18.dp))

            // 1. Permission Type Dropdown Selector
            Text(
                text = "Permission type",
                color = EduPassNavy,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(EduPassFieldBg, RoundedCornerShape(12.dp))
                        .border(1.dp, EduPassFieldBorder, RoundedCornerShape(12.dp))
                        .clickable { dropdownExpanded = true }
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = permissionType,
                                color = EduPassTextPrimary,
                                fontSize = 14.5.sp
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "v",
                                color = EduPassTextSecondary,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }

                DropdownMenu(
                    expanded = dropdownExpanded,
                    onDismissRequest = { dropdownExpanded = false },
                    modifier = Modifier.background(EduPassWhite)
                ) {
                    permissionTypes.forEach { type ->
                        DropdownMenuItem(
                            text = { Text(text = type, color = EduPassNavy, fontSize = 14.sp) },
                            onClick = {
                                onPermissionTypeChange(type)
                                dropdownExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // 2. Date Input Box
            Text(
                text = "Date",
                color = EduPassNavy,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(EduPassFieldBg, RoundedCornerShape(12.dp))
                    .border(1.dp, EduPassFieldBorder, RoundedCornerShape(12.dp))
                    .clickable { /* Hook for DatePickerDialog when hooked to ViewModel */ }
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = selectedDate,
                    color = EduPassTextPrimary,
                    fontSize = 14.5.sp
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            // 3. Start Time & End Time Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Start Time
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Start time",
                        color = EduPassNavy,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .background(EduPassFieldBg, RoundedCornerShape(12.dp))
                            .border(1.dp, EduPassFieldBorder, RoundedCornerShape(12.dp))
                            .clickable { /* Hook for TimePickerDialog */ }
                            .padding(horizontal = 16.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = startTime,
                            color = EduPassTextPrimary,
                            fontSize = 14.5.sp
                        )
                    }
                }

                // End Time
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "End time",
                        color = EduPassNavy,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .background(EduPassFieldBg, RoundedCornerShape(12.dp))
                            .border(1.dp, EduPassFieldBorder, RoundedCornerShape(12.dp))
                            .clickable { /* Hook for TimePickerDialog */ }
                            .padding(horizontal = 16.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = endTime,
                            color = EduPassTextPrimary,
                            fontSize = 14.5.sp
                        )
                    }
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Standalone Vector Icon
// ---------------------------------------------------------------------------

private object PermissionIcons {
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
    
    val BackArrow: ImageVector = ImageVector.Builder(
        name = "BackArrow",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(
        stroke = SolidColor(EduPassNavy),
        strokeLineWidth = 1.8f
    ) {
        moveTo(15f, 18f)
        lineTo(9f, 12f)
        lineTo(15f, 6f)
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
fun ApplyPermissionScreenPreview() {
    EduPassTheme {
        ApplyPermissionScreen()
    }
}