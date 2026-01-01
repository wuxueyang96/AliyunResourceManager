package life.wuxueyang

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import java.util.concurrent.Executor

class LoginActivity : AppCompatActivity() {
    
    private lateinit var biometricExecutor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Check if credentials are already set
        val secureStorage = SecureStorage(this)
        try {
            if (secureStorage.areCredentialsSet()) {
                // If credentials are set, show biometric authentication
                setContentView(R.layout.activity_login)
                
                biometricExecutor = ContextCompat.getMainExecutor(this)
                
                biometricPrompt = BiometricPrompt(this, biometricExecutor,
                    object : BiometricPrompt.AuthenticationCallback() {
                        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                            super.onAuthenticationError(errorCode, errString)
                            if (errorCode == BiometricPrompt.ERROR_NO_DEVICE_CREDENTIAL) {
                                // Device doesn't have biometrics or backup device authentication (PIN/pattern/password)
                                Toast.makeText(
                                    this@LoginActivity,
                                    "Device authentication required but not set up",
                                    Toast.LENGTH_LONG
                                ).show()
                            } else {
                                // Use device credential as fallback (PIN/pattern/password)
                                authenticateWithDeviceCredential()
                            }
                        }

                        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                            super.onAuthenticationSucceeded(result)
                            Toast.makeText(
                                this@LoginActivity,
                                "Authentication successful",
                                Toast.LENGTH_SHORT
                            ).show()
                            // Navigate to MainActivity
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                            finish()
                        }

                        override fun onAuthenticationFailed() {
                            super.onAuthenticationFailed()
                            Toast.makeText(
                                this@LoginActivity,
                                "Authentication failed, using device credentials",
                                Toast.LENGTH_SHORT
                            ).show()
                            // Use device credential as fallback
                            authenticateWithDeviceCredential()
                        }
                    })

                promptInfo = BiometricPrompt.PromptInfo.Builder()
                    .setTitle("Authenticate to access cloud resources")
                    .setSubtitle("Use your fingerprint, face, or device credentials (PIN/pattern/password)")
                    .setNegativeButtonText("Cancel")
                    .setDeviceCredentialAllowed(true) // Allow device PIN/pattern/password as fallback
                    .setConfirmationRequired(false) // Set to false to allow device credentials as fallback
                    .build()

                val authenticateButton = findViewById<Button>(R.id.authenticateButton)
                authenticateButton.setOnClickListener {
                    biometricPrompt.authenticate(promptInfo)
                }
                
                // Also add a button to directly use device credentials
                val deviceAuthButton = findViewById<Button>(R.id.deviceAuthButton)
                deviceAuthButton.setOnClickListener {
                    authenticateWithDeviceCredential()
                }
            } else {
                // If no credentials are set, go directly to CredentialSetupActivity
                startActivity(Intent(this, CredentialSetupActivity::class.java))
                finish()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error checking credentials: ${e.message}", Toast.LENGTH_LONG).show()
            // If there's an error, go to credential setup
            startActivity(Intent(this, CredentialSetupActivity::class.java))
            finish()
        }
    }
    
    private fun authenticateWithDeviceCredential() {
        // Use BiometricPrompt to authenticate with device credentials (PIN, pattern, or password)
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Device Authentication Required")
            .setSubtitle("Use your device PIN, pattern, or password to continue")
            .setDeviceCredentialAllowed(true) // This allows the system PIN/pattern/password
            .setNegativeButtonText("Cancel")
            .build()

        biometricPrompt.authenticate(promptInfo)
    }
}