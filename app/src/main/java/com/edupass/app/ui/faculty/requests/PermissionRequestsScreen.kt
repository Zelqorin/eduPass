package com.edupass.app.ui.faculty.requests

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edupass.app.ui.faculty.FacultyBadge
import com.edupass.app.ui.faculty.FacultyBottomNavBar
import com.edupass.app.ui.faculty.FacultyColors
import com.edupass.app.ui.faculty.FacultyDimens
import com.edupass.app.ui.faculty.FacultyIconChip
import com.edupass.app.ui.faculty.FacultyIcons
import com.edupass.app.ui.faculty.FacultyTopHeader
import com.edupass.app.ui.theme.EduPassTheme

@Composable
fun PermissionRequestsScreen(
    modifier: Modifier = Modifier,
    onNotificationClick: () -> Unit = {},
    onRequestClick: (String) -> Unit = {},
    onHomeTabClick: () -> Unit = {},
    onSectionsTabClick: () -> Unit = {},
    onLiveTabClick: () -> Unit = {},
    onProfileTabClick: () -> Unit = {}
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = FacultyColors.Background,
        bottomBar = {
            FacultyBottomNavBar(
                selectedTabIndex = 1,
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
            FacultyTopHeader(spacedSubtitle = true, onNotificationClick = onNotificationClick)

            Spacer(modifier = Modifier.height(25.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(FacultyColors.Blue)
                    .padding(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        imageVector = FacultyIcons.Clipboard(Color.White.copy(alpha = 0.8f)),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(9.dp))
                    Text(
                        text = "APPROVAL QUEUE",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 0.8.sp
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Permission requests",
                    color = Color.White,
                    fontSize = 30.sp,
                    lineHeight = 38.sp,
                    fontWeight = FontWeight.Normal,
                    letterSpacing = (-0.4).sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    HeroChip(
                        text = "62 students",
                        icon = FacultyIcons.People(Color.White.copy(alpha = 0.8f))
                    )
                    HeroChip(
                        text = "3rd Year · Section B",
                        icon = FacultyIcons.Book(Color.White.copy(alpha = 0.8f))
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Review decisions for 3rd Year · Section B · 62 students",
                    color = Color.White.copy(alpha = 0.78f),
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(14.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Row(
                        modifier = Modifier
                            .height(44.dp)
                            .background(Color.White.copy(alpha = 0.07f), CircleShape)
                            .border(1.dp, Color.White.copy(alpha = 0.32f), CircleShape)
                            .padding(horizontal = 14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            imageVector = FacultyIcons.Clock(Color.White.copy(alpha = 0.8f)),
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "02",
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Text(
                        text = "pending approvals",
                        color = Color.White.copy(alpha = 0.78f),
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
                    .background(FacultyColors.White, RoundedCornerShape(14.dp))
                    .border(1.dp, FacultyColors.Border, RoundedCornerShape(14.dp))
                    .padding(horizontal = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    imageVector = FacultyIcons.Search(FacultyColors.Slate),
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Search by student or request",
                    color = FacultyColors.Slate,
                    fontSize = 15.sp
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                FacultyIconChip(
                    icon = FacultyIcons.Clipboard(FacultyColors.Blue),
                    background = Color.Transparent,
                    size = 24,
                    iconSize = 20
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Decision queue",
                    color = FacultyColors.Navy,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = (-0.3).sp
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Requests are grouped by approval stage · 62 students",
                color = FacultyColors.Slate,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                FilterChip("Pending", FacultyColors.AmberSoft, FacultyColors.Amber, null)
                FilterChip("Approved", FacultyColors.TealSoft, FacultyColors.Teal, null)
                FilterChip("Rejected", FacultyColors.White, FacultyColors.Red, FacultyColors.Border)
            }

            Spacer(modifier = Modifier.height(16.dp))

            RequestQueueCard(
                name = "Maya Patel",
                type = "Late arrival",
                time = "Today · 8:42 AM",
                onClick = { onRequestClick("maya") }
            )
            Spacer(modifier = Modifier.height(14.dp))
            RequestQueueCard(
                name = "Jordan Lee",
                type = "Early dismissal",
                time = "Yesterday · 2:15 PM",
                onClick = { onRequestClick("jordan") }
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun HeroChip(text: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Row(
        modifier = Modifier
            .height(31.dp)
            .background(Color.White.copy(alpha = 0.06f), CircleShape)
            .border(1.dp, Color.White.copy(alpha = 0.3f), CircleShape)
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(imageVector = icon, contentDescription = null, modifier = Modifier.size(14.dp))
        Spacer(modifier = Modifier.width(7.dp))
        Text(
            text = text,
            color = Color.White.copy(alpha = 0.8f),
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun FilterChip(
    text: String,
    background: Color,
    foreground: Color,
    borderColor: Color?
) {
    Box(
        modifier = Modifier
            .height(31.dp)
            .background(background, CircleShape)
            .then(
                if (borderColor != null) Modifier.border(1.dp, borderColor, CircleShape)
                else Modifier
            )
            .padding(horizontal = 14.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = foreground,
            fontSize = 12.5.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun RequestQueueCard(
    name: String,
    type: String,
    time: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(FacultyColors.White, RoundedCornerShape(16.dp))
            .border(1.dp, FacultyColors.Border, RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                imageVector = FacultyIcons.Clock(FacultyColors.Amber),
                contentDescription = null,
                modifier = Modifier.size(26.dp)
            )
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = name,
                    color = FacultyColors.Navy,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = type, color = FacultyColors.Navy, fontSize = 14.sp)
            }
            FacultyBadge(
                text = "Needs your approval",
                background = FacultyColors.AmberSoft,
                foreground = FacultyColors.Amber,
                fontSize = 11.5f,
                horizontalPadding = 12,
                verticalPadding = 6
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            InfoPill(
                text = time,
                icon = FacultyIcons.Clock(FacultyColors.Slate)
            )
            InfoPill(
                text = "Faculty review",
                icon = FacultyIcons.CheckCircle(FacultyColors.Slate)
            )
        }
    }
}

@Composable
private fun InfoPill(text: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Row(
        modifier = Modifier
            .height(31.dp)
            .background(FacultyColors.White, CircleShape)
            .border(1.dp, FacultyColors.Border, CircleShape)
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(imageVector = icon, contentDescription = null, modifier = Modifier.size(14.dp))
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = text,
            color = FacultyColors.Navy,
            fontSize = 12.5.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, device = "spec:width=412dp,height=915dp,dpi=420")
@Composable
fun PermissionRequestsPreview() {
    EduPassTheme { PermissionRequestsScreen() }
}
