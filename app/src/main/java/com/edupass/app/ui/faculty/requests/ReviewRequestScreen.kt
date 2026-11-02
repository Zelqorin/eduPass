package com.edupass.app.ui.faculty.requests

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edupass.app.ui.faculty.FacultyColors
import com.edupass.app.ui.faculty.FacultyDimens
import com.edupass.app.ui.faculty.FacultyIcons
import com.edupass.app.ui.faculty.FacultyTopHeader
import com.edupass.app.ui.theme.EduPassTheme

@Composable
fun ReviewRequestScreen(
    modifier: Modifier = Modifier,
    onNotificationClick: () -> Unit = {},
    onBackClick: () -> Unit = {}
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

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "FACULTY      REVIEW",
            color = FacultyColors.Slate,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 0.6.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                imageVector = FacultyIcons.Report(FacultyColors.Blue),
                contentDescription = null,
                modifier = Modifier.size(26.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Review requests",
                color = FacultyColors.Navy,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = (-0.5).sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        PendingPill(
            text = "Pending Principal approval",
            background = FacultyColors.BlueSoft,
            foreground = FacultyColors.Blue
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Permission request details",
            color = FacultyColors.Slate,
            fontSize = 15.sp
        )

        Spacer(modifier = Modifier.height(18.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(FacultyColors.White, RoundedCornerShape(16.dp))
                .border(1.dp, FacultyColors.Border, RoundedCornerShape(16.dp))
                .padding(18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    imageVector = FacultyIcons.User(FacultyColors.Blue),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Arjun Kumar",
                    color = FacultyColors.Navy,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Row(
                    modifier = Modifier
                        .height(34.dp)
                        .background(FacultyColors.BlueSoft, CircleShape)
                        .padding(horizontal = 13.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        imageVector = FacultyIcons.Briefcase(FacultyColors.Blue),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(7.dp))
                    Text(
                        text = "Internship",
                        color = FacultyColors.Blue,
                        fontSize = 13.5.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(7.dp))

            Text(
                text = "CP-2025-00483 · submitted today",
                color = FacultyColors.Slate,
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(FacultyColors.BlueSoft, RoundedCornerShape(14.dp))
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    imageVector = FacultyIcons.Info(FacultyColors.Blue),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(14.dp))
                Column {
                    Row {
                        Text(text = "Student", color = FacultyColors.Slate, fontSize = 14.sp)
                        Text(
                            text = "   •   Internship",
                            color = FacultyColors.Slate,
                            fontSize = 14.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                            text = "22CSE1047 · 3rd Year · Section B",
                            color = FacultyColors.Slate,
                            fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    imageVector = FacultyIcons.CheckCircle(FacultyColors.Blue),
                    contentDescription = null,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.width(9.dp))
                Text(
                    text = "Authorization timeline",
                    color = FacultyColors.Navy,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            AuthorizationTimeline()

            Spacer(modifier = Modifier.height(16.dp))

            PendingPill(
                text = "Pending Principal approval",
                background = FacultyColors.BlueSoft,
                foreground = FacultyColors.Blue
            )

            Spacer(modifier = Modifier.height(18.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(FacultyColors.BorderSoft)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    imageVector = FacultyIcons.Clipboard(FacultyColors.Blue),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(9.dp))
                Text(
                    text = "Permission details",
                    color = FacultyColors.Navy,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                DetailCell(
                    icon = FacultyIcons.Building(FacultyColors.Slate),
                    label = "Type",
                    value = "Internship",
                    modifier = Modifier.weight(1f)
                )
                DetailCell(
                    icon = FacultyIcons.Calendar(FacultyColors.Slate),
                    label = "Date",
                    value = "18 Jun 2025",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                DetailCell(
                    icon = FacultyIcons.Clock(FacultyColors.Slate),
                    label = "Time",
                    value = "10:00 AM–02:00 PM",
                    modifier = Modifier.weight(1f)
                )
                DetailCell(
                    icon = FacultyIcons.Pin(FacultyColors.Slate),
                    label = "Destination",
                    value = "Nexora Labs,\nKoramangala",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(FacultyColors.BorderSoft)
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    imageVector = FacultyIcons.Phone(FacultyColors.Blue),
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(9.dp))
                Text(
                    text = "Contact details",
                    color = FacultyColors.Slate,
                    fontSize = 12.5.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Priya Menon · +91 98765 43210",
                color = FacultyColors.Slate,
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    imageVector = FacultyIcons.Message(FacultyColors.Blue),
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(9.dp))
                Text(
                    text = "Reason",
                    color = FacultyColors.Navy,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "I am requesting permission to attend my internship at Nexora Labs and complete the required workplace orientation during the scheduled time.",
                color = FacultyColors.Slate,
                fontSize = 14.sp,
                lineHeight = 21.sp
            )
        }

        Spacer(modifier = Modifier.height(28.dp))
    }
}

@Composable
private fun PendingPill(text: String, background: Color, foreground: Color) {
    Row(
        modifier = Modifier
            .height(34.dp)
            .background(background, CircleShape)
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            imageVector = FacultyIcons.Clock(foreground),
            contentDescription = null,
            modifier = Modifier.size(15.dp)
        )
        Spacer(modifier = Modifier.width(7.dp))
        Text(
            text = text,
            color = foreground,
            fontSize = 13.5.sp,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1
        )
    }
}

@Composable
private fun DetailCell(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(imageVector = icon, contentDescription = null, modifier = Modifier.size(14.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = label, color = FacultyColors.Slate, fontSize = 13.sp)
        }
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = value, color = FacultyColors.Navy, fontSize = 16.sp, lineHeight = 22.sp)
    }
}

@Composable
private fun AuthorizationTimeline() {
    val labels = listOf(
        "Request\nsubmitted",
        "Faculty\napproved",
        "HOD\napproved",
        "Principal\nreview",
        "Digital\npass issued"
    )
    Column {
        Box(modifier = Modifier.fillMaxWidth()) {
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
            ) {
                drawLine(
                    color = Color(0xFFC9DAEE),
                    start = Offset(size.width * 0.1f, size.height / 2),
                    end = Offset(size.width * 0.9f, size.height / 2),
                    strokeWidth = 1.2.dp.toPx()
                )
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                labels.forEachIndexed { index, label ->
                    TimelineStep(index = index, label = label, modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun TimelineStep(index: Int, label: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        when {
            index < 3 -> Box(
                modifier = Modifier
                    .size(30.dp)
                    .background(FacultyColors.TealSoft, CircleShape)
                    .border(1.6.dp, FacultyColors.Blue, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    imageVector = FacultyIcons.Check(FacultyColors.TealDeep),
                    contentDescription = null,
                    modifier = Modifier.size(15.dp)
                )
            }
            index == 3 -> Box(
                modifier = Modifier
                    .size(30.dp)
                    .background(FacultyColors.White, CircleShape)
                    .border(1.8.dp, FacultyColors.Blue, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "4",
                    color = FacultyColors.Blue,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            else -> Box(
                modifier = Modifier
                    .size(30.dp)
                    .background(FacultyColors.NeutralSoft, CircleShape)
            )
        }
        Spacer(modifier = Modifier.height(7.dp))
        Text(
            text = label,
            color = FacultyColors.Navy,
            fontSize = 11.sp,
            lineHeight = 15.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, device = "spec:width=412dp,height=1000dp,dpi=420")
@Composable
fun ReviewRequestPreview() {
    EduPassTheme { ReviewRequestScreen() }
}
