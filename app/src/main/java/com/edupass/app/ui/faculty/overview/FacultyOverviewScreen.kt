package com.edupass.app.ui.faculty.overview

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
import com.edupass.app.ui.faculty.FacultyColors
import com.edupass.app.ui.faculty.FacultyDimens
import com.edupass.app.ui.faculty.FacultyIcons
import com.edupass.app.ui.faculty.FacultyInfoBanner
import com.edupass.app.ui.faculty.FacultyTopHeader
import com.edupass.app.ui.theme.EduPassTheme

@Composable
fun FacultyOverviewScreen(
    modifier: Modifier = Modifier,
    onNotificationClick: () -> Unit = {},
    onOpenQueueClick: () -> Unit = {},
    onRequestClick: (String) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(FacultyColors.Background)
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
                .clip(RoundedCornerShape(18.dp))
                .background(FacultyColors.Blue)
                .padding(start = 22.dp, end = 22.dp, top = 18.dp, bottom = 20.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    imageVector = FacultyIcons.People(Color.White.copy(alpha = 0.8f)),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "HOD QUEUE OVERVIEW",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 0.8.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Good morning, Prof. N. Raman",
                color = Color.White,
                fontSize = 28.sp,
                lineHeight = 36.sp,
                fontWeight = FontWeight.Normal,
                letterSpacing = (-0.4).sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Computer Science & Engineering",
                color = Color.White.copy(alpha = 0.76f),
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.height(22.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    imageVector = FacultyIcons.Clipboard(Color.White.copy(alpha = 0.65f)),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Needs your approval",
                    color = Color.White.copy(alpha = 0.65f),
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(7.dp))

            Text(
                text = "6 requests need your approval",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                imageVector = FacultyIcons.Inbox(FacultyColors.Blue),
                contentDescription = null,
                modifier = Modifier.size(17.dp)
            )
            Spacer(modifier = Modifier.width(9.dp))
            Text(
                text = "APPROVAL QUEUE",
                color = FacultyColors.Blue,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.8.sp
            )
        }

        Spacer(modifier = Modifier.height(7.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Review requests",
                color = FacultyColors.Navy,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = (-0.4).sp
            )
            Box(
                modifier = Modifier
                    .background(FacultyColors.BluePale, CircleShape)
                    .padding(horizontal = 13.dp, vertical = 7.dp)
            ) {
                Text(
                    text = "2 open",
                    color = FacultyColors.Navy,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(FacultyColors.White, RoundedCornerShape(16.dp))
                .border(1.dp, FacultyColors.Border, RoundedCornerShape(16.dp))
                .padding(horizontal = 17.dp, vertical = 16.dp)
        ) {
            OverviewRequestRow(
                name = "Arjun Kumar",
                detail = "Internship · 3rd B",
                onClick = { onRequestClick("arjun") }
            )
            Spacer(modifier = Modifier.height(13.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(FacultyColors.BorderSoft)
            )
            Spacer(modifier = Modifier.height(13.dp))
            OverviewRequestRow(
                name = "Meera Iyer",
                detail = "Medical · 2nd A",
                onClick = { onRequestClick("meera") }
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        FacultyInfoBanner(
            title = "Pending Principal approvals",
            body = "Internship requests continue to Principal after HOD approval."
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .background(FacultyColors.Blue, RoundedCornerShape(12.dp))
                .clickable { onOpenQueueClick() },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                imageVector = FacultyIcons.ArrowUpRight(Color.White),
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(9.dp))
            Text(
                text = "Open HOD requests",
                color = Color.White,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(9.dp))
            Image(
                imageVector = FacultyIcons.ArrowUpRight(Color.White),
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
        }

        Spacer(modifier = Modifier.height(26.dp))
    }
}

@Composable
private fun OverviewRequestRow(
    name: String,
    detail: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            imageVector = FacultyIcons.User(FacultyColors.Slate),
            contentDescription = null,
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = name,
                color = FacultyColors.Navy,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(text = detail, color = FacultyColors.Slate, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(7.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    imageVector = FacultyIcons.Clock(FacultyColors.Amber),
                    contentDescription = null,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Needs your approval",
                    color = FacultyColors.Amber,
                    fontSize = 13.5.sp
                )
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        FacultyBadge(
            text = "Faculty Approved",
            background = FacultyColors.TealSoft,
            foreground = FacultyColors.TealDeep,
            fontSize = 12f,
            horizontalPadding = 12,
            verticalPadding = 6
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, device = "spec:width=412dp,height=915dp,dpi=420")
@Composable
fun FacultyOverviewPreview() {
    EduPassTheme { FacultyOverviewScreen() }
}
