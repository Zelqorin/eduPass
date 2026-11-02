package com.edupass.app.ui.faculty.profile

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edupass.app.ui.faculty.FacultyBottomNavBar
import com.edupass.app.ui.faculty.FacultyColors
import com.edupass.app.ui.faculty.FacultyDimens
import com.edupass.app.ui.faculty.FacultyHeaderTrailing
import com.edupass.app.ui.faculty.FacultyIconChip
import com.edupass.app.ui.faculty.FacultyIcons
import com.edupass.app.ui.faculty.FacultyTopHeader
import com.edupass.app.ui.theme.EduPassTheme

@Composable
fun FacultyProfileScreen(
    modifier: Modifier = Modifier,
    onSignOutClick: () -> Unit = {},
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
                selectedTabIndex = 3,
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
            FacultyTopHeader(
                subtitle = null,
                trailing = FacultyHeaderTrailing.LABEL,
                trailingLabel = "Faculty"
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "FACULTY WORKSPACE",
                color = FacultyColors.Slate,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "Faculty profile",
                color = FacultyColors.Navy,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = (-0.5).sp
            )

            Spacer(modifier = Modifier.height(17.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(FacultyColors.White, RoundedCornerShape(16.dp))
                    .border(1.dp, FacultyColors.Border, RoundedCornerShape(16.dp))
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .background(FacultyColors.Blue, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "MR",
                        color = Color.White,
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Normal
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = "Dr. Meera Rao",
                        color = FacultyColors.Navy,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Faculty · Computer Science & Engineering",
                        color = FacultyColors.Slate,
                        fontSize = 14.sp,
                        lineHeight = 19.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            Text(
                text = "Work profile",
                color = FacultyColors.Navy,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(11.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(FacultyColors.White, RoundedCornerShape(16.dp))
                    .border(1.dp, FacultyColors.Border, RoundedCornerShape(16.dp))
                    .padding(vertical = 2.dp)
            ) {
                WorkProfileRow(
                    icon = FacultyIcons.IdCard(FacultyColors.Blue),
                    label = "Employee ID",
                    value = "FAC-CSE-014"
                )
                ProfileDivider()
                WorkProfileRow(
                    icon = FacultyIcons.Building(FacultyColors.Blue),
                    label = "Department",
                    value = "Computer Science & Engineering"
                )
                ProfileDivider()
                WorkProfileRow(
                    icon = FacultyIcons.Layers(FacultyColors.Blue),
                    label = "Sections",
                    value = "A–F"
                )
            }

            Spacer(modifier = Modifier.height(22.dp))

            Text(
                text = "Settings",
                color = FacultyColors.Navy,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(11.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(FacultyColors.White, RoundedCornerShape(16.dp))
                    .border(1.dp, FacultyColors.Border, RoundedCornerShape(16.dp))
                    .padding(vertical = 2.dp)
            ) {
                SettingRow(
                    icon = FacultyIcons.Bell(FacultyColors.Blue),
                    label = "Notification preferences"
                )
                ProfileDivider()
                SettingRow(
                    icon = FacultyIcons.CheckCircle(FacultyColors.Blue),
                    label = "Approval preferences"
                )
                ProfileDivider()
                SettingRow(
                    icon = FacultyIcons.Help(FacultyColors.Blue),
                    label = "Help & support"
                )
                ProfileDivider()
                SettingRow(
                    icon = FacultyIcons.Shield(FacultyColors.Blue),
                    label = "Privacy and security"
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(FacultyColors.White, RoundedCornerShape(16.dp))
                    .border(1.dp, FacultyColors.Border, RoundedCornerShape(16.dp))
                    .clickable { onSignOutClick() },
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    imageVector = FacultyIcons.Logout(FacultyColors.Red),
                    contentDescription = null,
                    modifier = Modifier.size(17.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Sign out",
                    color = FacultyColors.Red,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(26.dp))
        }
    }
}

@Composable
private fun WorkProfileRow(icon: ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 13.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FacultyIconChip(icon = icon, background = FacultyColors.BlueSoft, size = 30, iconSize = 16)
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = label,
            color = FacultyColors.Navy,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(120.dp)
        )
        Text(
            text = value,
            color = FacultyColors.Slate,
            fontSize = 15.sp,
            lineHeight = 20.sp,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun SettingRow(icon: ImageVector, label: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(horizontal = 16.dp, vertical = 13.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FacultyIconChip(icon = icon, background = FacultyColors.BlueSoft, size = 30, iconSize = 16)
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = label,
            color = FacultyColors.Navy,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        Image(
            imageVector = FacultyIcons.ChevronRight(FacultyColors.Navy),
            contentDescription = null,
            modifier = Modifier.size(17.dp)
        )
    }
}

@Composable
private fun ProfileDivider() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp)
            .height(1.dp)
            .background(FacultyColors.BorderSoft)
    )
}

@Preview(showBackground = true, showSystemUi = true, device = "spec:width=412dp,height=915dp,dpi=420")
@Composable
fun FacultyProfilePreview() {
    EduPassTheme { FacultyProfileScreen() }
}
