package com.edupass.app.ui.student.requests

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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edupass.app.R
import com.edupass.app.ui.theme.EduPassBlue
import com.edupass.app.ui.theme.EduPassCardBorder
import com.edupass.app.ui.theme.EduPassNavy
import com.edupass.app.ui.theme.EduPassScreenBg
import com.edupass.app.ui.theme.EduPassTextMuted
import com.edupass.app.ui.theme.EduPassTextPrimary
import com.edupass.app.ui.theme.EduPassTextSecondary
import com.edupass.app.ui.theme.EduPassTheme
import com.edupass.app.ui.theme.EduPassWhite

// ---------------------------------------------------------------------------
// Request Data Models
// ---------------------------------------------------------------------------

enum class RequestFilterStatus(
    val label: String,
    val badgeBg: Color,
    val badgeText: Color
) {
    PENDING("Pending", Color(0xFFFEF3C7), Color(0xFFB45309)),
    APPROVED("Approved", Color(0xFFDCFCE7), Color(0xFF0F766E)),
    REJECTED("Rejected", Color(0xFFFEE2E2), Color(0xFFDC2626)),
    EXPIRED("Expired", Color(0xFFF1F5F9), Color(0xFF64748B))
}

data class PermissionRequestItem(
    val id: String,
    val category: String,
    val title: String,
    val destination: String,
    val dateTime: String,
    val status: RequestFilterStatus
)

// ---------------------------------------------------------------------------
// Main Screen Composable
// ---------------------------------------------------------------------------

@Composable
fun MyRequestsScreen(
    modifier: Modifier = Modifier,
    onNotificationClick: () -> Unit = {},
    onRequestClick: (PermissionRequestItem) -> Unit = {},
    onBottomNavTabSelected: (Int) -> Unit = {}
) {
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var selectedFilter by rememberSaveable { mutableStateOf<RequestFilterStatus?>(null) }
    var selectedNavTab by remember { mutableIntStateOf(1) } // 1 = Requests selected

    val sampleRequests = remember {
        listOf(
            PermissionRequestItem(
                id = "1",
                category = "INTERNSHIP",
                title = "Internship at Nexora Labs",
                destination = "Nexora Labs",
                dateTime = "18 Jun 2025 · 10:00–14:00",
                status = RequestFilterStatus.PENDING
            ),
            PermissionRequestItem(
                id = "2",
                category = "WORKSHOP",
                title = "Cloud Foundations Workshop",
                destination = "TechHub Auditorium",
                dateTime = "12 Jun 2025 · 09:00–16:00",
                status = RequestFilterStatus.APPROVED
            ),
            PermissionRequestItem(
                id = "3",
                category = "COMPANY VISIT",
                title = "Company visit · Orion Systems",
                destination = "Orion Systems",
                dateTime = "24 Jun 2025 · 14:00–17:00",
                status = RequestFilterStatus.REJECTED
            )
        )
    }

    // Dynamic filtering based on search query and selected status filter
    val filteredRequests = remember(searchQuery, selectedFilter) {
        sampleRequests.filter { item ->
            val matchesSearch = searchQuery.isBlank() ||
                item.title.contains(searchQuery, ignoreCase = true) ||
                item.category.contains(searchQuery, ignoreCase = true) ||
                item.destination.contains(searchQuery, ignoreCase = true)

            val matchesFilter = selectedFilter == null || item.status == selectedFilter
            matchesSearch && matchesFilter
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = EduPassScreenBg,
        bottomBar = {
            RequestsBottomNavBar(
                selectedTabIndex = selectedNavTab,
                onTabSelected = { index ->
                    selectedNavTab = index
                    onBottomNavTabSelected(index)
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .statusBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.Start
        ) {
            // 1. Header: Brand Logo + Notification Button
            MyRequestsHeader(onNotificationClick = onNotificationClick)

            Spacer(modifier = Modifier.height(24.dp))

            // 2. Section Subtitle and Screen Heading
            Text(
                text = "PERMISSION HISTORY",
                color = EduPassTextSecondary,
                fontSize = 11.5.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.8.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "My requests",
                color = EduPassNavy,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = (-0.5).sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Track every request from submission to pass.",
                color = EduPassTextSecondary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 3. Search Field
            SearchInputField(
                query = searchQuery,
                onQueryChange = { searchQuery = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 4. Status Filter Pills
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                RequestFilterStatus.entries.forEach { filter ->
                    val isSelected = selectedFilter == filter
                    StatusFilterPill(
                        filter = filter,
                        isSelected = isSelected,
                        onClick = {
                            // Toggle filter: clicking active filter returns to "all"
                            selectedFilter = if (isSelected) null else filter
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 5. Request Cards List
            if (filteredRequests.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No requests found matching your filter.",
                        color = EduPassTextSecondary,
                        fontSize = 14.sp
                    )
                }
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    filteredRequests.forEach { request ->
                        RequestCard(
                            request = request,
                            onClick = { onRequestClick(request) }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

// ---------------------------------------------------------------------------
// Header Component
// ---------------------------------------------------------------------------

@Composable
private fun MyRequestsHeader(onNotificationClick: () -> Unit) {
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

        Box(
            modifier = Modifier
                .size(42.dp)
                .background(EduPassWhite, RoundedCornerShape(12.dp))
                .border(1.dp, EduPassCardBorder, RoundedCornerShape(12.dp))
                .clickable { onNotificationClick() },
            contentAlignment = Alignment.Center
        ) {
            Image(
                imageVector = RequestIcons.NotificationBell,
                contentDescription = "Notifications",
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

// ---------------------------------------------------------------------------
// Search Input Field
// ---------------------------------------------------------------------------

@Composable
private fun SearchInputField(
    query: String,
    onQueryChange: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(EduPassWhite, RoundedCornerShape(12.dp))
            .border(1.dp, EduPassCardBorder, RoundedCornerShape(12.dp))
            .padding(horizontal = 14.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                imageVector = RequestIcons.Search,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Box(modifier = Modifier.weight(1f)) {
                if (query.isEmpty()) {
                    Text(
                        text = "Search requests",
                        color = EduPassTextMuted,
                        fontSize = 14.sp
                    )
                }
                BasicTextField(
                    value = query,
                    onValueChange = onQueryChange,
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(
                        color = EduPassTextPrimary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    ),
                    singleLine = true,
                    cursorBrush = SolidColor(EduPassBlue),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus() })
                )
            }

            if (query.isNotEmpty()) {
                Text(
                    text = "✕",
                    color = EduPassTextSecondary,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .clickable { onQueryChange("") }
                        .padding(horizontal = 4.dp)
                )
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Status Filter Pill
// ---------------------------------------------------------------------------

@Composable
private fun StatusFilterPill(
    filter: RequestFilterStatus,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val borderModifier = if (isSelected) {
        Modifier.border(1.5.dp, filter.badgeText, RoundedCornerShape(50))
    } else {
        Modifier
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(filter.badgeBg)
            .then(borderModifier)
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 7.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = filter.label,
            color = filter.badgeText,
            fontSize = 12.5.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold
        )
    }
}

// ---------------------------------------------------------------------------
// Request Card Item
// ---------------------------------------------------------------------------

@Composable
private fun RequestCard(
    request: PermissionRequestItem,
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            // Category label & Right-facing chevron
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = request.category,
                    color = EduPassTextSecondary,
                    fontSize = 11.5.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.8.sp
                )
                Image(
                    imageVector = RequestIcons.ChevronRight,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Main Request Title
            Text(
                text = request.title,
                color = EduPassNavy,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Destination / Organization Row
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    imageVector = RequestIcons.PersonPin,
                    contentDescription = null,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = request.destination,
                    color = EduPassTextSecondary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Date, Time & Status Pill Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        imageVector = RequestIcons.ClockCalendar,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = request.dateTime,
                        color = EduPassTextSecondary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Normal
                    )
                }

                // Status Indicator Badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(request.status.badgeBg)
                        .padding(horizontal = 12.dp, vertical = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = request.status.label,
                        color = request.status.badgeText,
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Persistent Bottom Navigation Bar (Requests Selected)
// ---------------------------------------------------------------------------

@Composable
private fun RequestsBottomNavBar(
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
            val tabs = listOf("Home", "Requests", "Passes", "Profile")
            tabs.forEachIndexed { index, title ->
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
                    val icon = when (index) {
                        0 -> RequestIcons.NavHome
                        1 -> RequestIcons.NavRequests
                        2 -> RequestIcons.NavPasses
                        else -> RequestIcons.NavProfile
                    }
                    Image(
                        imageVector = icon,
                        contentDescription = title,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = title,
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
// Standalone Vector Icons
// ---------------------------------------------------------------------------

private object RequestIcons {
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

    val Search: ImageVector = ImageVector.Builder(
        name = "Search",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(
        stroke = SolidColor(EduPassTextSecondary),
        strokeLineWidth = 1.8f
    ) {
        moveTo(10.5f, 18f)
        curveTo(14.64f, 18f, 18f, 14.64f, 18f, 10.5f)
        curveTo(18f, 6.36f, 14.64f, 3f, 10.5f, 3f)
        curveTo(6.36f, 3f, 3f, 6.36f, 3f, 10.5f)
        curveTo(3f, 14.64f, 6.36f, 18f, 10.5f, 18f)
        close()
        moveTo(16f, 16f)
        lineTo(21f, 21f)
    }.build()

    val ChevronRight: ImageVector = ImageVector.Builder(
        name = "ChevronRight",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(
        stroke = SolidColor(EduPassNavy),
        strokeLineWidth = 2f
    ) {
        moveTo(9f, 6f)
        lineTo(15f, 12f)
        lineTo(9f, 18f)
    }.build()

    val PersonPin: ImageVector = ImageVector.Builder(
        name = "PersonPin",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(
        stroke = SolidColor(EduPassTextSecondary),
        strokeLineWidth = 1.6f
    ) {
        moveTo(12f, 11f)
        curveTo(13.65f, 11f, 15f, 9.65f, 15f, 8f)
        curveTo(15f, 6.35f, 13.65f, 5f, 12f, 5f)
        curveTo(10.35f, 5f, 9f, 6.35f, 9f, 8f)
        curveTo(9f, 9.65f, 10.35f, 11f, 12f, 11f)
        close()
        moveTo(6f, 19f)
        curveTo(6f, 15.7f, 8.7f, 14f, 12f, 14f)
        curveTo(15.3f, 14f, 18f, 15.7f, 18f, 19f)
    }.build()

    val ClockCalendar: ImageVector = ImageVector.Builder(
        name = "ClockCalendar",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(
        stroke = SolidColor(EduPassTextSecondary),
        strokeLineWidth = 1.6f
    ) {
        moveTo(12f, 21f)
        curveTo(16.97f, 21f, 21f, 16.97f, 21f, 12f)
        curveTo(21f, 7.03f, 16.97f, 3f, 12f, 3f)
        curveTo(7.03f, 3f, 3f, 7.03f, 3f, 12f)
        curveTo(3f, 16.97f, 7.03f, 21f, 12f, 21f)
        close()
        moveTo(12f, 7f)
        lineTo(12f, 12f)
        lineTo(16f, 14f)
    }.build()

    val NavHome: ImageVector = ImageVector.Builder(
        name = "NavHome",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(
        stroke = SolidColor(EduPassTextMuted),
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
        stroke = SolidColor(EduPassBlue),
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
    device = "spec:width=390dp,height=844dp,dpi=420"
)
@Composable
fun MyRequestsScreenPreview() {
    EduPassTheme {
        MyRequestsScreen()
    }
}