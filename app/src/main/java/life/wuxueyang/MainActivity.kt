package life.wuxueyang

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    
    private lateinit var accessKeyIdEditText: TextInputEditText
    private lateinit var accessKeySecretEditText: TextInputEditText
    private lateinit var regionIdEditText: TextInputEditText
    private lateinit var instanceIdEditText: TextInputEditText
    private lateinit var eipAllocationIdEditText: TextInputEditText
    private lateinit var bandwidthEditText: TextInputEditText
    private lateinit var instanceIdForBindEditText: TextInputEditText
    private lateinit var resultTextView: TextView
    
    private lateinit var startInstanceButton: Button
    private lateinit var stopInstanceButton: Button
    private lateinit var refreshStatusButton: Button
    private lateinit var createEipButton: Button
    private lateinit var releaseEipButton: Button
    private lateinit var bindEipButton: Button
    private lateinit var unbindEipButton: Button
    
    private val aliyunService = AliyunService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setupClickListeners()
    }

    private fun initViews() {
        accessKeyIdEditText = findViewById(R.id.accessKeyIdEditText)
        accessKeySecretEditText = findViewById(R.id.accessKeySecretEditText)
        regionIdEditText = findViewById(R.id.regionIdEditText)
        instanceIdEditText = findViewById(R.id.instanceIdEditText)
        eipAllocationIdEditText = findViewById(R.id.eipAllocationIdEditText)
        bandwidthEditText = findViewById(R.id.bandwidthEditText)
        instanceIdForBindEditText = findViewById(R.id.instanceIdForBindEditText)
        resultTextView = findViewById(R.id.resultTextView)

        startInstanceButton = findViewById(R.id.startInstanceButton)
        stopInstanceButton = findViewById(R.id.stopInstanceButton)
        refreshStatusButton = findViewById(R.id.refreshStatusButton)
        createEipButton = findViewById(R.id.createEipButton)
        releaseEipButton = findViewById(R.id.releaseEipButton)
        bindEipButton = findViewById(R.id.bindEipButton)
        unbindEipButton = findViewById(R.id.unbindEipButton)
    }

    private fun setupClickListeners() {
        startInstanceButton.setOnClickListener {
            performAction {
                val instanceId = instanceIdEditText.text.toString().trim()
                if (instanceId.isEmpty()) {
                    Utils.showToast(this, "Please enter Instance ID")
                    return@performAction
                }
                val result = aliyunService.startInstance(instanceId)
                updateResult(ResultFormatter.formatSuccess(result))
            }
        }

        stopInstanceButton.setOnClickListener {
            performAction {
                val instanceId = instanceIdEditText.text.toString().trim()
                if (instanceId.isEmpty()) {
                    Utils.showToast(this, "Please enter Instance ID")
                    return@performAction
                }
                val result = aliyunService.stopInstance(instanceId)
                updateResult(ResultFormatter.formatSuccess(result))
            }
        }

        refreshStatusButton.setOnClickListener {
            performAction {
                val instanceId = instanceIdEditText.text.toString().trim()
                if (instanceId.isEmpty()) {
                    Utils.showToast(this, "Please enter Instance ID")
                    return@performAction
                }
                val result = aliyunService.getInstanceStatus(instanceId)
                updateResult(ResultFormatter.formatInfo(result))
            }
        }

        createEipButton.setOnClickListener {
            performAction {
                val bandwidthText = bandwidthEditText.text.toString().trim()
                val bandwidth = if (bandwidthText.isNotEmpty()) bandwidthText.toInt() else 5
                val result = aliyunService.createEip(bandwidth)
                updateResult(ResultFormatter.formatSuccess(result))
            }
        }

        releaseEipButton.setOnClickListener {
            performAction {
                val allocationId = eipAllocationIdEditText.text.toString().trim()
                if (allocationId.isEmpty()) {
                    Utils.showToast(this, "Please enter EIP Allocation ID")
                    return@performAction
                }
                val result = aliyunService.releaseEip(allocationId)
                updateResult(ResultFormatter.formatSuccess(result))
            }
        }

        bindEipButton.setOnClickListener {
            performAction {
                val allocationId = eipAllocationIdEditText.text.toString().trim()
                val instanceId = instanceIdForBindEditText.text.toString().trim()
                
                if (allocationId.isEmpty()) {
                    Utils.showToast(this, "Please enter EIP Allocation ID")
                    return@performAction
                }
                
                if (instanceId.isEmpty()) {
                    Utils.showToast(this, "Please enter Instance ID to bind")
                    return@performAction
                }
                
                val result = aliyunService.bindEipToInstance(allocationId, instanceId)
                updateResult(ResultFormatter.formatSuccess(result))
            }
        }

        unbindEipButton.setOnClickListener {
            performAction {
                val allocationId = eipAllocationIdEditText.text.toString().trim()
                val instanceId = instanceIdForBindEditText.text.toString().trim()
                
                if (allocationId.isEmpty()) {
                    Utils.showToast(this, "Please enter EIP Allocation ID")
                    return@performAction
                }
                
                if (instanceId.isEmpty()) {
                    Utils.showToast(this, "Please enter Instance ID to unbind")
                    return@performAction
                }
                
                val result = aliyunService.unbindEipFromInstance(allocationId, instanceId)
                updateResult(ResultFormatter.formatSuccess(result))
            }
        }
    }

    private fun performAction(action: suspend () -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            // Check network availability
            if (!Utils.isNetworkAvailable(this@MainActivity)) {
                Utils.showToast(this@MainActivity, "No internet connection available")
                updateResult(ResultFormatter.formatError("No internet connection"))
                return@launch
            }
            
            // Validate credentials first
            val accessKeyId = accessKeyIdEditText.text.toString().trim()
            val accessKeySecret = accessKeySecretEditText.text.toString().trim()
            val regionId = regionIdEditText.text.toString().trim()

            if (accessKeyId.isEmpty() || accessKeySecret.isEmpty() || regionId.isEmpty()) {
                Utils.showToast(this@MainActivity, "Please enter Access Key ID, Access Key Secret, and Region ID")
                return@launch
            }

            // Initialize the client
            try {
                aliyunService.initClient(accessKeyId, accessKeySecret, regionId)
            } catch (e: Exception) {
                updateResult(ResultFormatter.formatError("Error initializing client: ${e.message}"))
                return@launch
            }

            // Show loading
            updateResult(ResultFormatter.formatInfo("Loading..."))

            // Execute the action in background
            withContext(Dispatchers.IO) {
                try {
                    action()
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        updateResult(ResultFormatter.formatError("Error: ${e.message}"))
                    }
                }
            }
        }
    }

    private fun updateResult(result: String) {
        runOnUiThread {
            resultTextView.append("\n$result")
        }
    }
}