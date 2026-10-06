package com.minicodex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.minicodex.ui.theme.DarkColors
import com.minicodex.ui.screens.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colorScheme = DarkColors) {
                val navController = rememberNavController()
                var currentProject by remember { mutableStateOf("Student Management System") }

                NavHost(navController = navController, startDestination = "home") {
                    composable("home") {
                        HomeScreen(
                            aiStatus = "Online",
                            projectCount = 12,
                            activeTasks = 2,
                            lastProject = currentProject,
                            onNewProject = { /* TODO */ },
                            onAskAI = { navController.navigate("agent") },
                            onOpenBrowser = { navController.navigate("browser") },
                            onOpenTerminal = { navController.navigate("terminal") }
                        )
                    }
                    composable("agent") {
                        AgentScreen(
                            activities = emptyList(),
                            status = "Idle",
                            onSendMessage = { /* TODO */ },
                            onStopAgent = { /* TODO */ }
                        )
                    }
                    composable("terminal") {
                        TerminalScreen(
                            output = "$ welcome to MiniCodex Terminal\n$ ",
                            onExecute = { /* TODO */ }
                        )
                    }
                    composable("browser") {
                        // BrowserScreen implementation
                    }
                }
            }
        }
    }
}
