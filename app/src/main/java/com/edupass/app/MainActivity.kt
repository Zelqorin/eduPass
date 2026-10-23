package com.edupass.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.edupass.app.ui.student.requests.MyRequestsScreen
import com.edupass.app.ui.student.permission.RequestReviewScreen
import com.edupass.app.ui.student.permission.DocumentUploadScreen
import com.edupass.app.ui.student.permission.ApplyPermissionScreen
import com.edupass.app.ui.student.dashboard.StudentDashboardScreen
import com.edupass.app.ui.login.UserRole
import com.edupass.app.ui.login.LoginScreen
import com.edupass.app.ui.splash.SplashScreen
import com.edupass.app.ui.theme.EduPassTheme
import kotlinx.coroutines.delay

enum class Screen {
    Splash,
    Login,
    StudentDashBoard,
    FacultyDashboard,
    AdminDashboard,
    ApplyPermission,
    DocumentUpload,
    RequestReview,
    MyRequests,
    Notifications,
    PermissionDetails
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EduPassTheme {
                var currentScreen by remember { mutableStateOf(Screen.Splash) }

                when (currentScreen) {
                    Screen.Splash -> {
                        SplashScreen()
                        // 2-second splash timer before transitioning to Login
                        LaunchedEffect(Unit) {
                            delay(2000)
                            currentScreen = Screen.Login
                        }
                    }
                    Screen.Login -> {
                        LoginScreen(
                            onSignInClick = { collegeId, role ->
                                currentScreen = when (role) {
                                    UserRole.STUDENT -> Screen.StudentDashBoard
                                    UserRole.FACULTY -> Screen.FacultyDashboard
                                    UserRole.ADMIN -> Screen.AdminDashboard
                                }
                            }
                        )
                    }
                    Screen.StudentDashBoard -> {
                        StudentDashboardScreen(
                            onRequestPermissionClick = {
                                currentScreen = Screen.ApplyPermission
                            },
                            onRequestsTabClick = {
                                currentScreen = Screen.MyRequests
                            }
                        )
                    }
                    
                    Screen.FacultyDashboard -> {
                        androidx.compose.material3.Text(text = "Faculty Dashboard Coming Soon")
                    }
                    
                    Screen.AdminDashboard -> {
                        androidx.compose.material3.Text(text = "Admin Dashboard Coming Soon")
                    }
                    
                    Screen.ApplyPermission -> {
                        ApplyPermissionScreen(
                            onBackClick = {
                                currentScreen = Screen.StudentDashBoard
                            },
                            onNextClick = {
                                currentScreen = Screen.DocumentUpload
                            }
                        )
                    }
                    Screen.DocumentUpload -> {
                        DocumentUploadScreen(
                            onBackClick = {
                                currentScreen = Screen.ApplyPermission
                            },
                            onContinueClick = {
                                currentScreen = Screen.RequestReview
                            }
                        )
                    }
                    Screen.RequestReview -> {
                        RequestReviewScreen(
                            onEditDetailsClick = {
                                currentScreen = Screen.ApplyPermission
                            }
                        )
                    }
                    Screen.MyRequests -> {
                        MyRequestsScreen(
                            onBottomNavTabSelected = { index ->
                                if (index == 0) {
                                    currentScreen = Screen.StudentDashBoard
                                }
                            }
                        )
                    }
                    
                    Screen.Notifications -> {
                        androidx.compose.material3.Text(text = "Notifications Coming Soon")
                    }
                    
                    Screen.PermissionDetails -> {
                        androidx.compose.material3.Text(text = "Permission Details Coming Soon")
                    }
                }
            }
        }
    }
}