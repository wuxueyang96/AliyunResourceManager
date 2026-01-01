package life.wuxueyang

interface CloudCredentialsStorage {
    fun saveAlibabaCredentials(accessKeyId: String, accessKeySecret: String)
    fun getAlibabaCredentials(): Pair<String?, String?>
    fun saveTencentCredentials(accessKeyId: String, accessKeySecret: String)
    fun getTencentCredentials(): Pair<String?, String?>
    fun areCredentialsSet(): Boolean
    fun clearCredentials()
    fun savePin(pin: String)
    fun verifyPin(enteredPin: String): Boolean
    fun isPinSet(): Boolean
    fun clearPin()
}