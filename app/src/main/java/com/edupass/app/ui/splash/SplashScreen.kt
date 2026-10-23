package com.edupass.app.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edupass.app.R
import com.edupass.app.ui.theme.EduPassBlue
import com.edupass.app.ui.theme.EduPassNavy
import com.edupass.app.ui.theme.EduPassTextSecondary
import com.edupass.app.ui.theme.EduPassTheme
import com.edupass.app.ui.theme.EduPassWhite

/**
 * Native Compose Shape creating a smooth curved boundary at the bottom
 * of the upper image container.
 *
 * @param curveDepth Depth of the curve. Positive values curve gently downward
 * into the white container; negative values create an upward dome curve.
 */
class CurvedBottomShape(private val curveDepth: Dp = 28.dp) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val depthPx = with(density) { curveDepth.toPx() }
        val path = Path().apply {
            reset()
            moveTo(0f, 0f)
            lineTo(size.width, 0f)
            if (depthPx >= 0) {
                // Downward convex arc
                lineTo(size.width, size.height - depthPx)
                quadraticTo(
                    size.width / 2f,
                    size.height,
                    0f,
                    size.height - depthPx
                )
            } else {
                // Upward convex dome
                lineTo(size.width, size.height)
                quadraticTo(
                    size.width / 2f,
                    size.height + depthPx,
                    0f,
                    size.height
                )
            }
            close()
        }
        return Outline.Generic(path)
    }
}

@Composable
fun SplashScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(EduPassWhite),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 1. Campus photograph in the upper section with curved boundary
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.42f)
                .clip(CurvedBottomShape(curveDepth = 28.dp))
        ) {
            Image(
                painter = painterResource(id = R.drawable.campus_photo),
                contentDescription = "Campus Photograph",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            )
        }

        // 2. Lower branding, identity, and loading section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.58f)
                .navigationBarsPadding()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // College Logo Asset
            Image(
                painter = painterResource(id = R.drawable.vignan_logo),
                contentDescription = "Vignan's Logo",
                modifier = Modifier
                    .height(68.dp)
                    .fillMaxWidth(0.56f),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.weight(0.8f))

            // EduPass Branding
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = EduPassNavy,
                                fontWeight = FontWeight.Bold,
                                fontSize = 44.sp,
                                letterSpacing = (-0.5).sp
                            )
                        ) {
                            append("Edu")
                        }
                        withStyle(
                            style = SpanStyle(
                                color = EduPassBlue,
                                fontWeight = FontWeight.Bold,
                                fontSize = 44.sp,
                                letterSpacing = (-0.5).sp
                            )
                        ) {
                            append("Pass")
                        }
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Subtitle (wraps to 2 lines where required)
                Text(
                    text = "Student Permission & Class-Out Management",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        lineHeight = 20.sp
                    ),
                    color = EduPassTextSecondary,
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    modifier = Modifier.fillMaxWidth(0.72f)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Thin blue loading indicator & 3 loading dots
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .width(130.dp)
                        .height(3.dp)
                        .clip(CircleShape),
                    color = EduPassBlue,
                    trackColor = EduPassBlue.copy(alpha = 0.15f)
                )

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(3) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .background(
                                    color = EduPassBlue,
                                    shape = CircleShape
                                )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    device = "spec:width=390dp,height=920dp,dpi=420"
)
@Composable
fun SplashScreenPreview() {
    EduPassTheme {
        SplashScreen()
    }
}