package life.wuxueyang

import android.widget.Button
import android.widget.Toast
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.material.textfield.TextInputEditText
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowToast

@RunWith(AndroidJUnit4::class)
@Config(sdk = [28])
class CredentialSetupActivityTest {

    @Test
    fun `saveCredentials with valid Alibaba credentials should save successfully`() {
        // Create a mock for CloudCredentialsStorage
        val mockCredentialsStorage = MockSecureStorage()
        
        // Launch the test activity and inject the mock
        val scenario = ActivityScenario.launch(CredentialSetupActivity::class.java)
        scenario.onActivity { activity ->
            activity.setCredentialsStorage(mockCredentialsStorage)
            
            // Set up valid Alibaba credentials
            val accessKeyId = "valid_access_key_id"
            val accessKeySecret = "valid_access_key_secret"

            val alibabaAccessKeyIdEditText = activity.findViewById<TextInputEditText>(R.id.alibabaAccessKeyIdEditText)
            val alibabaAccessKeySecretEditText = activity.findViewById<TextInputEditText>(R.id.alibabaAccessKeySecretEditText)
            val saveCredentialsButton = activity.findViewById<Button>(R.id.saveCredentialsButton)

            alibabaAccessKeyIdEditText.setText(accessKeyId)
            alibabaAccessKeySecretEditText.setText(accessKeySecret)

            // Click the save button
            saveCredentialsButton.performClick()
        }

        // Verify toast message
        val latestToast = ShadowToast.getLatestToast()
        assert(latestToast != null)
        val toastText = ShadowToast.getTextOfLatestToast()
        assert(toastText == "Credentials saved successfully!")
    }

    @Test
    fun `saveCredentials with valid Tencent credentials should save successfully`() {
        // Create a mock for CloudCredentialsStorage
        val mockCredentialsStorage = MockSecureStorage()
        
        // Launch the test activity and inject the mock
        val scenario = ActivityScenario.launch(CredentialSetupActivity::class.java)
        scenario.onActivity { activity ->
            activity.setCredentialsStorage(mockCredentialsStorage)
            
            // Set up valid Tencent credentials
            val accessKeyId = "valid_tencent_access_key_id"
            val accessKeySecret = "valid_tencent_access_key_secret"

            val tencentAccessKeyIdEditText = activity.findViewById<TextInputEditText>(R.id.tencentAccessKeyIdEditText)
            val tencentAccessKeySecretEditText = activity.findViewById<TextInputEditText>(R.id.tencentAccessKeySecretEditText)
            val saveCredentialsButton = activity.findViewById<Button>(R.id.saveCredentialsButton)

            tencentAccessKeyIdEditText.setText(accessKeyId)
            tencentAccessKeySecretEditText.setText(accessKeySecret)

            // Click the save button
            saveCredentialsButton.performClick()
        }

        // Verify toast message
        val latestToast = ShadowToast.getLatestToast()
        assert(latestToast != null)
        val toastText = ShadowToast.getTextOfLatestToast()
        assert(toastText == "Credentials saved successfully!")
    }

    @Test
    fun `saveCredentials with empty credentials should show error message`() {
        // Create a mock for CloudCredentialsStorage
        val mockCredentialsStorage = MockSecureStorage()
        
        // Launch the test activity and inject the mock
        val scenario = ActivityScenario.launch(CredentialSetupActivity::class.java)
        scenario.onActivity { activity ->
            activity.setCredentialsStorage(mockCredentialsStorage)
            
            val saveCredentialsButton = activity.findViewById<Button>(R.id.saveCredentialsButton)

            // Click the save button without entering any credentials
            saveCredentialsButton.performClick()
        }

        // Verify error toast message
        val latestToast = ShadowToast.getLatestToast()
        assert(latestToast != null)
        val toastText = ShadowToast.getTextOfLatestToast()
        assert(toastText == "Please enter at least one set of credentials")
    }

    @Test
    fun `saveCredentials with both Alibaba and Tencent credentials should save both`() {
        // Create a mock for CloudCredentialsStorage
        val mockCredentialsStorage = MockSecureStorage()
        
        // Launch the test activity and inject the mock
        val scenario = ActivityScenario.launch(CredentialSetupActivity::class.java)
        scenario.onActivity { activity ->
            activity.setCredentialsStorage(mockCredentialsStorage)
            
            // Set up both Alibaba and Tencent credentials
            val alibabaAccessKeyId = "alibaba_key_id"
            val alibabaAccessKeySecret = "alibaba_key_secret"
            val tencentAccessKeyId = "tencent_key_id"
            val tencentAccessKeySecret = "tencent_key_secret"

            val alibabaAccessKeyIdEditText = activity.findViewById<TextInputEditText>(R.id.alibabaAccessKeyIdEditText)
            val alibabaAccessKeySecretEditText = activity.findViewById<TextInputEditText>(R.id.alibabaAccessKeySecretEditText)
            val tencentAccessKeyIdEditText = activity.findViewById<TextInputEditText>(R.id.tencentAccessKeyIdEditText)
            val tencentAccessKeySecretEditText = activity.findViewById<TextInputEditText>(R.id.tencentAccessKeySecretEditText)
            val saveCredentialsButton = activity.findViewById<Button>(R.id.saveCredentialsButton)

            alibabaAccessKeyIdEditText.setText(alibabaAccessKeyId)
            alibabaAccessKeySecretEditText.setText(alibabaAccessKeySecret)
            tencentAccessKeyIdEditText.setText(tencentAccessKeyId)
            tencentAccessKeySecretEditText.setText(tencentAccessKeySecret)

            // Click the save button
            saveCredentialsButton.performClick()
        }

        // Verify success toast message
        val latestToast = ShadowToast.getLatestToast()
        assert(latestToast != null)
        val toastText = ShadowToast.getTextOfLatestToast()
        assert(toastText == "Credentials saved successfully!")
    }
}