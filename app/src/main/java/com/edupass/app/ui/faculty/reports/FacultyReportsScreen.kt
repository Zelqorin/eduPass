package com.edupass.app.ui.faculty.reports

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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edupass.app.ui.faculty.FacultyColors
import com.edupass.app.ui.faculty.FacultyDimens
import com.edupass.app.ui.faculty.FacultyIconChip
import com.edupass.app.ui.faculty.FacultyIcons
import com.edupass.app.ui.faculty.FacultyTopHeader
import com.edupass.app.ui.theme.EduPassTheme

private data class FacultyReport(
    val title: String,
    val subtitle: String,
    val icon: Int
)

@Composable
fun FacultyReportsScreen(
    modifier: Modifier = Modifier,
    onNotificationClick: () -> Unit = {},
    onReportClick: (String) -> Unit = {}
) {
    val reports = listOf(
        FacultyReport("Daily Permission Report", "All permissions for a selected date", 0),
        FacultyReport("Section-wise Report", "Compare counts across sections", 1),
        FacultyReport("Student-wise Report", "Complete history for a student", 2),
        FacultyReport("Department Report", "Department-wide permission statistics", 3),
        FacultyReport("Permission Type Report", "Internship, event, medical counts", 4),
        FacultyReport(
            "Approval Report",
            "Normal permissions: Faculty → HOD → Digital pass | Internships: Faculty → HOD → Principal → Digital pass",
            5
        ),
        FacultyReport("Currently Outside Report", "Students with Active permissions", 6)
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(FacultyColors.Background)
            .verticalScroll(rememberScrollState())
            .statusBarsPadding()
            .padding(horizontal = FacultyDimens.ScreenPadding)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        FacultyTopHeader(subtitle = "Faculty · Reports", onNotificationClick = onNotificationClick)

        Spacer(modifier = Modifier.height(25.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(FacultyColors.Blue)
                .padding(horizontal = 20.dp, vertical = 17.dp)
        ) {
            Text(
                text = "Faculty workspace",
                color = Color.White.copy(alpha = 0.78f),
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(7.dp))
            Text(
                text = "Reports",
                color = Color.White,
                fontSize = 30.sp,
                lineHeight = 38.sp,
                fontWeight = FontWeight.Normal,
                letterSpacing = (-0.4).sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Department oversight for permission activity, approvals, active students, and section-level reporting.",
                color = Color.White.copy(alpha = 0.78f),
                fontSize = 14.sp,
                lineHeight = 20.sp
            )
            Spacer(modifier = Modifier.height(14.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                HeroMetric("Approved today", "18", 1, Modifier.weight(1f))
                HeroMetric("Pending", "12", 0, Modifier.weight(1f))
                HeroMetric("Active records", "3", 2, Modifier.weight(1f))
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Faculty scope",
                color = FacultyColors.Navy,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(text = "7 reports", color = FacultyColors.Slate, fontSize = 13.sp)
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            Row(
                modifier = Modifier
                    .height(44.dp)
                    .background(FacultyColors.White, RoundedCornerShape(12.dp))
                    .border(1.dp, FacultyColors.Border, RoundedCornerShape(12.dp))
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    imageVector = FacultyIcons.Calendar(FacultyColors.Navy),
                    contentDescription = null,
                    modifier = Modifier.size(15.dp)
                )
                Spacer(modifier = Modifier.width(7.dp))
                Text(
                    text = "18 Jun 2025",
                    color = FacultyColors.Navy,
                    fontSize = 13.5.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.width(6.dp))
                Image(
                    imageVector = FacultyIcons.ChevronDown(FacultyColors.Navy),
                    contentDescription = null,
                    modifier = Modifier.size(13.dp)
                )
            }
            Row(
                modifier = Modifier
                    .weight(1f)
                    .background(FacultyColors.White, RoundedCornerShape(12.dp))
                    .border(1.dp, FacultyColors.Border, RoundedCornerShape(12.dp))
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    imageVector = FacultyIcons.Layers(FacultyColors.Navy),
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(9.dp))
                Text(
                    text = "Computer Science & Engineering",
                    color = FacultyColors.Navy,
                    fontSize = 13.5.sp,
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = 18.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = "Faculty reports",
            color = FacultyColors.Navy,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(FacultyColors.White, RoundedCornerShape(16.dp))
                .border(1.dp, FacultyColors.Border, RoundedCornerShape(16.dp))
                .padding(vertical = 2.dp)
        ) {
            reports.forEachIndexed { index, report ->
                ReportRow(report = report, onClick = { onReportClick(report.title) })
                if (index != reports.lastIndex) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp)
                            .height(1.dp)
                            .background(FacultyColors.BorderSoft)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(26.dp))
    }
}

@Composable
private fun HeroMetric(label: String, value: String, iconType: Int, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .height(58.dp)
            .background(Color.White.copy(alpha = 0.06f), RoundedCornerShape(12.dp))
            .border(1.dp, Color.White.copy(alpha = 0.24f), RoundedCornerShape(12.dp))
            .padding(horizontal = 11.dp, vertical = 9.dp)
    ) {
        Text(
            text = label,
            color = Color.White.copy(alpha = 0.82f),
            fontSize = 11.5.sp,
            maxLines = 1
        )
        Spacer(modifier = Modifier.weight(1f))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = value,
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(6.dp))
            val icon = when (iconType) {
                0 -> FacultyIcons.Clock(FacultyColors.Amber)
                1 -> FacultyIcons.CheckCircle(Color(0xFF7BE0B8))
                else -> FacultyIcons.People(Color.White.copy(alpha = 0.8f))
            }
            Image(imageVector = icon, contentDescription = null, modifier = Modifier.size(15.dp))
        }
    }
}

@Composable
private fun ReportRow(report: FacultyReport, onClick: () -> Unit) {
    val icon: ImageVector = when (report.icon) {
        0 -> FacultyIcons.Calendar(FacultyColors.Navy)
        1 -> FacultyIcons.Layers(FacultyColors.Navy)
        2 -> FacultyIcons.People(FacultyColors.Navy)
        3 -> FacultyIcons.Building(FacultyColors.Navy)
        4 -> FacultyIcons.Tag(FacultyColors.Navy)
        5 -> FacultyIcons.CheckCircle(FacultyColors.Navy)
        else -> FacultyIcons.Pin(FacultyColors.Navy)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 13.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FacultyIconChip(icon = icon, background = FacultyColors.BlueSoft, size = 42, iconSize = 20)
        Spacer(modifier = Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = report.title,
                color = FacultyColors.Navy,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = report.subtitle,
                color = FacultyColors.Slate,
                fontSize = 13.5.sp,
                lineHeight = 18.sp
            )
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
fun FacultyReportsPreview() {
    EduPassTheme { FacultyReportsScreen() }
}
