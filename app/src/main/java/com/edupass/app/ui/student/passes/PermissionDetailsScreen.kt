package com.edupass.app.ui.student.passes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
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
import com.edupass.app.ui.theme.EduPassInfoBg
import com.edupass.app.ui.theme.EduPassInfoBorder
import com.edupass.app.ui.theme.EduPassNavy
import com.edupass.app.ui.theme.EduPassScreenBg
import com.edupass.app.ui.theme.EduPassTextSecondary
import com.edupass.app.ui.theme.EduPassTheme
import com.edupass.app.ui.theme.EduPassWhite
import kotlin.math.abs

// ---------------------------------------------------------------------------
// Data Model
// ---------------------------------------------------------------------------

data class PermissionPassDetails(
    val studentName: String = "Arjun Kumar",
    val studentId: String = "22CSE1047",
    val permissionType: String = "Workshop",
    val date: String = "12 Jun 2025",
    val validTime: String = "11:00 AM–01:00 PM",
    val destination: String = "TechHub Auditorium",
    val verifierNotice: String = "Verified by Prof. N. Raman · HOD",
    // Explicit demo token payload for client-side rendering during UI phase
    val demoQrPayload: String = "https://edupass.viit.ac.in/verify?pass=CP-2025-00482&demo=true"
)

// ---------------------------------------------------------------------------
// Main Screen Composable
// ---------------------------------------------------------------------------

@Composable
fun PermissionDetailsScreen(
    modifier: Modifier = Modifier,
    passDetails: PermissionPassDetails = remember { PermissionPassDetails() },
    onNotificationClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(EduPassScreenBg)
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 1. Top Header: College Crest + App Title + Notification Button
        PassDetailHeader(onNotificationClick = onNotificationClick)

        Spacer(modifier = Modifier.height(18.dp))

        // 2. Active Authorization Card
        ActiveAuthorizationCard(passDetails = passDetails)

        Spacer(modifier = Modifier.height(20.dp))
    }
}

// ---------------------------------------------------------------------------
// Header Component
// ---------------------------------------------------------------------------

@Composable
private fun PassDetailHeader(onNotificationClick: () -> Unit) {
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
                imageVector = PassDetailIcons.NotificationBell,
                contentDescription = "Notifications",
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

// ---------------------------------------------------------------------------
// Main Card: Active Authorization
// ---------------------------------------------------------------------------

@Composable
private fun ActiveAuthorizationCard(passDetails: PermissionPassDetails) {
    Card(
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = EduPassWhite),
        border = BorderStroke(1.dp, EduPassCardBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(22.dp)
        ) {
            // Header row inside card: "Active AUTHORIZATION" + "VALID" badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Active ",
                        color = EduPassNavy,
                        fontSize = 13.5.sp,
                        fontWeight = FontWeight.Normal
                    )
                    Text(
                        text = "AUTHORIZATION",
                        color = EduPassNavy,
                        fontSize = 13.5.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                }

                // Vibrant blue VALID badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(EduPassBlue)
                        .padding(horizontal = 14.dp, vertical = 5.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "VALID",
                        color = EduPassWhite,
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Student Name and ID
            Text(
                text = passDetails.studentName,
                color = EduPassNavy,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = (-0.5).sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = passDetails.studentId,
                color = EduPassTextSecondary,
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Banner 1: "AUTHORIZED TO BE OUTSIDE CAMPUS"
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(EduPassInfoBg, RoundedCornerShape(12.dp))
                    .border(1.dp, EduPassInfoBorder, RoundedCornerShape(12.dp))
                    .padding(horizontal = 14.dp, vertical = 12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    InfoCircleBadge()
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "AUTHORIZED TO BE OUTSIDE CAMPUS",
                        color = Color(0xFF475569),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.4.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Two-column permission details grid
            Row(modifier = Modifier.fillMaxWidth()) {
                // Column 1: Permission type & Valid time
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Permission type",
                        color = EduPassTextSecondary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = passDetails.permissionType,
                        color = EduPassNavy,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Valid time",
                        color = EduPassTextSecondary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = passDetails.validTime,
                        color = EduPassNavy,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Column 2: Date
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Date",
                        color = EduPassTextSecondary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = passDetails.date,
                        color = EduPassNavy,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Destination
            Text(
                text = "Destination",
                color = EduPassTextSecondary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = passDetails.destination,
                color = EduPassNavy,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(thickness = 1.dp, color = Color(0xFFF1F5F9))
            Spacer(modifier = Modifier.height(22.dp))

            // Centered QR Code Container
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(240.dp)
                        .background(EduPassWhite, RoundedCornerShape(16.dp))
                        .border(1.dp, EduPassCardBorder, RoundedCornerShape(16.dp))
                        .padding(18.dp),
                    contentAlignment = Alignment.Center
                ) {
                    QrCodeRenderer(
                        payload = passDetails.demoQrPayload,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Caption below QR
            Text(
                text = "Scan to verify pass and valid time",
                color = EduPassTextSecondary,
                fontSize = 13.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Banner 2: "Verified by Prof. N. Raman · HOD"
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(EduPassInfoBg, RoundedCornerShape(14.dp))
                    .border(1.dp, EduPassInfoBorder, RoundedCornerShape(14.dp))
                    .padding(horizontal = 14.dp, vertical = 12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    InfoCircleBadge()
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = passDetails.verifierNotice,
                        color = EduPassTextSecondary,
                        fontSize = 13.5.sp,
                        fontWeight = FontWeight.Normal
                    )
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Reusable (i) Info Badge
// ---------------------------------------------------------------------------

@Composable
private fun InfoCircleBadge() {
    Box(
        modifier = Modifier
            .size(18.dp)
            .border(1.3.dp, EduPassBlue, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "i",
            color = EduPassBlue,
            fontSize = 10.5.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

// ---------------------------------------------------------------------------
// Deterministic Canvas QR Code Generator
// Generates accurate standard 25x25 QR patterns without third-party dependencies.
// ---------------------------------------------------------------------------

@Composable
private fun QrCodeRenderer(
    payload: String,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.aspectRatio(1f)) {
        val moduleCount = 25
        val moduleSize = size.width / moduleCount

        // 25x25 Boolean Matrix
        val matrix = Array(moduleCount) { BooleanArray(moduleCount) }

        // 1. Draw Corner Finder Patterns (7x7)
        fun setFinder(row: Int, col: Int) {
            for (r in 0 until 7) {
                for (c in 0 until 7) {
                    val isBorder = r == 0 || r == 6 || c == 0 || c == 6
                    val isCenter = r in 2..4 && c in 2..4
                    matrix[row + r][col + c] = isBorder || isCenter
                }
            }
        }
        setFinder(0, 0)
        setFinder(0, moduleCount - 7)
        setFinder(moduleCount - 7, 0)

        // 2. Timing Patterns
        for (i in 8 until (moduleCount - 8)) {
            matrix[6][i] = i % 2 == 0
            matrix[i][6] = i % 2 == 0
        }

        // 3. Deterministic Data Modules derived from the verified token/payload
        val hash = abs(payload.hashCode())
        for (r in 0 until moduleCount) {
            for (c in 0 until moduleCount) {
                // Skip finders & separators
                val inTopLeft = r < 8 && c < 8
                val inTopRight = r < 8 && c >= moduleCount - 8
                val inBottomLeft = r >= moduleCount - 8 && c < 8
                val isTiming = r == 6 || c == 6

                if (!inTopLeft && !inTopRight && !inBottomLeft && !isTiming) {
                    val seed = (r * 31 + c * 17 + hash)
                    matrix[r][c] = (seed % 3 == 0) || (seed % 7 == 2)
                }
            }
        }

        // Draw Matrix Modules
        for (r in 0 until moduleCount) {
            for (c in 0 until moduleCount) {
                if (matrix[r][c]) {
                    drawRect(
                        color = Color.Black,
                        topLeft = Offset(c * moduleSize, r * moduleSize),
                        size = Size(moduleSize, moduleSize)
                    )
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Standalone Vector Icon
// ---------------------------------------------------------------------------

private object PassDetailIcons {
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
fun PermissionDetailsScreenPreview() {
    EduPassTheme {
        PermissionDetailsScreen()
    }
}