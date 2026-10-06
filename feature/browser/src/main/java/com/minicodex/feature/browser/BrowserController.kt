package com.minicodex.feature.browser

import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.ValueCallback
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

/**
 * Controls an Android WebView to perform automation tasks.
 */
class BrowserController(private val webView: WebView) {

    fun openUrl(url: String) {
        webView.loadUrl(url)
    }

    suspend fun readPageContent(): String = suspendCancellableCoroutine { continuation ->
        webView.evaluateJavascript("document.body.innerText") { value ->
            continuation.resume(value)
        }
    }

    suspend fun clickElement(selector: String): String = suspendCancellableCoroutine { continuation ->
        val js = " (function() { " +
                "  var el = document.querySelector('$selector'); " +
                "  if (el) { el.click(); return 'Clicked'; } " +
                "  return 'Element not found'; " +
                "})() "
        webView.evaluateJavascript(js) { value ->
            continuation.resume(value)
        }
    }

    suspend fun typeText(selector: String, text: String): String = suspendCancellableCoroutine { continuation ->
        val js = " (function() { " +
                "  var el = document.querySelector('$selector'); " +
                "  if (el) { el.value = '$text'; return 'Typed'; } " +
                "  return 'Element not found'; " +
                "})() "
        webView.evaluateJavascript(js) { value ->
            continuation.resume(value)
        }
    }
}
