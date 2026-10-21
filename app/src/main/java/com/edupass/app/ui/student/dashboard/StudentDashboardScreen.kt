package com.edupass.app.ui.student.dashboard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.edupass.app.ui.theme.EduPassScreenBg
import com.edupass.app.ui.theme.EduPassTextMuted
import com.edupass.app.ui.theme.EduPassTextPrimary
import com.edupass.app.ui.theme.EduPassTextSecondary
import com.edupass.app.ui.theme.EduPassTheme
import com.edupass.app.ui.theme.EduPassWhite

// ---------------------------------------------------------------------------
// Sample Data Models
// ---------------------------------------------------------------------------

enum class RequestStatus(val label: String, val badgeBg: Color, val badgeText: Color) {
    APPROVED("Approved", Color(0xFFDCFCE7), Color(0xFF15803D)),
    PENDING("Pending", Color(0xFFFEF3C7), Color(0xFFB45309)),
    REJECTED("Rejected", Color(0xFFFEE2E2), Color(0xFFB91C1C))
}

data class RecentRequest(
    val id: String,
    val title: String,
    val timeSlot: String,
    val status: RequestStatus,
    val iconType: RequestIconType
)

enum class RequestIconType { WORKSHOP, INTERNSHIP }

data class CollegeEvent(
    val id: String,
    val title: String,
    val dateBadge: String,
    val schedule: String,
    val locationAndDetails: String
)

data class ImportantUpdate(
    val id: String,
    val title: String,
    val subtitle: String,
    val updateType: UpdateType
)

enum class UpdateType { EVENTS, ACADEMICS, PLACEMENTS, ANNOUNCEMENTS }

enum class StudentNavTab(val label: String) {
    HOME("Home"),
    REQUESTS("Requests"),
    PASSES("Passes"),
    PROFILE("Profile")
}

// ---------------------------------------------------------------------------
// Main Dashboard Screen Composable
// ---------------------------------------------------------------------------

@Composable
fun StudentDashboardScreen(
    modifier: Modifier = Modifier,
    studentName: String = "Arjun",
    studentDetails: String = "3rd Year · CSE · Section B",
    onNotificationClick: () -> Unit = {},
    onRequestPermissionClick: () -> Unit = {},
    onViewAllRequestsClick: () -> Unit = {},
    onViewAllEventsClick: () -> Unit = {},
    onViewAllUpdatesClick: () -> Unit = {},
    onRequestItemClick: (RecentRequest) -> Unit = {},
    onEventItemClick: (CollegeEvent) -> Unit = {}
) {
    var selectedTab by remember { mutableIntStateOf(StudentNavTab.HOME.ordinal) }

    val recentRequests = remember {
        listOf(
            RecentRequest(
                id = "1",
                title = "Cloud Foundations Workshop",
                timeSlot = "12 Jun 2025 · 11:00–13:00",
                status = RequestStatus.APPROVED,
                iconType = RequestIconType.WORKSHOP
            ),
            RecentRequest(
                id = "2",
                title = "Internship at Nexora Labs",
                timeSlot = "18 Jun 2025 · 10:00–14:00",
                status = RequestStatus.PENDING,
                iconType = RequestIconType.INTERNSHIP
            )
        )
    }

    val collegeEvents = remember {
        listOf(
            CollegeEvent(
                id = "1",
                title = "Tech Fest",
                dateBadge = "14 Jun",
                schedule = "14 Jun 2025 · 14:00–18:00",
                locationAndDetails = "Main Auditorium · Hackathons, demos & ideathons"
            ),
            CollegeEvent(
                id = "2",
                title = "Placement Drive",
                dateBadge = "18 Jun",
                schedule = "18 Jun 2025 · 09:30–16:30",
                locationAndDetails = "Seminar Hall A · Pre-placement talks & interviews"
            )
        )
    }

    val importantUpdates = remember {
        listOf(
            ImportantUpdate("1", "Events", "Event calendar", UpdateType.EVENTS),
            ImportantUpdate("2", "Academics", "Academic notices", UpdateType.ACADEMICS),
            ImportantUpdate("3", "Placements", "Campus recruitment", UpdateType.PLACEMENTS),
            ImportantUpdate("4", "Announcements", "Campus circulars", UpdateType.ANNOUNCEMENTS)
        )
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = EduPassScreenBg,
        bottomBar = {
            StudentBottomNavBar(
                selectedTabIndex = selectedTab,
                onTabSelected = { selectedTab = it }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .statusBarsPadding()
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            // 1. Top Bar: App Identity & Notifications
            DashboardHeader(onNotificationClick = onNotificationClick)

            Spacer(modifier = Modifier.height(20.dp))

            // 2. Greeting Section
            Text(
                text = "STUDENT HOME",
                color = EduPassTextSecondary,
                fontSize = 11.5.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.8.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Good morning, $studentName",
                color = EduPassNavy,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = (-0.5).sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = studentDetails,
                color = EduPassTextSecondary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal
            )

            Spacer(modifier = Modifier.height(18.dp))

            // 3. Active Permission Banner Card
            ActivePermissionCard(onRequestPermissionClick = onRequestPermissionClick)

            Spacer(modifier = Modifier.height(26.dp))

            // 4. Recent Requests Section
            Text(
                text = "ACTIVITY",
                color = EduPassBlue,
                fontSize = 11.5.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.8.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            SectionHeader(
                title = "Recent requests",
                onViewAllClick = onViewAllRequestsClick
            )

            Spacer(modifier = Modifier.height(12.dp))

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                recentRequests.forEach { request ->
                    RecentRequestItemView(
                        item = request,
                        onClick = { onRequestItemClick(request) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Informational notice
            InfoNoteBanner(text = "Your approved pass will appear here when issued.")

            Spacer(modifier = Modifier.height(26.dp))

            // 5. College Events Section
            SectionHeader(
                title = "College Events",
                onViewAllClick = onViewAllEventsClick
            )
            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                collegeEvents.forEach { event ->
                    CollegeEventCard(
                        event = event,
                        onClick = { onEventItemClick(event) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Carousel dots
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .background(EduPassBlue, CircleShape)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .background(EduPassCardBorder, CircleShape)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .background(EduPassCardBorder, CircleShape)
                )
            }

            Spacer(modifier = Modifier.height(26.dp))

            // 6. Important Updates Section (2-Column Grid)
            SectionHeader(
                title = "Important Updates",
                onViewAllClick = onViewAllUpdatesClick
            )
            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ImportantUpdateCard(
                    item = importantUpdates[0],
                    modifier = Modifier.weight(1f)
                )
                ImportantUpdateCard(
                    item = importantUpdates[1],
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ImportantUpdateCard(
                    item = importantUpdates[2],
                    modifier = Modifier.weight(1f)
                )
                ImportantUpdateCard(
                    item = importantUpdates[3],
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

// ---------------------------------------------------------------------------
// Header Components
// ---------------------------------------------------------------------------

@Composable
private fun DashboardHeader(onNotificationClick: () -> Unit) {
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
                imageVector = DashboardIcons.NotificationBell,
                contentDescription = "Notifications",
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
private fun SectionHeader(
    title: String,
    onViewAllClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            color = EduPassNavy,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "View all",
            color = EduPassBlue,
            fontSize = 13.5.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.clickable { onViewAllClick() }
        )
    }
}

// ---------------------------------------------------------------------------
// Active Permission Card
// ---------------------------------------------------------------------------

@Composable
private fun ActivePermissionCard(onRequestPermissionClick: () -> Unit) {
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
            .padding(20.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(7.dp)
                        .background(Color.White.copy(alpha = 0.85f), CircleShape)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "YOU'RE ON CAMPUS",
                    color = Color.White.copy(alpha = 0.85f),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.8.sp
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "No active permission",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "You have no active pass in use right now.",
                color = Color.White.copy(alpha = 0.85f),
                fontSize = 13.5.sp,
                fontWeight = FontWeight.Normal
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = onRequestPermissionClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = EduPassWhite,
                    contentColor = EduPassBlue
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        imageVector = DashboardIcons.RequestDoc,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Request permission",
                        color = EduPassBlue,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Recent Requests Components
// ---------------------------------------------------------------------------

@Composable
private fun RecentRequestItemView(
    item: RecentRequest,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(18.dp),
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
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon container
            val iconBg = if (item.iconType == RequestIconType.WORKSHOP) Color(0xFFE0F7F6) else Color(0xFFFEF3C7)
            val iconTint = if (item.iconType == RequestIconType.WORKSHOP) Color(0xFF0D9488) else Color(0xFFD97706)

            Box(
                modifier = Modifier
                    .size(42.dp)
                    .background(iconBg, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    imageVector = if (item.iconType == RequestIconType.WORKSHOP) DashboardIcons.CalendarOutline else DashboardIcons.BriefcaseOutline,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.title,
                    color = EduPassNavy,
                    fontSize = 14.5.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = item.timeSlot,
                    color = EduPassTextSecondary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Status Badge
            Box(
                modifier = Modifier
                    .background(item.status.badgeBg, RoundedCornerShape(50))
                    .padding(horizontal = 10.dp, vertical = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = item.status.label,
                    color = item.status.badgeText,
                    fontSize = 11.5.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun InfoNoteBanner(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(EduPassInfoBg, RoundedCornerShape(14.dp))
            .border(1.dp, EduPassInfoBorder, RoundedCornerShape(14.dp))
            .padding(horizontal = 14.dp, vertical = 12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(18.dp)
                    .border(1.2.dp, EduPassBlue, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "i",
                    color = EduPassBlue,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = text,
                color = EduPassTextSecondary,
                fontSize = 12.5.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

// ---------------------------------------------------------------------------
// College Events Components
// ---------------------------------------------------------------------------

@Composable
private fun CollegeEventCard(
    event: CollegeEvent,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = EduPassWhite),
        border = BorderStroke(1.dp, EduPassCardBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier
            .width(285.dp)
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            // Visual header box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(132.dp)
                    .background(Color(0xFFE9F2FE), RoundedCornerShape(14.dp))
            ) {
                // Centered circular flash icon
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .background(EduPassBlue, CircleShape)
                        .align(Alignment.Center),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        imageVector = DashboardIcons.FlashSpark,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                }

                // Date badge on bottom-left
                Text(
                    text = event.dateBadge,
                    color = EduPassBlue,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(10.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = event.title,
                color = EduPassNavy,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = event.schedule,
                color = EduPassTextSecondary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = event.locationAndDetails,
                color = EduPassTextSecondary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

// ---------------------------------------------------------------------------
// Important Updates Components
// ---------------------------------------------------------------------------

@Composable
private fun ImportantUpdateCard(
    item: ImportantUpdate,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = EduPassWhite),
        border = BorderStroke(1.dp, EduPassCardBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .background(Color(0xFFEBF3FE), RoundedCornerShape(9.dp)),
                contentAlignment = Alignment.Center
            ) {
                val icon = when (item.updateType) {
                    UpdateType.EVENTS -> DashboardIcons.CalendarOutline
                    UpdateType.ACADEMICS -> DashboardIcons.OpenBook
                    UpdateType.PLACEMENTS -> DashboardIcons.BriefcaseOutline
                    UpdateType.ANNOUNCEMENTS -> DashboardIcons.NotificationBell
                }
                Image(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(17.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = item.title,
                color = EduPassNavy,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = item.subtitle,
                color = EduPassTextSecondary,
                fontSize = 11.5.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

// ---------------------------------------------------------------------------
// Bottom Navigation Bar
// ---------------------------------------------------------------------------

@Composable
private fun StudentBottomNavBar(
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
            val tabs = StudentNavTab.entries
            tabs.forEachIndexed { index, tab ->
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
                    val icon = when (tab) {
                        StudentNavTab.HOME -> DashboardIcons.NavHome
                        StudentNavTab.REQUESTS -> DashboardIcons.NavRequests
                        StudentNavTab.PASSES -> DashboardIcons.NavPasses
                        StudentNavTab.PROFILE -> DashboardIcons.NavProfile
                    }
                    Image(
                        imageVector = icon,
                        contentDescription = tab.label,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = tab.label,
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
// Standalone Vector Icon Definitions (Independent of external icon libraries)
// ---------------------------------------------------------------------------

private object DashboardIcons {
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

    val RequestDoc: ImageVector = ImageVector.Builder(
        name = "RequestDoc",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(
        stroke = SolidColor(EduPassBlue),
        strokeLineWidth = 1.8f
    ) {
        moveTo(8f, 4f)
        lineTo(16f, 4f)
        curveTo(17.1f, 4f, 18f, 4.9f, 18f, 6f)
        lineTo(18f, 18f)
        curveTo(18f, 19.1f, 17.1f, 20f, 16f, 20f)
        lineTo(8f, 20f)
        curveTo(6.9f, 20f, 6f, 19.1f, 6f, 18f)
        lineTo(6f, 6f)
        curveTo(6f, 4.9f, 6.9f, 4f, 8f, 4f)
        close()
        moveTo(10f, 9f)
        lineTo(14f, 9f)
        moveTo(10f, 13f)
        lineTo(14f, 13f)
    }.build()

    val CalendarOutline: ImageVector = ImageVector.Builder(
        name = "CalendarOutline",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(
        stroke = SolidColor(EduPassBlue),
        strokeLineWidth = 1.8f
    ) {
        moveTo(5f, 6f)
        lineTo(19f, 6f)
        curveTo(20.1f, 6f, 21f, 6.9f, 21f, 8f)
        lineTo(21f, 19f)
        curveTo(21f, 20.1f, 20.1f, 21f, 19f, 21f)
        lineTo(5f, 21f)
        curveTo(3.9f, 21f, 3f, 20.1f, 3f, 19f)
        lineTo(3f, 8f)
        curveTo(3f, 6.9f, 3.9f, 6f, 5f, 6f)
        close()
        moveTo(3f, 10f)
        lineTo(21f, 10f)
        moveTo(8f, 3f)
        lineTo(8f, 7f)
        moveTo(16f, 3f)
        lineTo(16f, 7f)
    }.build()

    val BriefcaseOutline: ImageVector = ImageVector.Builder(
        name = "BriefcaseOutline",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(
        stroke = SolidColor(Color(0xFFD97706)),
        strokeLineWidth = 1.8f
    ) {
        moveTo(5f, 8f)
        lineTo(19f, 8f)
        curveTo(20.1f, 8f, 21f, 8.9f, 21f, 10f)
        lineTo(21f, 19f)
        curveTo(21f, 20.1f, 20.1f, 21f, 19f, 21f)
        lineTo(5f, 21f)
        curveTo(3.9f, 21f, 3f, 20.1f, 3f, 19f)
        lineTo(3f, 10f)
        curveTo(3f, 8.9f, 3.9f, 8f, 5f, 8f)
        close()
        moveTo(9f, 8f)
        lineTo(9f, 5f)
        curveTo(9f, 4.4f, 9.4f, 4f, 10f, 4f)
        lineTo(14f, 4f)
        curveTo(14.6f, 4f, 15f, 4.4f, 15f, 5f)
        lineTo(15f, 8f)
    }.build()

    val FlashSpark: ImageVector = ImageVector.Builder(
        name = "FlashSpark",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(
        fill = SolidColor(Color.White)
    ) {
        moveTo(13f, 3f)
        lineTo(6f, 13f)
        lineTo(11.5f, 13f)
        lineTo(10.5f, 21f)
        lineTo(18f, 11f)
        lineTo(12.5f, 11f)
        close()
    }.build()

    val OpenBook: ImageVector = ImageVector.Builder(
        name = "OpenBook",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(
        stroke = SolidColor(EduPassBlue),
        strokeLineWidth = 1.8f
    ) {
        moveTo(4f, 6f)
        curveTo(7f, 5f, 10f, 6f, 12f, 7f)
        curveTo(14f, 6f, 17f, 5f, 20f, 6f)
        lineTo(20f, 19f)
        curveTo(17f, 18f, 14f, 19f, 12f, 20f)
        curveTo(10f, 19f, 7f, 18f, 4f, 19f)
        close()
        moveTo(12f, 7f)
        lineTo(12f, 20f)
    }.build()

    val NavHome: ImageVector = ImageVector.Builder(
        name = "NavHome",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(
        stroke = SolidColor(EduPassBlue),
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
        stroke = SolidColor(EduPassTextMuted),
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
// Preview
// ---------------------------------------------------------------------------

@Preview(
    showBackground = true,
    showSystemUi = true,
    device = "spec:width=390dp,height=960dp,dpi=420"
)
@Composable
fun StudentDashboardPreview() {
    EduPassTheme {
        StudentDashboardScreen()
    }
}