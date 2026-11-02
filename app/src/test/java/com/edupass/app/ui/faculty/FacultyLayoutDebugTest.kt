package com.edupass.app.ui.faculty

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import com.edupass.app.ui.faculty.dashboard.FacultyDashboardScreen
import com.edupass.app.ui.theme.EduPassTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [35], qualifiers = "w412dp-h1200dp-xxhdpi")
class FacultyLayoutDebugTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun printBounds() {
        composeRule.setContent { EduPassTheme { FacultyTypography { FacultyDashboardScreen() } } }
        composeRule.waitForIdle()
        listOf(
            "EduPass", "Faculty Dashboard", "Faculty workspace",
            "Good morning, Dr. Meera", "Computer Science & Engineering",
            "12 requests require your decision",
            "Today at a glance", "Pending requests", "12", "Pending",
            "Request status", "Needs your attention",
            "Requests awaiting faculty approval", "Arjun Kumar"
        ).forEach { text ->
            val nodes = composeRule.onAllNodesWithText(text, useUnmergedTree = true).fetchSemanticsNodes()
            nodes.forEachIndexed { i, node ->
                val b = node.boundsInRoot
                println("BOUNDS '$text'[$i] = left=${b.left} top=${b.top} right=${b.right} bottom=${b.bottom} w=${b.width} h=${b.height}")
            }
        }
    }
}
