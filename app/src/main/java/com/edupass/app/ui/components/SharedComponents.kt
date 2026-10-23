package com.edupass.app.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edupass.app.R
import com.edupass.app.ui.theme.*

// ---------------------------------------------------------------------------
// 1. SHARED HEADER
// ---------------------------------------------------------------------------
@Composable
fun EduPassTopHeader(
    showBackButton: Boolean = false,
    onBackClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (showBackButton) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .background(EduPassWhite, RoundedCornerShape(12.dp))
                        .border(1.dp, EduPassCardBorder, RoundedCornerShape(12.dp))
                        .clickable { onBackClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        imageVector = SharedIcons.BackArrow,
                        contentDescription = "Back",
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
            }

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
                imageVector = SharedIcons.NotificationBell,
                contentDescription = "Notifications",
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

// ---------------------------------------------------------------------------
// 2. SHARED BOTTOM NAVIGATION BAR
// ---------------------------------------------------------------------------
@Composable
fun EduPassBottomNavBar(
    selectedTabIndex: Int, // 0: Home, 1: Requests, 2: Passes, 3: Profile
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
                        0 -> if (isSelected) SharedIcons.NavHomeActive else SharedIcons.NavHomeInactive
                        1 -> if (isSelected) SharedIcons.NavRequestsActive else SharedIcons.NavRequestsInactive
                        2 -> if (isSelected) SharedIcons.NavPassesActive else SharedIcons.NavPassesInactive
                        else -> if (isSelected) SharedIcons.NavProfileActive else SharedIcons.NavProfileInactive
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
// 3. SHARED ICONS (Copy-pasted from your other files)
// ---------------------------------------------------------------------------
object SharedIcons {
    val NotificationBell: ImageVector = ImageVector.Builder(
        name = "NotificationBell", defaultWidth = 24.dp, defaultHeight = 24.dp, viewportWidth = 24f, viewportHeight = 24f
    ).path(stroke = SolidColor(EduPassNavy), strokeLineWidth = 1.8f) {
        moveTo(18f, 8f); curveTo(18f, 5.79f, 16.21f, 4f, 14f, 4f); lineTo(10f, 4f); curveTo(7.79f, 4f, 6f, 5.79f, 6f, 8f)
        curveTo(6f, 13f, 4f, 15f, 4f, 17f); lineTo(20f, 17f); curveTo(20f, 15f, 18f, 13f, 18f, 8f); close()
        moveTo(10f, 19f); curveTo(10f, 20.1f, 10.9f, 21f, 12f, 21f); curveTo(13.1f, 21f, 14f, 20.1f, 14f, 19f)
    }.build()

    val BackArrow: ImageVector = ImageVector.Builder(
        name = "BackArrow", defaultWidth = 24.dp, defaultHeight = 24.dp, viewportWidth = 24f, viewportHeight = 24f
    ).path(stroke = SolidColor(EduPassNavy), strokeLineWidth = 1.8f) {
        moveTo(15f, 18f); lineTo(9f, 12f); lineTo(15f, 6f)
    }.build()

    // Bottom Nav Icons
    val NavHomeActive = ImageVector.Builder(name="HomeActive", defaultWidth=24.dp, defaultHeight=24.dp, viewportWidth=24f, viewportHeight=24f).path(stroke=SolidColor(EduPassBlue), strokeLineWidth=1.8f) { moveTo(4f, 11f); lineTo(12f, 4f); lineTo(20f, 11f); lineTo(20f, 19f); curveTo(20f, 19.6f, 19.6f, 20f, 19f, 20f); lineTo(5f, 20f); curveTo(4.4f, 20f, 4f, 19.6f, 4f, 19f); close() }.build()
    val NavHomeInactive = ImageVector.Builder(name="HomeInactive", defaultWidth=24.dp, defaultHeight=24.dp, viewportWidth=24f, viewportHeight=24f).path(stroke=SolidColor(EduPassTextMuted), strokeLineWidth=1.8f) { moveTo(4f, 11f); lineTo(12f, 4f); lineTo(20f, 11f); lineTo(20f, 19f); curveTo(20f, 19.6f, 19.6f, 20f, 19f, 20f); lineTo(5f, 20f); curveTo(4.4f, 20f, 4f, 19.6f, 4f, 19f); close() }.build()
    
    val NavRequestsActive = ImageVector.Builder(name="ReqActive", defaultWidth=24.dp, defaultHeight=24.dp, viewportWidth=24f, viewportHeight=24f).path(stroke=SolidColor(EduPassBlue), strokeLineWidth=1.8f) { moveTo(7f, 4f); lineTo(17f, 4f); lineTo(17f, 19f); lineTo(7f, 19f); close(); moveTo(10f, 8f); lineTo(14f, 8f); moveTo(10f, 12f); lineTo(14f, 12f) }.build()
    val NavRequestsInactive = ImageVector.Builder(name="ReqInactive", defaultWidth=24.dp, defaultHeight=24.dp, viewportWidth=24f, viewportHeight=24f).path(stroke=SolidColor(EduPassTextMuted), strokeLineWidth=1.8f) { moveTo(7f, 4f); lineTo(17f, 4f); lineTo(17f, 19f); lineTo(7f, 19f); close(); moveTo(10f, 8f); lineTo(14f, 8f); moveTo(10f, 12f); lineTo(14f, 12f) }.build()

    val NavPassesActive = ImageVector.Builder(name="PassActive", defaultWidth=24.dp, defaultHeight=24.dp, viewportWidth=24f, viewportHeight=24f).path(stroke=SolidColor(EduPassBlue), strokeLineWidth=1.8f) { moveTo(4f, 4f); lineTo(10f, 4f); lineTo(10f, 10f); lineTo(4f, 10f); close(); moveTo(14f, 4f); lineTo(20f, 4f); lineTo(20f, 10f); lineTo(14f, 10f); close(); moveTo(4f, 14f); lineTo(10f, 14f); lineTo(10f, 20f); lineTo(4f, 20f); close(); moveTo(14f, 14f); lineTo(20f, 14f); lineTo(20f, 20f); lineTo(14f, 20f); close() }.build()
    val NavPassesInactive = ImageVector.Builder(name="PassInactive", defaultWidth=24.dp, defaultHeight=24.dp, viewportWidth=24f, viewportHeight=24f).path(stroke=SolidColor(EduPassTextMuted), strokeLineWidth=1.8f) { moveTo(4f, 4f); lineTo(10f, 4f); lineTo(10f, 10f); lineTo(4f, 10f); close(); moveTo(14f, 4f); lineTo(20f, 4f); lineTo(20f, 10f); lineTo(14f, 10f); close(); moveTo(4f, 14f); lineTo(10f, 14f); lineTo(10f, 20f); lineTo(4f, 20f); close(); moveTo(14f, 14f); lineTo(20f, 14f); lineTo(20f, 20f); lineTo(14f, 20f); close() }.build()

    val NavProfileActive = ImageVector.Builder(name="ProfActive", defaultWidth=24.dp, defaultHeight=24.dp, viewportWidth=24f, viewportHeight=24f).path(stroke=SolidColor(EduPassBlue), strokeLineWidth=1.8f) { moveTo(12f, 11f); curveTo(13.65f, 11f, 15f, 9.65f, 15f, 8f); curveTo(15f, 6.35f, 13.65f, 5f, 12f, 5f); curveTo(10.35f, 5f, 9f, 6.35f, 9f, 8f); curveTo(9f, 9.65f, 10.35f, 11f, 12f, 11f); close(); moveTo(5f, 19f); curveTo(5f, 15.7f, 8.1f, 14f, 12f, 14f); curveTo(15.9f, 14f, 19f, 15.7f, 19f, 19f) }.build()
    val NavProfileInactive = ImageVector.Builder(name="ProfInactive", defaultWidth=24.dp, defaultHeight=24.dp, viewportWidth=24f, viewportHeight=24f).path(stroke=SolidColor(EduPassTextMuted), strokeLineWidth=1.8f) { moveTo(12f, 11f); curveTo(13.65f, 11f, 15f, 9.65f, 15f, 8f); curveTo(15f, 6.35f, 13.65f, 5f, 12f, 5f); curveTo(10.35f, 5f, 9f, 6.35f, 9f, 8f); curveTo(9f, 9.65f, 10.35f, 11f, 12f, 11f); close(); moveTo(5f, 19f); curveTo(5f, 15.7f, 8.1f, 14f, 12f, 14f); curveTo(15.9f, 14f, 19f, 15.7f, 19f, 19f) }.build()
}