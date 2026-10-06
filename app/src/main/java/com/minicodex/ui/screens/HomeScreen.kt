package com.minicodex.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.minicodex.ui.components.DevCard
import com.minicodex.ui.components.TerminalText
import com.minicodex.feature.agent.AgentStatus
import com.minicodex.feature.agent.AgentActivity

@Composable
fun HomeScreen(
    aiStatus: String,
    projectCount: Int,
    activeTasks: Int,
    lastProject: String,
    onNewProject: () -> Unit,
    onAskAI: () -> Unit,
    onOpenBrowser: () -> Unit,
    onOpenTerminal: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "MiniCodex",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(24.dp))

        DevCard("System Status") {
            Column {
                Text("AI Status: ● $aiStatus", color = MaterialTheme.colorScheme.onSurface)
                Text("Projects: $projectCount", color = MaterialTheme.colorScheme.onSurface)
                Text("Active Tasks: $activeTasks", color = MaterialTheme.colorScheme.onSurface)
                Text("Last Project: $lastProject", color = MaterialTheme.colorScheme.onSurface)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = onNewProject,
                modifier = Modifier.weight(1f)
            ) { Text("New Project") }
            Button(
                onClick = onAskAI,
                modifier = Modifier.weight(1f)
            ) { Text("Ask AI") }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = onOpenBrowser,
                modifier = Modifier.weight(1f)
            ) { Text("Browser") }
            Button(
                onClick = onOpenTerminal,
                modifier = Modifier.weight(1f)
            ) { Text("Terminal") }
        }
    }
}
