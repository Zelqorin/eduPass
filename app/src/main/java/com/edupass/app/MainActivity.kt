package com.edupass.app

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
import com.edupass.app.ui.faculty.FacultyTypography
import com.edupass.app.ui.faculty.dashboard.FacultyDashboardScreen
import com.edupass.app.ui.faculty.live.CurrentlyOutsideScreen
import com.edupass.app.ui.faculty.overview.FacultyOverviewScreen
import com.edupass.app.ui.faculty.profile.FacultyProfileScreen
import com.edupass.app.ui.faculty.reports.FacultyReportsScreen
import com.edupass.app.ui.faculty.requests.PermissionRequestsScreen
import com.edupass.app.ui.faculty.requests.ReviewRequestScreen
import com.edupass.app.ui.faculty.sections.SectionOverviewScreen
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
    FacultySectionOverview,
    FacultyPermissionRequests,
    FacultyReviewRequest,
    FacultyLive,
    FacultyOverview,
    FacultyReports,
    FacultyProfile,
    Profile
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EduPassTheme {
                var currentScreen by remember { mutableStateOf(Screen.Splash) }

                BackHandler(enabled = currentScreen != Screen.Splash && currentScreen != Screen.Login && currentScreen != Screen.StudentDashBoard && currentScreen != Screen.FacultyDashboard) {
                    when (currentScreen) {
                        Screen.Notifications -> currentScreen = Screen.StudentDashBoard
                        Screen.PermissionDetails -> currentScreen = Screen.Passes
                        Screen.FacultySectionOverview,
                        Screen.FacultyPermissionRequests,
                        Screen.FacultyLive,
                        Screen.FacultyProfile,
                        Screen.FacultyOverview,
                        Screen.FacultyReports -> currentScreen = Screen.FacultyDashboard
                        Screen.FacultyReviewRequest -> currentScreen = Screen.FacultyPermissionRequests
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
                        FacultyTypography {
                            FacultyDashboardScreen(
                                onPendingRequestsClick = { currentScreen = Screen.FacultyPermissionRequests },
                                onOutsideClick = { currentScreen = Screen.FacultyLive },
                                onReportsClick = { currentScreen = Screen.FacultyReports },
                                onAttentionClick = { currentScreen = Screen.FacultyReviewRequest },
                                onQueueClick = { currentScreen = Screen.FacultyOverview },
                                onHomeTabClick = { currentScreen = Screen.FacultyDashboard },
                                onSectionsTabClick = { currentScreen = Screen.FacultySectionOverview },
                                onLiveTabClick = { currentScreen = Screen.FacultyLive },
                                onProfileTabClick = { currentScreen = Screen.FacultyProfile }
                            )
                        }
                    }

                    Screen.FacultySectionOverview -> {
                        FacultyTypography {
                            SectionOverviewScreen(
                                onSectionClick = { currentScreen = Screen.FacultyPermissionRequests },
                                onHomeTabClick = { currentScreen = Screen.FacultyDashboard },
                                onSectionsTabClick = { currentScreen = Screen.FacultySectionOverview },
                                onLiveTabClick = { currentScreen = Screen.FacultyLive },
                                onProfileTabClick = { currentScreen = Screen.FacultyProfile }
                            )
                        }
                    }

                    Screen.FacultyPermissionRequests -> {
                        FacultyTypography {
                            PermissionRequestsScreen(
                                onRequestClick = { currentScreen = Screen.FacultyReviewRequest },
                                onHomeTabClick = { currentScreen = Screen.FacultyDashboard },
                                onSectionsTabClick = { currentScreen = Screen.FacultySectionOverview },
                                onLiveTabClick = { currentScreen = Screen.FacultyLive },
                                onProfileTabClick = { currentScreen = Screen.FacultyProfile }
                            )
                        }
                    }

                    Screen.FacultyReviewRequest -> {
                        FacultyTypography {
                            ReviewRequestScreen()
                        }
                    }

                    Screen.FacultyLive -> {
                        FacultyTypography {
                            CurrentlyOutsideScreen(
                                onHomeTabClick = { currentScreen = Screen.FacultyDashboard },
                                onSectionsTabClick = { currentScreen = Screen.FacultySectionOverview },
                                onLiveTabClick = { currentScreen = Screen.FacultyLive },
                                onProfileTabClick = { currentScreen = Screen.FacultyProfile }
                            )
                        }
                    }

                    Screen.FacultyOverview -> {
                        FacultyTypography {
                            FacultyOverviewScreen(
                                onOpenQueueClick = { currentScreen = Screen.FacultyPermissionRequests },
                                onRequestClick = { currentScreen = Screen.FacultyReviewRequest }
                            )
                        }
                    }

                    Screen.FacultyReports -> {
                        FacultyTypography {
                            FacultyReportsScreen()
                        }
                    }

                    Screen.FacultyProfile -> {
                        FacultyTypography {
                            FacultyProfileScreen(
                                onSignOutClick = { currentScreen = Screen.Login },
                                onHomeTabClick = { currentScreen = Screen.FacultyDashboard },
                                onSectionsTabClick = { currentScreen = Screen.FacultySectionOverview },
                                onLiveTabClick = { currentScreen = Screen.FacultyLive },
                                onProfileTabClick = { currentScreen = Screen.FacultyProfile }
                            )
                        }
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