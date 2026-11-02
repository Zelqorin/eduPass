package com.edupass.app.ui.faculty.live

import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import com.edupass.app.ui.faculty.FacultyAvatar
import com.edupass.app.ui.faculty.FacultyBadge
import com.edupass.app.ui.faculty.FacultyBottomNavBar
import com.edupass.app.ui.faculty.FacultyColors
import com.edupass.app.ui.faculty.FacultyDimens
import com.edupass.app.ui.faculty.FacultyIcons
import com.edupass.app.ui.faculty.FacultyTopHeader
import com.edupass.app.ui.theme.EduPassTheme

@Composable
fun CurrentlyOutsideScreen(
    modifier: Modifier = Modifier,
    onNotificationClick: () -> Unit = {},
    onViewPassClick: (String) -> Unit = {},
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
                selectedTabIndex = 2,
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

            LiveHero()

            Spacer(modifier = Modifier.height(18.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    imageVector = FacultyIcons.People(FacultyColors.Blue),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(9.dp))
                Text(
                    text = "Students by year",
                    color = FacultyColors.Navy,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Image(
                    imageVector = FacultyIcons.Report(FacultyColors.Teal),
                    contentDescription = null,
                    modifier = Modifier.size(15.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = "3 Active records", color = FacultyColors.Teal, fontSize = 13.sp)
            }

            Spacer(modifier = Modifier.height(11.dp))

            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(9.dp)
            ) {
                YearFilter("All", FacultyIcons.Layers(FacultyColors.Navy))
                YearFilter("1st", FacultyIcons.Hash(FacultyColors.Navy))
                YearFilter("2nd", FacultyIcons.Hash(FacultyColors.Navy))
                YearFilter("3rd", FacultyIcons.Hash(FacultyColors.Navy))
                YearFilter("4th", FacultyIcons.Hash(FacultyColors.Navy))
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "3rd year · 2 active students",
                color = FacultyColors.Slate,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(10.dp))

            LiveStudentCard(
                initials = "RK",
                name = "Ravi Kumar",
                section = "3rd · Section B",
                request = "Internship · 10:00–14:00",
                onClick = { onViewPassClick("ravi") }
            )
            Spacer(modifier = Modifier.height(12.dp))
            LiveStudentCard(
                initials = "KN",
                name = "Kiran Nair",
                section = "3rd · Section C",
                request = "Workshop · 11:00–13:00",
                onClick = { onViewPassClick("kiran") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "4th year · 1 active student",
                color = FacultyColors.Slate,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(10.dp))

            LiveStudentCard(
                initials = "AD",
                name = "Arun Das",
                section = "4th · Section A",
                request = "Company visit · 09:30–16:00",
                onClick = { onViewPassClick("arun") }
            )

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
private fun LiveHero() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(FacultyColors.Blue)
            .padding(horizontal = 18.dp, vertical = 18.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                imageVector = FacultyIcons.Eye(Color.White.copy(alpha = 0.8f)),
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "·",
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "Live monitoring",
                color = Color.White.copy(alpha = 0.78f),
                fontSize = 14.sp,
                modifier = Modifier.weight(1f)
            )
            Image(
                imageVector = FacultyIcons.Clock(Color.White.copy(alpha = 0.8f)),
                contentDescription = null,
                modifier = Modifier.size(15.dp)
            )
            Spacer(modifier = Modifier.width(7.dp))
            Text(
                text = "Updated just now",
                color = Color.White.copy(alpha = 0.78f),
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Currently outside",
            color = Color.White,
            fontSize = 30.sp,
            lineHeight = 38.sp,
            fontWeight = FontWeight.Normal,
            letterSpacing = (-0.4).sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.White.copy(alpha = 0.28f))
        )

        Spacer(modifier = Modifier.height(14.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            HeroStatTile(
                label = "Valid permissions",
                value = "7",
                icon = FacultyIcons.CheckCircle(Color.White.copy(alpha = 0.8f)),
                modifier = Modifier.weight(1f)
            )
            HeroStatTile(
                label = "Active records",
                value = "3",
                icon = FacultyIcons.People(Color.White.copy(alpha = 0.8f)),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White.copy(alpha = 0.06f), RoundedCornerShape(12.dp))
                .border(1.dp, Color.White.copy(alpha = 0.22f), RoundedCornerShape(12.dp))
                .padding(horizontal = 14.dp, vertical = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Year distribution",
                    color = Color.White.copy(alpha = 0.85f),
                    fontSize = 14.sp
                )
                Text(
                    text = "3 active",
                    color = Color.White.copy(alpha = 0.85f),
                    fontSize = 14.sp
                )
            }
            Spacer(modifier = Modifier.height(9.dp))
            YearBarRow("3rd", 2f / 3f, "2")
            Spacer(modifier = Modifier.height(8.dp))
            YearBarRow("4th", 1f / 3f, "1")
        }
    }
}

@Composable
private fun HeroStatTile(
    label: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .height(70.dp)
            .background(Color.White.copy(alpha = 0.06f), RoundedCornerShape(12.dp))
            .border(1.dp, Color.White.copy(alpha = 0.22f), RoundedCornerShape(12.dp))
            .padding(horizontal = 14.dp, vertical = 11.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(imageVector = icon, contentDescription = null, modifier = Modifier.size(14.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                    text = label,
                    color = Color.White.copy(alpha = 0.78f),
                    fontSize = 11.sp,
                    maxLines = 1
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = value,
            color = Color.White,
            fontSize = 30.sp,
            fontWeight = FontWeight.Normal,
            lineHeight = 30.sp,
            modifier = Modifier.padding(bottom = 4.dp)
        )
    }
}

@Composable
private fun YearBarRow(label: String, fraction: Float, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = label,
            color = Color.White.copy(alpha = 0.85f),
            fontSize = 13.sp,
            modifier = Modifier.width(38.dp)
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .height(5.dp)
                .background(Color.White.copy(alpha = 0.22f), RoundedCornerShape(50))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(fraction)
                    .height(5.dp)
                    .background(Color.White.copy(alpha = 0.75f), RoundedCornerShape(50))
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = value,
            color = Color.White,
            fontSize = 13.sp,
            modifier = Modifier.width(14.dp)
        )
    }
}

@Composable
private fun YearFilter(text: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Row(
        modifier = Modifier
            .height(38.dp)
            .background(FacultyColors.White, RoundedCornerShape(11.dp))
            .border(1.dp, FacultyColors.Border, RoundedCornerShape(11.dp))
            .padding(horizontal = 13.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(imageVector = icon, contentDescription = null, modifier = Modifier.size(14.dp))
        Spacer(modifier = Modifier.width(7.dp))
        Text(
            text = text,
            color = FacultyColors.Navy,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun LiveStudentCard(
    initials: String,
    name: String,
    section: String,
    request: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(FacultyColors.White, RoundedCornerShape(16.dp))
            .border(1.dp, FacultyColors.Border, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            FacultyAvatar(initials = initials, size = 44)
            Spacer(modifier = Modifier.width(13.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = name,
                    color = FacultyColors.Navy,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(3.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        imageVector = FacultyIcons.Building(FacultyColors.Slate),
                        contentDescription = null,
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = section, color = FacultyColors.Slate, fontSize = 12.5.sp)
                }
            }
            Row(
                modifier = Modifier
                    .height(34.dp)
                    .background(FacultyColors.TealSoft, CircleShape)
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    imageVector = FacultyIcons.Check(FacultyColors.TealDeep),
                    contentDescription = null,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Active",
                    color = FacultyColors.TealDeep,
                    fontSize = 12.5.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Spacer(modifier = Modifier.height(13.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                imageVector = FacultyIcons.Report(FacultyColors.Slate),
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = request,
                color = FacultyColors.Slate,
                fontSize = 13.5.sp,
                modifier = Modifier.weight(1f)
            )
            Image(
                imageVector = FacultyIcons.ArrowRight(FacultyColors.Blue),
                contentDescription = null,
                modifier = Modifier.size(15.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "View pass",
                color = FacultyColors.Blue,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(3.dp))
            Text(text = "›", color = FacultyColors.Blue, fontSize = 15.sp)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, device = "spec:width=412dp,height=915dp,dpi=420")
@Composable
fun CurrentlyOutsidePreview() {
    EduPassTheme { CurrentlyOutsideScreen() }
}
