package life.wuxueyang

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

open class CredentialSetupActivity : AppCompatActivity() {
    
    private lateinit var alibabaAccessKeyIdEditText: TextInputEditText
    private lateinit var alibabaAccessKeySecretEditText: TextInputEditText
    private lateinit var tencentAccessKeyIdEditText: TextInputEditText
    private lateinit var tencentAccessKeySecretEditText: TextInputEditText
    private lateinit var saveCredentialsButton: Button
    
    // Make CloudCredentialsStorage injectable for testing
    private var _credentialsStorage: CloudCredentialsStorage? = null
    private val storage: CloudCredentialsStorage
        get() {
            if (_credentialsStorage == null) {
                _credentialsStorage = createCredentialsStorage()
            }
            return _credentialsStorage!!
        }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_credential_setup)
        
        initViews()
        setupClickListeners()
    }
    
    // Method to allow direct injection for testing
    fun setCredentialsStorage(storage: CloudCredentialsStorage) {
        this._credentialsStorage = storage
    }
    
    // Factory method to allow overriding in tests
    protected open fun createCredentialsStorage(): CloudCredentialsStorage {
        return SecureStorage(this)
    }
    
    private fun initViews() {
        alibabaAccessKeyIdEditText = findViewById(R.id.alibabaAccessKeyIdEditText)
        alibabaAccessKeySecretEditText = findViewById(R.id.alibabaAccessKeySecretEditText)
        tencentAccessKeyIdEditText = findViewById(R.id.tencentAccessKeyIdEditText)
        tencentAccessKeySecretEditText = findViewById(R.id.tencentAccessKeySecretEditText)

        saveCredentialsButton = findViewById(R.id.saveCredentialsButton)
    }
    
    private fun setupClickListeners() {
        saveCredentialsButton.setOnClickListener {
            saveCredentials()
        }
    }
    
    private fun saveCredentials() {
        val alibabaAccessKeyId = alibabaAccessKeyIdEditText.text.toString().trim()
        val alibabaAccessKeySecret = alibabaAccessKeySecretEditText.text.toString().trim()
        val tencentAccessKeyId = tencentAccessKeyIdEditText.text.toString().trim()
        val tencentAccessKeySecret = tencentAccessKeySecretEditText.text.toString().trim()
        
        if (alibabaAccessKeyId.isEmpty() && alibabaAccessKeySecret.isEmpty() &&
            tencentAccessKeyId.isEmpty() && tencentAccessKeySecret.isEmpty()) {
            Toast.makeText(this, "Please enter at least one set of credentials", Toast.LENGTH_SHORT).show()
            return
        }
        
        try {
            if (alibabaAccessKeyId.isNotEmpty() && alibabaAccessKeySecret.isNotEmpty()) {
                storage.saveAlibabaCredentials(alibabaAccessKeyId, alibabaAccessKeySecret)
            }
            
            if (tencentAccessKeyId.isNotEmpty() && tencentAccessKeySecret.isNotEmpty()) {
                storage.saveTencentCredentials(tencentAccessKeyId, tencentAccessKeySecret)
            }
            
            Toast.makeText(this, "Credentials saved successfully!", Toast.LENGTH_SHORT).show()
            
            // Redirect to login activity for biometric authentication setup
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        } catch (e: Exception) {
            Toast.makeText(this, "Error saving credentials: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}