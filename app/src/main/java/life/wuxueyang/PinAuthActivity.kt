package life.wuxueyang

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DeviceAuthActivity : AppCompatActivity() {
    
    private lateinit var authenticateButton: Button
    private lateinit var cancelButton: Button
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_auth)
        
        initViews()
        setupClickListeners()
    }
    
    private fun initViews() {
        authenticateButton = findViewById(R.id.authenticateButton)
        cancelButton = findViewById(R.id.cancelButton)
    }
    
    private fun setupClickListeners() {
        authenticateButton.setOnClickListener {
            // In this case, we'll just finish the activity and let the calling activity handle authentication
            Toast.makeText(this, "Device authentication would be triggered here", Toast.LENGTH_SHORT).show()
            finish()
        }
        
        cancelButton.setOnClickListener {
            finish()
        }
    }
}
