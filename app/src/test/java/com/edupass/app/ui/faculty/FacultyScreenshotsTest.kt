package com.edupass.app.ui.faculty

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import com.edupass.app.ui.faculty.dashboard.FacultyDashboardScreen
import com.edupass.app.ui.faculty.live.CurrentlyOutsideScreen
import com.edupass.app.ui.faculty.overview.FacultyOverviewScreen
import com.edupass.app.ui.faculty.profile.FacultyProfileScreen
import com.edupass.app.ui.faculty.reports.FacultyReportsScreen
import com.edupass.app.ui.faculty.requests.PermissionRequestsScreen
import com.edupass.app.ui.faculty.requests.ReviewRequestScreen
import com.edupass.app.ui.faculty.sections.SectionOverviewScreen
import com.edupass.app.ui.theme.EduPassTheme
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [35], qualifiers = "w412dp-h1200dp-xxhdpi")
class FacultyScreenshotsTest {

    init {
        System.setProperty("roborazzi.test.record", "true")
    }

    @get:Rule
    val composeRule = createComposeRule()

    private fun capture(name: String, content: @Composable () -> Unit) {
        composeRule.setContent { EduPassTheme { FacultyTypography { content() } } }
        composeRule.waitForIdle()
        composeRule.onRoot().captureRoboImage("build/screenshots/$name.png")
    }

    @Test
    fun dashboard() = capture("dashboard") { FacultyDashboardScreen() }

    @Test
    fun sectionOverview() = capture("section_overview") { SectionOverviewScreen() }

    @Test
    fun permissionRequests() = capture("permission_requests") { PermissionRequestsScreen() }

    @Test
    fun reviewRequest() = capture("review_request") { ReviewRequestScreen() }

    @Test
    fun currentlyOutside() = capture("currently_outside") { CurrentlyOutsideScreen() }

    @Test
    fun facultyOverview() = capture("faculty_overview") { FacultyOverviewScreen() }

    @Test
    fun facultyReports() = capture("faculty_reports") { FacultyReportsScreen() }

    @Test
    fun facultyProfile() = capture("faculty_profile") { FacultyProfileScreen() }
}
