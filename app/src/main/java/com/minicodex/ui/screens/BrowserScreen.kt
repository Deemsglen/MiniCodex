package com.minicodex.ui.screens

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.unit.dp
import com.minicodex.feature.browser.BrowserController

@Composable
fun BrowserScreen(
    onUrlChanged: (String) -> Unit
) {
    var url by remember { mutableStateOf("https://google.com") }
    var webView by remember { mutableStateOf<WebView?>(null) }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextField(
                value = url,
                onValueChange = { url = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Enter URL...") },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface
                )
            )
            Button(onClick = { /* WebView handles loadUrl internally via State */ }) {
                Text("Go")
            }
        }

        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    settings.javaScriptEnabled = true
                    webViewClient = WebViewClient()
                    webView.loadUrl(url)
                    webView = this
                }
            },
            modifier = Modifier.fillMaxSize(),
            update = { view ->
                view.loadUrl(url)
            }
        )
    }
}
