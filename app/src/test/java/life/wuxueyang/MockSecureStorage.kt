package life.wuxueyang

class MockSecureStorage : CloudCredentialsStorage {
    private var alibabaAccessKeyId: String? = null
    private var alibabaAccessKeySecret: String? = null
    private var tencentAccessKeyId: String? = null
    private var tencentAccessKeySecret: String? = null
    private var credentialsSet = false
    
    override fun saveAlibabaCredentials(accessKeyId: String, accessKeySecret: String) {
        this.alibabaAccessKeyId = accessKeyId
        this.alibabaAccessKeySecret = accessKeySecret
        this.credentialsSet = true
    }
    
    override fun getAlibabaCredentials(): Pair<String?, String?> {
        return Pair(alibabaAccessKeyId, alibabaAccessKeySecret)
    }
    
    override fun saveTencentCredentials(accessKeyId: String, accessKeySecret: String) {
        this.tencentAccessKeyId = accessKeyId
        this.tencentAccessKeySecret = accessKeySecret
        this.credentialsSet = true
    }
    
    override fun getTencentCredentials(): Pair<String?, String?> {
        return Pair(tencentAccessKeyId, tencentAccessKeySecret)
    }
    
    override fun areCredentialsSet(): Boolean {
        return credentialsSet
    }
    
    override fun clearCredentials() {
        alibabaAccessKeyId = null
        alibabaAccessKeySecret = null
        tencentAccessKeyId = null
        tencentAccessKeySecret = null
        credentialsSet = false
    }
    
    override fun savePin(pin: String) {
        // Mock implementation - do nothing for now
    }
    
    override fun verifyPin(enteredPin: String): Boolean {
        return false // Mock implementation
    }
    
    override fun isPinSet(): Boolean {
        return false // Mock implementation
    }
    
    override fun clearPin() {
        // Mock implementation - do nothing
    }
}