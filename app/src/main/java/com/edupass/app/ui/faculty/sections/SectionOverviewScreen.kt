package com.edupass.app.ui.faculty.sections

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
import com.edupass.app.ui.faculty.FacultyBottomNavBar
import com.edupass.app.ui.faculty.FacultyColors
import com.edupass.app.ui.faculty.FacultyDimens
import com.edupass.app.ui.faculty.FacultyIcons
import com.edupass.app.ui.faculty.FacultyTopHeader
import com.edupass.app.ui.theme.EduPassTheme

private data class FacultySection(
    val letter: String,
    val students: Int,
    val pending: Int
)

@Composable
fun SectionOverviewScreen(
    modifier: Modifier = Modifier,
    onNotificationClick: () -> Unit = {},
    onSectionClick: (String) -> Unit = {},
    onHomeTabClick: () -> Unit = {},
    onSectionsTabClick: () -> Unit = {},
    onLiveTabClick: () -> Unit = {},
    onProfileTabClick: () -> Unit = {}
) {
    val sections = listOf(
        FacultySection("A", 58, 2),
        FacultySection("B", 62, 4),
        FacultySection("C", 60, 1),
        FacultySection("D", 59, 0),
        FacultySection("E", 61, 3),
        FacultySection("F", 57, 2)
    )

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
                    .padding(start = 20.dp, end = 20.dp, top = 14.dp, bottom = 12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Faculty",
                        color = Color.White.copy(alpha = 0.78f),
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "workspace",
                        color = Color.White.copy(alpha = 0.78f),
                        fontSize = 14.sp
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Section overview",
                    color = Color.White,
                    fontSize = 30.sp,
                    lineHeight = 38.sp,
                    fontWeight = FontWeight.Normal,
                    letterSpacing = (-0.4).sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Choose a section",
                    color = FacultyColors.Navy,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = (-0.2).sp
                )
                Box(
                    modifier = Modifier
                        .background(FacultyColors.BluePale, RoundedCornerShape(50))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "6 sections",
                        color = FacultyColors.Navy,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = "12 pending across 6 sections",
                color = FacultyColors.Slate,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            sections.forEachIndexed { index, section ->
                SectionCard(section = section, onClick = { onSectionClick(section.letter) })
                if (index != sections.lastIndex) {
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun SectionCard(section: FacultySection, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(95.dp)
            .background(FacultyColors.White, RoundedCornerShape(16.dp))
            .border(1.dp, FacultyColors.Border, RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(horizontal = 17.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(47.dp)
                .background(FacultyColors.BlueSoft, RoundedCornerShape(13.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = section.letter,
                color = FacultyColors.Navy,
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Section ${section.letter}",
                color = FacultyColors.Navy,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(2.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    imageVector = FacultyIcons.People(FacultyColors.Slate),
                    contentDescription = null,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(7.dp))
                Text(
                    text = "${section.students} students",
                    color = FacultyColors.Slate,
                    fontSize = 13.sp
                )
            }
            Spacer(modifier = Modifier.height(2.dp))

            if (section.pending > 0) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        imageVector = FacultyIcons.Clock(FacultyColors.Amber),
                        contentDescription = null,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(7.dp))
                    Text(
                        text = "${section.pending} Pending",
                        color = FacultyColors.Amber,
                        fontSize = 13.sp
                    )
                }
            } else {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        imageVector = FacultyIcons.CheckCircle(FacultyColors.Teal),
                        contentDescription = null,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(7.dp))
                    Text(
                        text = "No pending requests",
                        color = FacultyColors.Teal,
                        fontSize = 13.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(3.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    imageVector = FacultyIcons.Chart(FacultyColors.Slate),
                    contentDescription = null,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(9.dp))
                Box(
                    modifier = Modifier
                        .width(62.dp)
                        .height(5.dp)
                        .background(FacultyColors.BlueSoft, RoundedCornerShape(50))
                ) {
                    if (section.pending > 0) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(section.pending / 12f)
                                .height(5.dp)
                                .background(FacultyColors.Blue, RoundedCornerShape(50))
                        )
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = if (section.pending > 0) "${section.pending} of 12 pending" else "Queue clear",
                    color = if (section.pending > 0) FacultyColors.Slate else FacultyColors.Teal,
                    fontSize = 12.sp
                )
            }
        }

        Spacer(modifier = Modifier.width(8.dp))
        Image(
            imageVector = FacultyIcons.ChevronRight(FacultyColors.Navy),
            contentDescription = null,
            modifier = Modifier.size(18.dp)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, device = "spec:width=412dp,height=915dp,dpi=420")
@Composable
fun SectionOverviewPreview() {
    EduPassTheme { SectionOverviewScreen() }
}
