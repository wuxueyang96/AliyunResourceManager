package life.wuxueyang

class CloudServiceManager {
    private val aliyunService = AliyunService()
    private val tencentService = TencentService()

    fun initAliyunClient(accessKeyId: String, accessKeySecret: String, regionId: String) {
        aliyunService.initClient(accessKeyId, accessKeySecret, regionId)
    }

    fun initTencentClient(accessKeyId: String, accessKeySecret: String, regionId: String) {
        tencentService.initClient(accessKeyId, accessKeySecret, regionId)
    }

    // Alibaba Cloud ECS Management Methods
    fun startAliyunInstance(instanceId: String): String {
        return aliyunService.startInstance(instanceId)
    }

    fun stopAliyunInstance(instanceId: String, forceStop: Boolean = false): String {
        return aliyunService.stopInstance(instanceId, forceStop)
    }

    fun getAliyunInstanceStatus(instanceId: String): String {
        return aliyunService.getInstanceStatus(instanceId)
    }

    // List ECS instances
    fun listAliyunEcsInstances(): String {
        return aliyunService.listEcsInstances()
    }

    // Alibaba Cloud EIP Management Methods
    fun listAliyunEips(): String {
        return aliyunService.listEips()
    }

    fun createAliyunEip(bandwidth: Int = 5): String {
        return aliyunService.createEip(bandwidth)
    }

    fun releaseAliyunEip(allocationId: String): String {
        return aliyunService.releaseEip(allocationId)
    }

    fun bindAliyunEipToInstance(allocationId: String, instanceId: String): String {
        return aliyunService.bindEipToInstance(allocationId, instanceId)
    }

    fun unbindAliyunEipFromInstance(allocationId: String, instanceId: String): String {
        return aliyunService.unbindEipFromInstance(allocationId, instanceId)
    }

    fun getAliyunEipInfo(allocationId: String): String {
        return aliyunService.getEipInfo(allocationId)
    }

    // Tencent Cloud Methods (placeholder implementation)
    fun startTencentInstance(instanceId: String): String {
        return tencentService.startInstance(instanceId)
    }

    fun stopTencentInstance(instanceId: String): String {
        return tencentService.stopInstance(instanceId)
    }

    fun getTencentInstanceStatus(instanceId: String): String {
        return tencentService.getInstanceStatus(instanceId)
    }

    fun createTencentEip(bandwidth: Int = 5): String {
        return tencentService.createEip(bandwidth)
    }

    fun releaseTencentEip(allocationId: String): String {
        return tencentService.releaseEip(allocationId)
    }
    
    // Method to bind EIP to DNS for Tencent Cloud
    fun bindEipToTencentDns(domain: String, eip: String): String {
        // This is a placeholder - in a real implementation, you would call Tencent Cloud DNS API
        return "Tencent DNS binding functionality would bind $eip to domain $domain (not implemented)"
    }
}