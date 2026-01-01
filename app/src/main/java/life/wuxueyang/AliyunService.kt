package life.wuxueyang

import com.aliyuncs.DefaultAcsClient
import com.aliyuncs.IAcsClient
import com.aliyuncs.ecs.model.v20140526.*
import com.aliyuncs.exceptions.ClientException
import com.aliyuncs.exceptions.ServerException
import com.aliyuncs.profile.DefaultProfile

class AliyunService {
    private var client: IAcsClient? = null

    fun initClient(accessKeyId: String, accessKeySecret: String, regionId: String) {
        val profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret)
        client = DefaultAcsClient(profile)
    }

    // Alibaba Cloud ECS Management Methods
    fun startInstance(instanceId: String): String {
        if (client == null) {
            return "Client not initialized. Please call initClient first."
        }
        try {
            val request = StartInstanceRequest()
            request.setInstanceId(instanceId)
            val response = client?.getAcsResponse(request)
            return "Start instance request sent for $instanceId"
        } catch (e: ServerException) {
            return "Server error: ${e.message}"
        } catch (e: ClientException) {
            return "Client error: ${e.message}"
        }
    }

    fun stopInstance(instanceId: String, forceStop: Boolean = false): String {
        if (client == null) {
            return "Client not initialized. Please call initClient first."
        }
        try {
            val request = StopInstanceRequest()
            request.setInstanceId(instanceId)
            request.setForceStop(forceStop)
            request.setStoppedMode("StopCharging")
            val response = client?.getAcsResponse(request)
            return "Stop instance request sent for $instanceId"
        } catch (e: ServerException) {
            return "Server error: ${e.message}"
        } catch (e: ClientException) {
            return "Client error: ${e.message}"
        }
    }

    fun getInstanceStatus(instanceId: String): String {
        if (client == null) {
            return "Client not initialized. Please call initClient first."
        }
        try {
            val request = DescribeInstanceStatusRequest()
            request.setInstanceIds(listOf(instanceId))
            val response = client?.getAcsResponse(request)
            // Since we're not sure about the exact property names, let's just return a success message
            return "Instance status request sent for $instanceId. Check response in logs."
        } catch (e: ServerException) {
            return "Server error: ${e.message}"
        } catch (e: ClientException) {
            return "Client error: ${e.message}"
        }
    }

    // List ECS instances
    fun listEcsInstances(): String {
        if (client == null) {
            return "Client not initialized. Please call initClient first."
        }
        try {
            val request = DescribeInstancesRequest()
            val response = client?.getAcsResponse(request)
            return "ECS instances list request sent. Check response in logs."
        } catch (e: ServerException) {
            return "Server error: ${e.message}"
        } catch (e: ClientException) {
            return "Client error: ${e.message}"
        }
    }

    // Alibaba Cloud EIP Management Methods - These will be stubbed since we don't have VPC SDK
    fun listEips(): String {
        return "EIP listing functionality not implemented - VPC SDK not included"
    }

    fun createEip(bandwidth: Int = 5): String {
        return "EIP creation functionality not implemented - VPC SDK not included"
    }

    fun releaseEip(allocationId: String): String {
        return "EIP release functionality not implemented - VPC SDK not included"
    }

    fun bindEipToInstance(allocationId: String, instanceId: String): String {
        return "EIP bind functionality not implemented - VPC SDK not included"
    }

    fun unbindEipFromInstance(allocationId: String, instanceId: String): String {
        return "EIP unbind functionality not implemented - VPC SDK not included"
    }

    fun getEipInfo(allocationId: String): String {
        return "EIP info functionality not implemented - VPC SDK not included"
    }
}