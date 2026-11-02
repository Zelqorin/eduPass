package com.edupass.app.ui.faculty

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathBuilder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edupass.app.R

// ---------------------------------------------------------------------------
// Palette sampled directly from the faculty reference screenshots.
// ---------------------------------------------------------------------------
object FacultyColors {
    val Background = Color(0xFFF4F8FC)
    val White = Color(0xFFFFFFFF)
    val Blue = Color(0xFF3B81F6)
    val BlueDeep = Color(0xFF2F6FE0)
    val Navy = Color(0xFF173764)
    val Slate = Color(0xFF63758E)
    val Muted = Color(0xFF8B9BB1)
    val Border = Color(0xFFDCE6F1)
    val BorderSoft = Color(0xFFE8EEF5)

    val BlueSoft = Color(0xFFE8F3FC)
    val BluePale = Color(0xFFEAF0FF)
    val BlueMuted = Color(0xFFA8C5EE)

    val Teal = Color(0xFF00A6B2)
    val TealDeep = Color(0xFF008692)
    val TealSoft = Color(0xFFE6F5F0)
    val AquaSoft = Color(0xFFE6F7F8)

    val Amber = Color(0xFFE9A000)
    val AmberSoft = Color(0xFFFFF3D1)

    val Red = Color(0xFFF03E43)
    val RedSoft = Color(0xFFFDE8E8)

    val NeutralSoft = Color(0xFFF1F5F9)
    val Panel = Color(0xFFF8FBFE)
}

object FacultyDimens {
    val ScreenPadding = 18.dp
}

// ---------------------------------------------------------------------------
// Typography scope
// The app-wide body style carries a fixed 24sp line height. Explicit font
// sizes in the faculty screens should use natural leading instead, so scope
// a faculty-local base style to those screens.
// ---------------------------------------------------------------------------
private val FacultyBaseTextStyle = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp,
    lineHeight = TextUnit.Unspecified,
    letterSpacing = 0.sp
)

@Composable
fun FacultyTypography(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalTextStyle provides FacultyBaseTextStyle,
        content = content
    )
}

// ---------------------------------------------------------------------------
// Header
// ---------------------------------------------------------------------------
enum class FacultyHeaderTrailing { BELL, LABEL }

@Composable
fun FacultyTopHeader(
    subtitle: String? = "Faculty Dashboard",
    spacedSubtitle: Boolean = false,
    trailing: FacultyHeaderTrailing = FacultyHeaderTrailing.BELL,
    trailingLabel: String = "Faculty",
    onNotificationClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.ic_edupass_logo),
                contentDescription = "EduPass",
                modifier = Modifier.size(36.dp)
            )
            Spacer(modifier = Modifier.width(3.dp))
            Column {
                Text(
                    text = "EduPass",
                    color = FacultyColors.Navy,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = (-0.1).sp
                )
                if (subtitle != null) {
                    if (spacedSubtitle) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Faculty",
                                color = FacultyColors.Slate,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Normal
                            )
                            Spacer(modifier = Modifier.width(14.dp))
                            Text(
                                text = subtitle,
                                color = FacultyColors.Slate,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Normal
                            )
                        }
                    } else {
                        Text(
                            text = subtitle,
                            color = FacultyColors.Slate,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }
            }
        }

        when (trailing) {
            FacultyHeaderTrailing.BELL -> Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(FacultyColors.White, RoundedCornerShape(11.dp))
                    .border(1.dp, FacultyColors.Border, RoundedCornerShape(11.dp))
                    .clickable { onNotificationClick() },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    imageVector = FacultyIcons.Bell(FacultyColors.Navy),
                    contentDescription = "Notifications",
                    modifier = Modifier.size(20.dp)
                )
            }
            FacultyHeaderTrailing.LABEL -> Text(
                text = trailingLabel,
                color = FacultyColors.Slate,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

// ---------------------------------------------------------------------------
// Bottom navigation (Home / My sections / Live / Profile)
// ---------------------------------------------------------------------------
@Composable
fun FacultyBottomNavBar(
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    Surface(
        color = FacultyColors.White,
        border = BorderStroke(1.dp, FacultyColors.Border),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(top = 10.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val tabs = listOf("Home", "My sections", "Live", "Profile")
            tabs.forEachIndexed { index, title ->
                val selected = index == selectedTabIndex
                val color = if (selected) FacultyColors.Blue else FacultyColors.Slate
                val icon = when (index) {
                    0 -> FacultyIcons.Home(color)
                    1 -> FacultyIcons.Copy(color)
                    2 -> FacultyIcons.LiveGrid(color)
                    else -> FacultyIcons.User(color)
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { onTabSelected(index) }
                        .padding(horizontal = 12.dp, vertical = 2.dp)
                ) {
                    Image(
                        imageVector = icon,
                        contentDescription = title,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = title,
                        color = color,
                        fontSize = 11.sp,
                        fontWeight = if (selected) FontWeight.Medium else FontWeight.Normal
                    )
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Small shared building blocks
// ---------------------------------------------------------------------------
@Composable
fun FacultyIconChip(
    icon: ImageVector,
    background: Color,
    size: Int = 24,
    iconSize: Int = 15
) {
    Box(
        modifier = Modifier
            .size(size.dp)
            .background(background, RoundedCornerShape((size / 2.2f).dp)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(iconSize.dp)
        )
    }
}

@Composable
fun FacultyBadge(
    text: String,
    background: Color,
    foreground: Color,
    fontSize: Float = 11f,
    horizontalPadding: Int = 11,
    verticalPadding: Int = 5
) {
    Box(
        modifier = Modifier
            .background(background, CircleShape)
            .padding(horizontal = horizontalPadding.dp, vertical = verticalPadding.dp)
    ) {
        Text(
            text = text,
            color = foreground,
            fontSize = fontSize.sp,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1
        )
    }
}

@Composable
fun FacultyOutlinePill(
    text: String,
    icon: ImageVector? = null,
    textColor: Color = FacultyColors.Navy,
    iconSize: Int = 14,
    fontSize: Int = 12,
    height: Int = 32
) {
    Row(
        modifier = Modifier
            .height(height.dp)
            .background(FacultyColors.White, CircleShape)
            .border(1.dp, FacultyColors.Border, CircleShape)
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != null) {
            Image(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(iconSize.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
        }
        Text(
            text = text,
            color = textColor,
            fontSize = fontSize.sp,
            fontWeight = FontWeight.Medium,
            maxLines = 1
        )
    }
}

@Composable
fun FacultySectionHeading(
    title: String,
    icon: ImageVector? = null,
    iconBackground: Color = Color.Transparent,
    modifier: Modifier = Modifier,
    fontSize: Int = 20
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != null) {
            FacultyIconChip(icon = icon, background = iconBackground, size = 22, iconSize = 20)
            Spacer(modifier = Modifier.width(10.dp))
        }
        Text(
            text = title,
            color = FacultyColors.Navy,
            fontSize = fontSize.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = (-0.2).sp
        )
    }
}

@Composable
fun FacultyInfoBanner(
    title: String? = null,
    body: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(FacultyColors.BlueSoft, RoundedCornerShape(14.dp))
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.Top
    ) {
        Image(
            imageVector = FacultyIcons.Info(FacultyColors.Blue),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 1.dp)
                .size(18.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            if (title != null) {
                Text(
                    text = title,
                    color = FacultyColors.Navy,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(2.dp))
            }
            Text(
                text = body,
                color = FacultyColors.Slate,
                fontSize = 13.sp,
                lineHeight = 19.sp
            )
        }
    }
}

@Composable
fun FacultyWhiteCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(FacultyColors.White, RoundedCornerShape(16.dp))
            .border(1.dp, FacultyColors.Border, RoundedCornerShape(16.dp)),
        content = content
    )
}

// ---------------------------------------------------------------------------
// Icon set (hand-built vectors so no external icon dependency is required)
// ---------------------------------------------------------------------------
object FacultyIcons {

    private fun line(
        name: String,
        color: Color,
        stroke: Float = 1.8f,
        fill: Color? = null,
        builder: PathBuilder.() -> Unit
    ): ImageVector {
        val vector = ImageVector.Builder(
            name = name,
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        )
        if (fill != null) {
            vector.path(fill = SolidColor(fill), pathBuilder = builder)
        } else {
            vector.path(
                stroke = SolidColor(color),
                strokeLineWidth = stroke,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
                pathBuilder = builder
            )
        }
        return vector.build()
    }

    fun Bell(color: Color = FacultyColors.Navy) = line("Bell", color) {
        moveTo(18f, 8.6f); curveTo(18f, 6.1f, 15.9f, 4f, 13.4f, 4f)
        lineTo(10.6f, 4f); curveTo(8.1f, 4f, 6f, 6.1f, 6f, 8.6f)
        curveTo(6f, 13.2f, 4.5f, 15.2f, 4.5f, 16.6f); lineTo(19.5f, 16.6f)
        curveTo(19.5f, 15.2f, 18f, 13.2f, 18f, 8.6f); close()
        moveTo(9.8f, 19.2f); curveTo(10.1f, 20.2f, 10.9f, 20.9f, 12f, 20.9f)
        curveTo(13.1f, 20.9f, 13.9f, 20.2f, 14.2f, 19.2f)
    }

    fun Grid(color: Color = FacultyColors.White) = line("Grid", color, 1.6f) {
        moveTo(4f, 4f); lineTo(10f, 4f); lineTo(10f, 10f); lineTo(4f, 10f); close()
        moveTo(14f, 4f); lineTo(20f, 4f); lineTo(20f, 10f); lineTo(14f, 10f); close()
        moveTo(4f, 14f); lineTo(10f, 14f); lineTo(10f, 20f); lineTo(4f, 20f); close()
        moveTo(14f, 14f); lineTo(20f, 14f); lineTo(20f, 20f); lineTo(14f, 20f); close()
    }

    fun Chart(color: Color = FacultyColors.Blue) = line("Chart", color, 1.7f) {
        moveTo(4.5f, 20f); lineTo(4.5f, 12f)
        moveTo(12f, 20f); lineTo(12f, 4f)
        moveTo(19.5f, 20f); lineTo(19.5f, 8.5f)
    }

    fun Clock(color: Color = FacultyColors.Amber, stroke: Float = 1.8f) = line("Clock", color, stroke) {
        moveTo(21f, 12f)
        curveTo(21f, 16.97f, 16.97f, 21f, 12f, 21f)
        curveTo(7.03f, 21f, 3f, 16.97f, 3f, 12f)
        curveTo(3f, 7.03f, 7.03f, 3f, 12f, 3f)
        curveTo(16.97f, 3f, 21f, 7.03f, 21f, 12f)
        close()
        moveTo(12f, 7f); lineTo(12f, 12f); lineTo(15.5f, 14f)
    }

    fun CheckCircle(color: Color = FacultyColors.Teal) = line("CheckCircle", color, 1.8f) {
        moveTo(21f, 12f)
        curveTo(21f, 16.97f, 16.97f, 21f, 12f, 21f)
        curveTo(7.03f, 21f, 3f, 16.97f, 3f, 12f)
        curveTo(3f, 7.03f, 7.03f, 3f, 12f, 3f)
        curveTo(16.97f, 3f, 21f, 7.03f, 21f, 12f)
        close()
        moveTo(8.2f, 12.1f); lineTo(11f, 14.9f); lineTo(16.2f, 9.5f)
    }

    fun Check(color: Color = FacultyColors.Teal) = line("Check", color, 2.2f) {
        moveTo(5f, 12.5f); lineTo(10f, 17.5f); lineTo(19f, 6.5f)
    }

    fun People(color: Color = FacultyColors.Blue) = line("People", color, 1.7f) {
        moveTo(9f, 11f)
        curveTo(10.66f, 11f, 12f, 9.66f, 12f, 8f)
        curveTo(12f, 6.34f, 10.66f, 5f, 9f, 5f)
        curveTo(7.34f, 5f, 6f, 6.34f, 6f, 8f)
        curveTo(6f, 9.66f, 7.34f, 11f, 9f, 11f)
        close()
        moveTo(17f, 11f)
        curveTo(18.1f, 11f, 19f, 10.1f, 19f, 9f)
        curveTo(19f, 7.9f, 18.1f, 7f, 17f, 7f)
        moveTo(3.5f, 19f)
        curveTo(3.5f, 15.9f, 6f, 14f, 9f, 14f)
        curveTo(12f, 14f, 14.5f, 15.9f, 14.5f, 19f)
        moveTo(16.5f, 14.3f)
        curveTo(19f, 14.7f, 20.5f, 16.4f, 20.5f, 18.6f)
    }

    fun User(color: Color = FacultyColors.Navy) = line("User", color, 1.8f) {
        moveTo(12f, 11.2f)
        curveTo(13.9f, 11.2f, 15.4f, 9.7f, 15.4f, 7.8f)
        curveTo(15.4f, 5.9f, 13.9f, 4.4f, 12f, 4.4f)
        curveTo(10.1f, 4.4f, 8.6f, 5.9f, 8.6f, 7.8f)
        curveTo(8.6f, 9.7f, 10.1f, 11.2f, 12f, 11.2f)
        close()
        moveTo(4.8f, 20f)
        curveTo(4.8f, 16.2f, 8f, 14f, 12f, 14f)
        curveTo(16f, 14f, 19.2f, 16.2f, 19.2f, 20f)
    }

    fun Alert(color: Color = FacultyColors.Amber) = line("Alert", color, 1.8f) {
        moveTo(21f, 12f)
        curveTo(21f, 16.97f, 16.97f, 21f, 12f, 21f)
        curveTo(7.03f, 21f, 3f, 16.97f, 3f, 12f)
        curveTo(3f, 7.03f, 7.03f, 3f, 12f, 3f)
        curveTo(16.97f, 3f, 21f, 7.03f, 21f, 12f)
        close()
        moveTo(12f, 7.5f); lineTo(12f, 13f)
        moveTo(12f, 16.3f); lineTo(12f, 16.4f)
    }

    fun Info(color: Color = FacultyColors.Blue) = line("Info", color, 1.8f) {
        moveTo(21f, 12f)
        curveTo(21f, 16.97f, 16.97f, 21f, 12f, 21f)
        curveTo(7.03f, 21f, 3f, 16.97f, 3f, 12f)
        curveTo(3f, 7.03f, 7.03f, 3f, 12f, 3f)
        curveTo(16.97f, 3f, 21f, 7.03f, 21f, 12f)
        close()
        moveTo(12f, 10.5f); lineTo(12f, 16.5f)
        moveTo(12f, 7.6f); lineTo(12f, 7.7f)
    }

    fun Clipboard(color: Color = FacultyColors.Blue) = line("Clipboard", color, 1.7f) {
        moveTo(9f, 4f); lineTo(15f, 4f); lineTo(15f, 7f); lineTo(9f, 7f); close()
        moveTo(7f, 5.2f)
        curveTo(5.9f, 5.6f, 5f, 6.7f, 5f, 8f); lineTo(5f, 19f)
        curveTo(5f, 20.1f, 5.9f, 21f, 7f, 21f); lineTo(17f, 21f)
        curveTo(18.1f, 21f, 19f, 20.1f, 19f, 19f); lineTo(19f, 8f)
        curveTo(19f, 6.7f, 18.1f, 5.6f, 17f, 5.2f)
        moveTo(8.5f, 12.5f); lineTo(10.5f, 14.5f); lineTo(15f, 9.8f)
    }

    fun Book(color: Color = FacultyColors.White) = line("Book", color, 1.8f) {
        moveTo(3.5f, 5.5f)
        curveTo(6.5f, 4.5f, 9.5f, 5.5f, 12f, 7f)
        curveTo(14.5f, 5.5f, 17.5f, 4.5f, 20.5f, 5.5f)
        lineTo(20.5f, 18.5f)
        curveTo(17.5f, 17.5f, 14.5f, 18.5f, 12f, 20f)
        curveTo(9.5f, 18.5f, 6.5f, 17.5f, 3.5f, 18.5f)
        close()
        moveTo(12f, 7f); lineTo(12f, 20f)
    }

    fun Search(color: Color = FacultyColors.Slate) = line("Search", color, 1.9f) {
        moveTo(18f, 11f)
        curveTo(18f, 14.87f, 14.87f, 18f, 11f, 18f)
        curveTo(7.13f, 18f, 4f, 14.87f, 4f, 11f)
        curveTo(4f, 7.13f, 7.13f, 4f, 11f, 4f)
        curveTo(14.87f, 4f, 18f, 7.13f, 18f, 11f)
        close()
        moveTo(16.2f, 16.2f); lineTo(20.5f, 20.5f)
    }

    fun Briefcase(color: Color = FacultyColors.Blue) = line("Briefcase", color, 1.7f) {
        moveTo(4f, 8f); lineTo(20f, 8f)
        curveTo(20.55f, 8f, 21f, 8.45f, 21f, 9f)
        lineTo(21f, 19f)
        curveTo(21f, 19.55f, 20.55f, 20f, 20f, 20f)
        lineTo(4f, 20f)
        curveTo(3.45f, 20f, 3f, 19.55f, 3f, 19f)
        lineTo(3f, 9f)
        curveTo(3f, 8.45f, 3.45f, 8f, 4f, 8f); close()
        moveTo(8.5f, 8f); lineTo(8.5f, 5.5f)
        curveTo(8.5f, 4.95f, 8.95f, 4.5f, 9.5f, 4.5f)
        lineTo(14.5f, 4.5f)
        curveTo(15.05f, 4.5f, 15.5f, 4.95f, 15.5f, 5.5f)
        lineTo(15.5f, 8f)
        moveTo(3f, 13f); lineTo(21f, 13f)
    }

    fun Calendar(color: Color = FacultyColors.Slate) = line("Calendar", color, 1.7f) {
        moveTo(4f, 6f); lineTo(20f, 6f)
        curveTo(20.55f, 6f, 21f, 6.45f, 21f, 7f)
        lineTo(21f, 19f)
        curveTo(21f, 19.55f, 20.55f, 20f, 20f, 20f)
        lineTo(4f, 20f)
        curveTo(3.45f, 20f, 3f, 19.55f, 3f, 19f)
        lineTo(3f, 7f)
        curveTo(3f, 6.45f, 3.45f, 6f, 4f, 6f); close()
        moveTo(3f, 10f); lineTo(21f, 10f)
        moveTo(8f, 3.5f); lineTo(8f, 7.2f)
        moveTo(16f, 3.5f); lineTo(16f, 7.2f)
    }

    fun Pin(color: Color = FacultyColors.Slate) = line("Pin", color, 1.8f) {
        moveTo(12f, 21f)
        curveTo(12f, 21f, 19f, 14.6f, 19f, 9.6f)
        curveTo(19f, 5.7f, 15.9f, 3f, 12f, 3f)
        curveTo(8.1f, 3f, 5f, 5.7f, 5f, 9.6f)
        curveTo(5f, 14.6f, 12f, 21f, 12f, 21f); close()
        moveTo(12f, 12f)
        curveTo(13.1f, 12f, 14f, 11.1f, 14f, 10f)
        curveTo(14f, 8.9f, 13.1f, 8f, 12f, 8f)
        curveTo(10.9f, 8f, 10f, 8.9f, 10f, 10f)
        curveTo(10f, 11.1f, 10.9f, 12f, 12f, 12f); close()
    }

    fun Phone(color: Color = FacultyColors.Blue) = line("Phone", color, 1.8f) {
        moveTo(6.6f, 3.8f)
        curveTo(7.2f, 3.3f, 8.1f, 3.4f, 8.6f, 4f)
        lineTo(10.4f, 6.4f)
        curveTo(10.8f, 7f, 10.8f, 7.7f, 10.3f, 8.2f)
        lineTo(9.4f, 9.1f)
        curveTo(9f, 9.5f, 9f, 10.1f, 9.3f, 10.6f)
        curveTo(10.2f, 12.2f, 11.8f, 13.7f, 13.4f, 14.6f)
        curveTo(13.9f, 14.9f, 14.5f, 14.9f, 14.9f, 14.5f)
        lineTo(15.8f, 13.6f)
        curveTo(16.3f, 13.1f, 17f, 13.1f, 17.6f, 13.5f)
        lineTo(20f, 15.3f)
        curveTo(20.6f, 15.8f, 20.7f, 16.7f, 20.2f, 17.3f)
        lineTo(19.3f, 18.4f)
        curveTo(18.5f, 19.4f, 17.2f, 19.9f, 16f, 19.6f)
        curveTo(10.9f, 18.3f, 5.6f, 13.1f, 4.3f, 8f)
        curveTo(4f, 6.7f, 4.5f, 5.4f, 5.5f, 4.7f)
        close()
    }

    fun Document(color: Color = FacultyColors.Slate) = line("Document", color, 1.7f) {
        moveTo(6f, 3.5f); lineTo(14f, 3.5f); lineTo(19f, 8.5f)
        lineTo(19f, 20.5f); lineTo(6f, 20.5f); close()
        moveTo(14f, 3.5f); lineTo(14f, 8.5f); lineTo(19f, 8.5f)
        moveTo(9f, 12f); lineTo(16f, 12f)
        moveTo(9f, 15.5f); lineTo(16f, 15.5f)
    }

    fun ChevronRight(color: Color = FacultyColors.Navy, stroke: Float = 2f) =
        line("ChevronRight", color, stroke) {
            moveTo(9f, 5f); lineTo(16f, 12f); lineTo(9f, 19f)
        }

    fun ChevronDown(color: Color = FacultyColors.Navy) = line("ChevronDown", color, 1.9f) {
        moveTo(6f, 9f); lineTo(12f, 15f); lineTo(18f, 9f)
    }

    fun ArrowRight(color: Color = FacultyColors.Blue) = line("ArrowRight", color, 2f) {
        moveTo(4f, 12f); lineTo(19f, 12f)
        moveTo(13.5f, 6.5f); lineTo(19f, 12f); lineTo(13.5f, 17.5f)
    }

    fun ArrowUpRight(color: Color = FacultyColors.White) = line("ArrowUpRight", color, 2f) {
        moveTo(6f, 18f); lineTo(18f, 6f)
        moveTo(9f, 6f); lineTo(18f, 6f); lineTo(18f, 15f)
    }

    fun Layers(color: Color = FacultyColors.Navy) = line("Layers", color, 1.7f) {
        moveTo(12f, 3.5f); lineTo(21f, 8f); lineTo(12f, 12.5f); lineTo(3f, 8f); close()
        moveTo(3f, 12.2f); lineTo(12f, 16.7f); lineTo(21f, 12.2f)
        moveTo(3f, 16.3f); lineTo(12f, 20.8f); lineTo(21f, 16.3f)
    }

    fun Hash(color: Color = FacultyColors.Navy) = line("Hash", color, 1.9f) {
        moveTo(9f, 3.5f); lineTo(7.5f, 20.5f)
        moveTo(16.5f, 3.5f); lineTo(15f, 20.5f)
        moveTo(4f, 9f); lineTo(20f, 9f)
        moveTo(3.5f, 15f); lineTo(19.5f, 15f)
    }

    fun Home(color: Color = FacultyColors.Navy) = line("Home", color, 1.8f) {
        moveTo(3.5f, 11f); lineTo(12f, 4f); lineTo(20.5f, 11f)
        lineTo(20.5f, 20f); lineTo(3.5f, 20f); close()
        moveTo(9.5f, 20f); lineTo(9.5f, 13.5f); lineTo(14.5f, 13.5f); lineTo(14.5f, 20f)
    }

    fun Copy(color: Color = FacultyColors.Blue) = line("Copy", color, 1.8f) {
        moveTo(8f, 4f); lineTo(18f, 4f); curveTo(19.1f, 4f, 20f, 4.9f, 20f, 6f)
        lineTo(20f, 15f); curveTo(20f, 16.1f, 19.1f, 17f, 18f, 17f)
        moveTo(4f, 8f); lineTo(14f, 8f); curveTo(15.1f, 8f, 16f, 8.9f, 16f, 10f)
        lineTo(16f, 19f); curveTo(16f, 20.1f, 15.1f, 21f, 14f, 21f)
        lineTo(4f, 21f); curveTo(2.9f, 21f, 2f, 20.1f, 2f, 19f)
        lineTo(2f, 10f); curveTo(2f, 8.9f, 2.9f, 8f, 4f, 8f); close()
        moveTo(8f, 8f); lineTo(8f, 17f)
    }

    fun LiveGrid(color: Color = FacultyColors.Slate) = line("LiveGrid", color, 1.6f) {
        moveTo(4f, 4f); lineTo(9f, 4f); lineTo(9f, 9f); lineTo(4f, 9f); close()
        moveTo(15f, 4f); lineTo(20f, 4f); lineTo(20f, 9f); lineTo(15f, 9f); close()
        moveTo(4f, 15f); lineTo(9f, 15f); lineTo(9f, 20f); lineTo(4f, 20f); close()
        moveTo(15f, 15f); lineTo(20f, 15f); lineTo(20f, 20f); lineTo(15f, 20f); close()
        moveTo(11.5f, 4f); lineTo(12.5f, 4f)
        moveTo(11.5f, 7f); lineTo(12.5f, 7f)
        moveTo(4f, 11.5f); lineTo(4f, 12.5f)
        moveTo(7f, 11.5f); lineTo(7f, 12.5f)
        moveTo(11.5f, 11.5f); lineTo(12.5f, 12.5f)
        moveTo(15f, 11.5f); lineTo(20f, 11.5f)
        moveTo(17.5f, 15f); lineTo(17.5f, 20f)
    }

    fun Inbox(color: Color = FacultyColors.Blue) = line("Inbox", color, 1.8f) {
        moveTo(3f, 13f); lineTo(8f, 13f); lineTo(9.5f, 16f); lineTo(14.5f, 16f); lineTo(16f, 13f)
        lineTo(21f, 13f)
        moveTo(5.5f, 5f); lineTo(18.5f, 5f); curveTo(19.4f, 5f, 20.2f, 5.6f, 20.5f, 6.4f)
        lineTo(21f, 13f); lineTo(21f, 19f); lineTo(3f, 19f); lineTo(3f, 13f); lineTo(3.5f, 6.4f)
        curveTo(3.8f, 5.6f, 4.6f, 5f, 5.5f, 5f); close()
    }

    fun Eye(color: Color = FacultyColors.White) = line("Eye", color, 1.8f) {
        moveTo(2.5f, 12f)
        curveTo(5f, 7.2f, 8.2f, 5f, 12f, 5f)
        curveTo(15.8f, 5f, 19f, 7.2f, 21.5f, 12f)
        curveTo(19f, 16.8f, 15.8f, 19f, 12f, 19f)
        curveTo(8.2f, 19f, 5f, 16.8f, 2.5f, 12f); close()
        moveTo(12f, 14.6f)
        curveTo(13.44f, 14.6f, 14.6f, 13.44f, 14.6f, 12f)
        curveTo(14.6f, 10.56f, 13.44f, 9.4f, 12f, 9.4f)
        curveTo(10.56f, 9.4f, 9.4f, 10.56f, 9.4f, 12f)
        curveTo(9.4f, 13.44f, 10.56f, 14.6f, 12f, 14.6f); close()
    }

    fun IdCard(color: Color = FacultyColors.Blue) = line("IdCard", color, 1.7f) {
        moveTo(3f, 5.5f); lineTo(21f, 5.5f); lineTo(21f, 18.5f); lineTo(3f, 18.5f); close()
        moveTo(7.5f, 10.5f)
        curveTo(8.3f, 10.5f, 9f, 9.8f, 9f, 9f)
        curveTo(9f, 8.2f, 8.3f, 7.5f, 7.5f, 7.5f)
        curveTo(6.7f, 7.5f, 6f, 8.2f, 6f, 9f)
        curveTo(6f, 9.8f, 6.7f, 10.5f, 7.5f, 10.5f); close()
        moveTo(4.8f, 15.2f)
        curveTo(5f, 13.6f, 6.1f, 12.6f, 7.5f, 12.6f)
        curveTo(8.9f, 12.6f, 10f, 13.6f, 10.2f, 15.2f)
        moveTo(12.5f, 9f); lineTo(18f, 9f)
        moveTo(12.5f, 12.5f); lineTo(18f, 12.5f)
        moveTo(12.5f, 16f); lineTo(16f, 16f)
    }

    fun Building(color: Color = FacultyColors.Slate) = line("Building", color, 1.7f) {
        moveTo(4f, 21f); lineTo(4f, 4f); lineTo(16f, 4f); lineTo(16f, 21f)
        moveTo(16f, 9f); lineTo(20f, 9f); lineTo(20f, 21f)
        moveTo(2.5f, 21f); lineTo(21.5f, 21f)
        moveTo(7f, 7.5f); lineTo(7f, 8.5f)
        moveTo(10.5f, 7.5f); lineTo(10.5f, 8.5f)
        moveTo(13f, 7.5f); lineTo(13f, 8.5f)
        moveTo(7f, 11.5f); lineTo(7f, 12.5f)
        moveTo(10.5f, 11.5f); lineTo(10.5f, 12.5f)
        moveTo(13f, 11.5f); lineTo(13f, 12.5f)
        moveTo(7f, 15.5f); lineTo(7f, 16.5f)
        moveTo(10.5f, 15.5f); lineTo(10.5f, 16.5f)
        moveTo(13f, 15.5f); lineTo(13f, 16.5f)
        moveTo(9f, 21f); lineTo(9f, 18f); lineTo(11.5f, 18f); lineTo(11.5f, 21f)
    }

    fun Message(color: Color = FacultyColors.Blue) = line("Message", color, 1.8f) {
        moveTo(4f, 4.5f); lineTo(20f, 4.5f); lineTo(20f, 16f); lineTo(12f, 16f)
        lineTo(7f, 20f); lineTo(7f, 16f); lineTo(4f, 16f); close()
    }

    fun Logout(color: Color = FacultyColors.Red) = line("Logout", color, 1.8f) {
        moveTo(10f, 4f); lineTo(4.5f, 4f); lineTo(4.5f, 20f); lineTo(10f, 20f)
        moveTo(9f, 12f); lineTo(20f, 12f)
        moveTo(16f, 8f); lineTo(20f, 12f); lineTo(16f, 16f)
    }

    fun Tag(color: Color = FacultyColors.Navy) = line("Tag", color, 1.7f) {
        moveTo(3.5f, 4.5f); lineTo(11.5f, 4.5f); lineTo(21f, 14f); lineTo(13.5f, 21.5f)
        lineTo(4f, 12f); close()
        moveTo(8f, 8.4f); lineTo(8f, 8.5f)
    }

    fun Report(color: Color = FacultyColors.Blue) = line("Report", color, 1.7f) {
        moveTo(6f, 3.5f); lineTo(14f, 3.5f); lineTo(19f, 8.5f); lineTo(19f, 20.5f); lineTo(6f, 20.5f); close()
        moveTo(14f, 3.5f); lineTo(14f, 8.5f); lineTo(19f, 8.5f)
        moveTo(9f, 12f); lineTo(16f, 12f)
        moveTo(9f, 15.5f); lineTo(16f, 15.5f)
        moveTo(9f, 18.5f); lineTo(13f, 18.5f)
    }

    fun Shield(color: Color = FacultyColors.Blue) = line("Shield", color, 1.7f) {
        moveTo(12f, 3f); lineTo(20f, 6.2f); lineTo(20f, 11.5f)
        curveTo(20f, 16.4f, 16.7f, 19.8f, 12f, 21.2f)
        curveTo(7.3f, 19.8f, 4f, 16.4f, 4f, 11.5f)
        lineTo(4f, 6.2f); close()
    }

    fun Help(color: Color = FacultyColors.Blue) = line("Help", color, 1.7f) {
        moveTo(21f, 12f)
        curveTo(21f, 16.97f, 16.97f, 21f, 12f, 21f)
        curveTo(7.03f, 21f, 3f, 16.97f, 3f, 12f)
        curveTo(3f, 7.03f, 7.03f, 3f, 12f, 3f)
        curveTo(16.97f, 3f, 21f, 7.03f, 21f, 12f); close()
        moveTo(9.4f, 9.2f)
        curveTo(9.7f, 7.7f, 10.7f, 6.9f, 12f, 6.9f)
        curveTo(13.4f, 6.9f, 14.5f, 7.8f, 14.5f, 9.1f)
        curveTo(14.5f, 10.9f, 12f, 10.9f, 12f, 13.1f)
        moveTo(12f, 16.3f); lineTo(12f, 16.4f)
    }
}

// ---------------------------------------------------------------------------
// Helpers used across the faculty screens
// ---------------------------------------------------------------------------
@Composable
fun FacultyAvatar(initials: String, size: Int = 56) {
    Box(
        modifier = Modifier
            .size(size.dp)
            .background(FacultyColors.BlueSoft, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initials,
            color = FacultyColors.Navy,
            fontSize = (size * 0.32f).sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun FacultySmallLabel(text: String, color: Color = FacultyColors.Slate) {
    Text(
        text = text,
        color = color,
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        textAlign = TextAlign.Start
    )
}
