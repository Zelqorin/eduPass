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
import com.edupass.app.ui.student.dashboard.StudentDashboardScreen
import com.edupass.app.ui.login.LoginScreen
import com.edupass.app.ui.splash.SplashScreen
import com.edupass.app.ui.theme.EduPassTheme
import kotlinx.coroutines.delay

enum class Screen {
    Splash,
    Login,
    StudentDashBoard
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
                                currentScreen = Screen.StudentDashBoard
                            }
                        )
                    }
                    Screen.StudentDashBoard -> {
                        StudentDashboardScreen()
                    }
                }
            }
        }
    }
}