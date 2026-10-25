package com.edupass.app

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Box
import com.edupass.app.data.AuthRepository
import com.edupass.app.data.LoginResult
import kotlinx.coroutines.launch
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.edupass.app.ui.student.notifications.NotificationsScreen
import com.edupass.app.ui.student.passes.PermissionDetailsScreen
import com.edupass.app.ui.student.passes.DigitalPermissionPassScreen
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
    Passes,
    PermissionDetails,
    Profile
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EduPassTheme {
                
                var loginError by remember { mutableStateOf<String?>(null) }
                var isLoggingIn by remember { mutableStateOf(false) }
                val scope = rememberCoroutineScope()
                
                var currentScreen by remember { mutableStateOf(Screen.Splash) }

                BackHandler(enabled = currentScreen != Screen.Splash && currentScreen != Screen.Login) {
                    when (currentScreen) {
                        Screen.Notifications -> currentScreen = Screen.StudentDashBoard
                        Screen.PermissionDetails -> currentScreen = Screen.Passes
                        else -> currentScreen = Screen.StudentDashBoard
                    }
                }
                
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
                    Box {
                        LoginScreen(
                            onSignInClick = { collegeId, password, role ->

                            if (collegeId.isBlank() || password.isBlank()) {
                                loginError = "Enter your roll number and password."
                            } else if (!isLoggingIn) {

                            scope.launch {
                                isLoggingIn = true
                                loginError = null

                                try {
                                    val result = AuthRepository.login(
                                    rollNumber = collegeId,
                                    password = password,
                                    selectedRole = when (role) {
                                        UserRole.STUDENT -> "student"
                                        UserRole.FACULTY -> "faculty"
                                        UserRole.ADMIN -> "admin"
                                    }
                                )

                                when (result) {
                                    is LoginResult.Success -> {
                                        when (result.profile.role.lowercase()) {
                                            "student" -> currentScreen = Screen.StudentDashBoard

                                            "faculty" -> currentScreen = Screen.FacultyDashboard

                                            "admin" -> currentScreen = Screen.AdminDashboard

                                            else -> {
                                                loginError = "Your account has an unsupported role."
                                            }
                                        }
                                    }

                                    is LoginResult.Failure -> {
                                        loginError = result.message
                                    }
                                }
                            } catch (e: Exception) {
                                loginError =
                                    "Login failed. Please check your connection."
                            } finally {
                                isLoggingIn = false
                            }
                        }
                    }
                }
            )

            if (isLoggingIn) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
              )
            }

            loginError?.let { message ->
                Text(
                    text = message,
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
            }
        }
    }
                    
                    Screen.StudentDashBoard -> {
                        StudentDashboardScreen(
                            onNotificationClick = {
                                currentScreen = Screen.Notifications
                            },
                            
                            onRequestPermissionClick = {
                                currentScreen = Screen.ApplyPermission
                            },
                            onRequestsTabClick = {
                                currentScreen = Screen.MyRequests
                            },
                                
                            onPassesTabClick = {
                                currentScreen = Screen.Passes
                            },
                                
                            onProfileTabClick = {
                                currentScreen = Screen.Profile
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
                            onNotificationClick = {
                                currentScreen = Screen.Notifications
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
                            onNotificationClick = {
                                currentScreen = Screen.Notifications
                            },
                            
                            onBottomNavTabSelected = { index ->
                                when (index) {
                                    0 -> currentScreen = Screen.StudentDashBoard
                                    2 -> currentScreen = Screen.Passes
                                    3 -> currentScreen = Screen.Profile
                                }
                            }
                        )
                    }
                    
                    Screen.Passes -> {
                        DigitalPermissionPassScreen(
                            onBackClick = {
                                currentScreen = Screen.StudentDashBoard
                            },
                                
                            onNotificationClick = {
                                currentScreen = Screen.Notifications
                            },
                            
                            onViewDigitalPassClick = {
                                currentScreen = Screen.PermissionDetails
                            },
                                
                            onBottomNavTabSelected = { index ->
                                when (index) {
                                    0 -> currentScreen = Screen.StudentDashBoard
                                    1 -> currentScreen = Screen.MyRequests
                                    3 -> currentScreen = Screen.Profile
                                }
                            }
                        )
                    }
                    
                    Screen.Profile -> {
                        androidx.compose.material3.Text(text = "Profile Coming Soon")
                    }
                    
                    Screen.Notifications -> {
                        NotificationsScreen(
                            onBackClick = {
                                currentScreen = Screen.StudentDashBoard
                            }
                        )
                    }
                    
                    Screen.PermissionDetails -> {
                        PermissionDetailsScreen(
                            onBackClick = {
                                currentScreen = Screen.Passes
                            },
                            
                            onNotificationClick = {
                                currentScreen = Screen.Notifications
                            }
                        )
                    }
                }
            }
        }
    }
}