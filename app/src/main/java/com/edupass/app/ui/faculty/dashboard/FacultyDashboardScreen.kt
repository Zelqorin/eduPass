package com.edupass.app.ui.faculty.dashboard

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edupass.app.ui.faculty.FacultyBadge
import com.edupass.app.ui.faculty.FacultyBottomNavBar
import com.edupass.app.ui.faculty.FacultyColors
import com.edupass.app.ui.faculty.FacultyDimens
import com.edupass.app.ui.faculty.FacultyIconChip
import com.edupass.app.ui.faculty.FacultyIcons
import com.edupass.app.ui.faculty.FacultySectionHeading
import com.edupass.app.ui.faculty.FacultyTopHeader
import com.edupass.app.ui.theme.EduPassTheme

private data class FacultyStat(
    val label: String,
    val value: String,
    val badge: String,
    val accent: Color,
    val chipBg: Color,
    val badgeBg: Color,
    val badgeText: Color,
    val icon: Int
)

@Composable
fun FacultyDashboardScreen(
    modifier: Modifier = Modifier,
    onNotificationClick: () -> Unit = {},
    onPendingRequestsClick: () -> Unit = {},
    onOutsideClick: () -> Unit = {},
    onReportsClick: () -> Unit = {},
    onAttentionClick: () -> Unit = {},
    onQueueClick: () -> Unit = {},
    onHomeTabClick: () -> Unit = {},
    onSectionsTabClick: () -> Unit = {},
    onLiveTabClick: () -> Unit = {},
    onProfileTabClick: () -> Unit = {}
) {
    val stats = listOf(
        FacultyStat(
            "Pending requests", "12", "Pending", FacultyColors.Blue,
            FacultyColors.AmberSoft, FacultyColors.AmberSoft, FacultyColors.Amber, 0
        ),
        FacultyStat(
            "Approved today", "18", "Approved", FacultyColors.Teal,
            FacultyColors.AquaSoft, FacultyColors.TealSoft, FacultyColors.Teal, 1
        ),
        FacultyStat(
            "Currently outside", "7", "Outside", FacultyColors.Blue,
            FacultyColors.BluePale, FacultyColors.BluePale, FacultyColors.Blue, 2
        ),
        FacultyStat(
            "Expired", "24", "Expired", FacultyColors.Blue,
            FacultyColors.NeutralSoft, FacultyColors.NeutralSoft, FacultyColors.Slate, 3
        )
    )

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = FacultyColors.Background,
        bottomBar = {
            FacultyBottomNavBar(
                selectedTabIndex = 0,
                onTabSelected = { index ->
                    when (index) {
                        0 -> onHomeTabClick()
                        1 -> onSectionsTabClick()
                        2 -> onLiveTabClick()
                        3 -> onProfileTabClick()
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .statusBarsPadding()
                .padding(horizontal = FacultyDimens.ScreenPadding)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            FacultyTopHeader(onNotificationClick = onNotificationClick)

            Spacer(modifier = Modifier.height(29.dp))

            WorkspaceHero(
                onQueueClick = onQueueClick
            )

            Spacer(modifier = Modifier.height(14.dp))

            FacultySectionHeading(
                title = "Today at a glance",
                icon = FacultyIcons.Chart(FacultyColors.Blue),
                iconBackground = FacultyColors.BluePale
            )

            Spacer(modifier = Modifier.height(11.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(stats[0], Modifier.weight(1f)) { onPendingRequestsClick() }
                StatCard(stats[1], Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(stats[2], Modifier.weight(1f)) { onOutsideClick() }
                StatCard(stats[3], Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(12.dp))

            RequestStatusCard(onSnapshotClick = onReportsClick)

            Spacer(modifier = Modifier.height(13.dp))

            FacultySectionHeading(
                title = "Needs your attention",
                icon = FacultyIcons.Alert(FacultyColors.Amber),
                iconBackground = FacultyColors.AmberSoft,
                fontSize = 20
            )
            Spacer(modifier = Modifier.height(7.dp))
            Text(
                text = "Requests awaiting faculty approval",
                color = FacultyColors.Slate,
                fontSize = 13.sp
            )
            Spacer(modifier = Modifier.height(10.dp))

            AttentionCard(onItemClick = onAttentionClick)

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun WorkspaceHero(
    onQueueClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(FacultyColors.Blue)
            .padding(start = 22.dp, end = 22.dp, top = 16.dp, bottom = 15.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            androidx.compose.foundation.Image(
                imageVector = FacultyIcons.Grid(FacultyColors.White),
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(9.dp))
            Text(
                text = "Faculty workspace",
                color = Color.White.copy(alpha = 0.82f),
                fontSize = 13.sp,
                fontWeight = FontWeight.Normal
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Good morning, Dr. Meera",
            color = Color.White,
            fontSize = 24.sp,
            lineHeight = 30.sp,
            fontWeight = FontWeight.Normal,
            letterSpacing = (-0.4).sp
        )

        Spacer(modifier = Modifier.height(7.dp))

        Text(
            text = "Computer Science & Engineering",
            color = Color.White.copy(alpha = 0.78f),
            fontSize = 14.sp,
            lineHeight = 19.sp
        )

        Spacer(modifier = Modifier.height(3.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(22.dp)
                .background(Color.White.copy(alpha = 0.07f), CircleShape)
                .border(1.dp, Color.White.copy(alpha = 0.32f), CircleShape)
                .clickable { onQueueClick() }
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            androidx.compose.foundation.Image(
                imageVector = FacultyIcons.Alert(Color.White),
                contentDescription = null,
                modifier = Modifier.size(13.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "12 requests require your decision",
                color = Color.White,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun StatCard(
    stat: FacultyStat,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .height(81.dp)
            .background(FacultyColors.White, RoundedCornerShape(14.dp))
            .border(1.dp, FacultyColors.Border, RoundedCornerShape(14.dp))
            .clickable { onClick() }
            .padding(start = 14.dp, end = 14.dp, top = 10.dp, bottom = 11.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            val icon = when (stat.icon) {
                0 -> FacultyIcons.Clock(FacultyColors.Amber)
                1 -> FacultyIcons.CheckCircle(FacultyColors.Teal)
                2 -> FacultyIcons.People(FacultyColors.Blue)
                else -> FacultyIcons.Alert(FacultyColors.Slate)
            }
            FacultyIconChip(icon = icon, background = stat.chipBg, size = 22, iconSize = 14)
            Spacer(modifier = Modifier.width(9.dp))
            Text(
                text = stat.label,
                color = FacultyColors.Slate,
                fontSize = 12.sp,
                maxLines = 1
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stat.value,
                color = stat.accent,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = (-0.5).sp
            )
            Spacer(modifier = Modifier.width(11.dp))
            FacultyBadge(
                text = stat.badge,
                background = stat.badgeBg,
                foreground = stat.badgeText,
                fontSize = 11f,
                verticalPadding = 4
            )
        }
    }
}

@Composable
private fun RequestStatusCard(onSnapshotClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(FacultyColors.White, RoundedCornerShape(16.dp))
            .border(1.dp, FacultyColors.Border, RoundedCornerShape(16.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                FacultyIconChip(
                    icon = FacultyIcons.Chart(FacultyColors.Blue),
                    background = FacultyColors.BluePale,
                    size = 22,
                    iconSize = 20
                )
                Spacer(modifier = Modifier.width(9.dp))
                Text(
                    text = "Request status",
                    color = FacultyColors.Navy,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Text(
                text = "Snapshot",
                color = FacultyColors.Slate,
                fontSize = 12.5.sp,
                modifier = Modifier.clickable { onSnapshotClick() }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(FacultyColors.Panel, RoundedCornerShape(12.dp))
                .border(1.dp, FacultyColors.Border, RoundedCornerShape(12.dp))
                .padding(horizontal = 8.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.Top
            ) {
                BarColumn("Approved", 40.dp, FacultyColors.Teal, Modifier.weight(1f))
                BarColumn("Pending", 27.dp, FacultyColors.Amber, Modifier.weight(1f))
                RejectedColumn(Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier
                    .width(100.dp)
                    .padding(top = 3.dp, end = 2.dp)
            ) {
                LegendRow("Approved", "18", FacultyColors.Teal)
                Spacer(modifier = Modifier.height(7.5.dp))
                LegendRow("Pending", "12", FacultyColors.Amber)
                Spacer(modifier = Modifier.height(7.5.dp))
                LegendRow("Rejected", "—", FacultyColors.Slate)
            }
        }
    }
}

@Composable
private fun BarColumn(
    label: String,
    height: androidx.compose.ui.unit.Dp,
    color: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .width(48.dp)
                .height(height)
                .background(color, RoundedCornerShape(7.dp))
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            color = color,
            fontSize = 12.sp,
            lineHeight = 15.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun RejectedColumn(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .height(63.dp)
            .padding(top = 33.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Rejected",
            color = FacultyColors.Slate,
            fontSize = 12.sp,
            lineHeight = 15.sp,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "—",
            color = FacultyColors.Slate,
            fontSize = 13.sp,
            lineHeight = 9.sp
        )
    }
}

@Composable
private fun LegendRow(label: String, value: String, valueColor: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = FacultyColors.Slate,
            fontSize = 12.sp,
            lineHeight = 15.sp
        )
        Text(
            text = value,
            color = valueColor,
            fontSize = 12.5.sp,
            lineHeight = 15.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun AttentionCard(onItemClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(FacultyColors.White, RoundedCornerShape(16.dp))
            .border(1.dp, FacultyColors.Border, RoundedCornerShape(16.dp))
            .padding(horizontal = 16.dp, vertical = 11.dp)
    ) {
        AttentionItem(
            name = "Arjun Kumar",
            type = "Internship",
            date = "18 Jun",
            time = "10:00–14:00",
            onClick = onItemClick
        )
        Spacer(modifier = Modifier.height(12.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(FacultyColors.BorderSoft)
        )
        Spacer(modifier = Modifier.height(12.dp))
        AttentionItem(
            name = "Kiran Nair",
            type = "Workshop",
            date = "18 Jun",
            time = "11:00–13:00",
            onClick = onItemClick
        )
    }
}

@Composable
private fun AttentionItem(
    name: String,
    type: String,
    date: String,
    time: String,
    onClick: () -> Unit
) {
    Column(modifier = Modifier.clickable { onClick() }) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FacultyIconChip(
                icon = FacultyIcons.User(FacultyColors.Blue),
                background = FacultyColors.BluePale,
                size = 24,
                iconSize = 14
            )
            Spacer(modifier = Modifier.width(11.dp))
            Text(
                text = name,
                color = FacultyColors.Navy,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            FacultyBadge(
                text = "Pending faculty review",
                background = FacultyColors.AmberSoft,
                foreground = FacultyColors.Amber,
                fontSize = 11.5f,
                horizontalPadding = 12,
                verticalPadding = 6
            )
        }
        Spacer(modifier = Modifier.height(7.dp))
        Text(text = type, color = FacultyColors.Slate, fontSize = 14.sp)
        Spacer(modifier = Modifier.height(7.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                androidx.compose.foundation.Image(
                    imageVector = FacultyIcons.Calendar(FacultyColors.Slate),
                    contentDescription = null,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(7.dp))
                Text(text = date, color = FacultyColors.Slate, fontSize = 12.sp)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                androidx.compose.foundation.Image(
                    imageVector = FacultyIcons.Clock(FacultyColors.Slate),
                    contentDescription = null,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(7.dp))
                Text(text = time, color = FacultyColors.Slate, fontSize = 12.sp)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, device = "spec:width=412dp,height=915dp,dpi=420")
@Composable
fun FacultyDashboardPreview() {
    EduPassTheme { FacultyDashboardScreen() }
}
