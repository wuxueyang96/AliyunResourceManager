package life.wuxueyang

import java.text.SimpleDateFormat
import java.util.*

object ResultFormatter {
    fun formatResult(message: String): String {
        val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val time = dateFormat.format(Date())
        return "[$time] $message"
    }
    
    fun formatSuccess(message: String): String {
        return "✅ ${formatResult(message)}"
    }
    
    fun formatError(message: String): String {
        return "❌ ${formatResult(message)}"
    }
    
    fun formatInfo(message: String): String {
        return "ℹ️ ${formatResult(message)}"
    }
}