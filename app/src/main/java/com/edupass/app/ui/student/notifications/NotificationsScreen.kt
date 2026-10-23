package com.edupass.app.ui.student.notifications

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.edupass.app.ui.theme.EduPassCardBorder
import com.edupass.app.ui.theme.EduPassNavy
import com.edupass.app.ui.theme.EduPassScreenBg
import com.edupass.app.ui.theme.EduPassTextSecondary
import com.edupass.app.ui.theme.EduPassTheme
import com.edupass.app.ui.theme.EduPassWhite
import com.edupass.app.ui.components.EduPassTopHeader

// ---------------------------------------------------------------------------
// Notification Data Model
// ---------------------------------------------------------------------------

data class NotificationItem(
    val id: String,
    val title: String,
    val message: String,
    val timestamp: String,
    val hasClockIcon: Boolean = false,
    val isRead: Boolean = false,
    val isToday: Boolean = true
)

// ---------------------------------------------------------------------------
// Main Screen Composable
// ---------------------------------------------------------------------------

@Composable
fun NotificationsScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onNotificationHeaderClick: () -> Unit = {},
    onNotificationItemClick: (NotificationItem) -> Unit = {}
) {
    // Local sample notifications matching Figma design
    var notifications by remember {
        mutableStateOf(
            listOf(
                NotificationItem(
                    id = "1",
                    title = "Digital pass available",
                    message = "Your pass for Cloud Foundations Workshop is ready.",
                    timestamp = "9:42 AM",
                    hasClockIcon = true,
                    isToday = true
                ),
                NotificationItem(
                    id = "2",
                    title = "Faculty Approved your request",
                    message = "Cloud Foundations Workshop · 12 Jun 2025",
                    timestamp = "9:40 AM",
                    hasClockIcon = true,
                    isToday = true
                ),
                NotificationItem(
                    id = "3",
                    title = "Permission expiring soon",
                    message = "Your internship permission ends at 2:00 PM today.",
                    timestamp = "8:15 AM",
                    hasClockIcon = true,
                    isToday = true
                ),
                NotificationItem(
                    id = "4",
                    title = "Request submitted",
                    message = "Internship at Nexora Labs was sent to faculty review.",
                    timestamp = "Yesterday",
                    hasClockIcon = false,
                    isToday = false
                ),
                NotificationItem(
                    id = "5",
                    title = "Permission expired",
                    message = "Your earlier permission has expired.",
                    timestamp = "",
                    hasClockIcon = false,
                    isToday = false
                )
            )
        )
    }

    val todayNotifications = remember(notifications) { notifications.filter { it.isToday } }
    val earlierNotifications = remember(notifications) { notifications.filter { !it.isToday } }
    val allRead = remember(notifications) { notifications.all { it.isRead } }

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
        EduPassTopHeader(
            showBackButton = true,
            onBackClick = onBackClick,
            onNotificationClick = onNotificationHeaderClick)

        Spacer(modifier = Modifier.height(20.dp))

        // 2. Blue Notifications Banner Card
        NotificationsBannerCard(
            allRead = allRead,
            onMarkAllAsRead = {
                notifications = notifications.map { it.copy(isRead = true) }
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 3. "Today" Section Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Today",
                color = EduPassNavy,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Latest activity",
                color = EduPassTextSecondary,
                fontSize = 13.sp,
                fontWeight = FontWeight.Normal
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // 4. "Today" Notification Cards
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            todayNotifications.forEach { item ->
                NotificationCard(
                    item = item,
                    onClick = { onNotificationItemClick(item) }
                )
            }
        }

        Spacer(modifier = Modifier.height(26.dp))

        // 5. "Earlier" Section Header
        Text(
            text = "Earlier",
            color = EduPassNavy,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(14.dp))

        // 6. "Earlier" Notification Cards
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            earlierNotifications.forEach { item ->
                NotificationCard(
                    item = item,
                    onClick = { onNotificationItemClick(item) }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}


// ---------------------------------------------------------------------------
// Blue Notification Banner
// ---------------------------------------------------------------------------

@Composable
private fun NotificationsBannerCard(
    allRead: Boolean,
    onMarkAllAsRead: () -> Unit
) {
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Notifications",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = (-0.5).sp
                )

                Text(
                    text = if (allRead) "All read" else "Mark all as read",
                    color = Color.White.copy(alpha = if (allRead) 0.6f else 0.85f),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        if (!allRead) onMarkAllAsRead()
                    }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Your latest EduPass updates",
                color = Color.White.copy(alpha = 0.85f),
                fontSize = 14.5.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

// ---------------------------------------------------------------------------
// Reusable Notification Card
// ---------------------------------------------------------------------------

@Composable
private fun NotificationCard(
    item: NotificationItem,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = EduPassWhite),
        border = BorderStroke(1.dp, EduPassCardBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 16.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Optional clock icon (shown for Today items per reference)
            if (item.hasClockIcon) {
                Image(
                    imageVector = NotificationIcons.Clock,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = 2.dp)
                        .size(20.dp)
                )
                Spacer(modifier = Modifier.width(14.dp))
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.title,
                    color = EduPassNavy,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = item.message,
                    color = EduPassTextSecondary,
                    fontSize = 13.5.sp,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 18.sp
                )

                if (item.timestamp.isNotBlank()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = item.timestamp,
                        color = EduPassTextSecondary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal
                    )
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Standalone Vector Icons
// ---------------------------------------------------------------------------

private object NotificationIcons {

    val Clock: ImageVector = ImageVector.Builder(
        name = "ClockOutline",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(
        stroke = SolidColor(EduPassNavy),
        strokeLineWidth = 1.8f
    ) {
        moveTo(12f, 21f)
        curveTo(16.97f, 21f, 21f, 16.97f, 21f, 12f)
        curveTo(21f, 7.03f, 16.97f, 3f, 12f, 3f)
        curveTo(7.03f, 3f, 3f, 7.03f, 3f, 12f)
        curveTo(3f, 16.97f, 7.03f, 21f, 12f, 21f)
        close()
        moveTo(12f, 7f)
        lineTo(12f, 12f)
        lineTo(15.5f, 14.5f)
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
fun NotificationsScreenPreview() {
    EduPassTheme {
        NotificationsScreen()
    }
}