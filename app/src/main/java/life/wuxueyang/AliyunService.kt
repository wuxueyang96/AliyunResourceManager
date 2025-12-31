package life.wuxueyang

import com.aliyuncs.DefaultAcsClient
import com.aliyuncs.IAcsClient
import com.aliyuncs.ecs.model.v20140526.*
import com.aliyuncs.exceptions.ClientException
import com.aliyuncs.exceptions.ServerException
import com.aliyuncs.profile.DefaultProfile
import com.aliyuncs.vpc.model.v20160428.*

class AliyunService {
    private var client: IAcsClient? = null

    fun initClient(accessKeyId: String, accessKeySecret: String, regionId: String) {
        val profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret)
        client = DefaultAcsClient(profile)
    }

    // ECS Management Methods
    fun startInstance(instanceId: String): String {
        try {
            val request = StartInstanceRequest()
            request.instanceId = instanceId
            val response = client?.getAcsResponse(request)
            return "Start instance request sent successfully for instance: $instanceId"
        } catch (e: ServerException) {
            return "Server Exception: ${e.errorCode} - ${e.errorMessage}"
        } catch (e: ClientException) {
            return "Client Exception: ${e.errorCode} - ${e.errorMessage}"
        }
    }

    fun stopInstance(instanceId: String, forceStop: Boolean = false): String {
        try {
            val request = StopInstanceRequest()
            request.instanceId = instanceId
            request.forceStop = forceStop
            val response = client?.getAcsResponse(request)
            return "Stop instance request sent successfully for instance: $instanceId"
        } catch (e: ServerException) {
            return "Server Exception: ${e.errorCode} - ${e.errorMessage}"
        } catch (e: ClientException) {
            return "Client Exception: ${e.errorCode} - ${e.errorMessage}"
        }
    }

    fun getInstanceStatus(instanceId: String): String {
        try {
            val request = DescribeInstanceStatusRequest()
            request.instanceIds = listOf(instanceId)
            val response = client?.getAcsResponse(request)
            
            if (response?.instanceStatusSet?.isNotEmpty() == true) {
                val status = response.instanceStatusSet[0].status
                return "Instance $instanceId status: $status"
            } else {
                return "Instance $instanceId not found"
            }
        } catch (e: ServerException) {
            return "Server Exception: ${e.errorCode} - ${e.errorMessage}"
        } catch (e: ClientException) {
            return "Client Exception: ${e.errorCode} - ${e.errorMessage}"
        }
    }

    // EIP Management Methods
    fun createEip(bandwidth: Int = 5): String {
        try {
            val request = AllocateEipAddressRequest()
            request.bandwidth = bandwidth.toString()
            val response = client?.getAcsResponse(request)
            
            return "EIP created successfully: ${response?.eipAddress} with AllocationId: ${response?.allocationId}"
        } catch (e: ServerException) {
            return "Server Exception: ${e.errorCode} - ${e.errorMessage}"
        } catch (e: ClientException) {
            return "Client Exception: ${e.errorCode} - ${e.errorMessage}"
        }
    }

    fun releaseEip(allocationId: String): String {
        try {
            val request = ReleaseEipAddressRequest()
            request.allocationId = allocationId
            val response = client?.getAcsResponse(request)
            return "EIP with AllocationId $allocationId released successfully"
        } catch (e: ServerException) {
            return "Server Exception: ${e.errorCode} - ${e.errorMessage}"
        } catch (e: ClientException) {
            return "Client Exception: ${e.errorCode} - ${e.errorMessage}"
        }
    }

    fun bindEipToInstance(allocationId: String, instanceId: String): String {
        try {
            val request = AssociateEipAddressRequest()
            request.allocationId = allocationId
            request.instanceId = instanceId
            request.instanceType = "EcsInstance"
            val response = client?.getAcsResponse(request)
            return "EIP with AllocationId $allocationId bound to instance $instanceId successfully"
        } catch (e: ServerException) {
            return "Server Exception: ${e.errorCode} - ${e.errorMessage}"
        } catch (e: ClientException) {
            return "Client Exception: ${e.errorCode} - ${e.errorMessage}"
        }
    }

    fun unbindEipFromInstance(allocationId: String, instanceId: String): String {
        try {
            val request = UnassociateEipAddressRequest()
            request.allocationId = allocationId
            request.instanceId = instanceId
            request.instanceType = "EcsInstance"
            val response = client?.getAcsResponse(request)
            return "EIP with AllocationId $allocationId unbound from instance $instanceId successfully"
        } catch (e: ServerException) {
            return "Server Exception: ${e.errorCode} - ${e.errorMessage}"
        } catch (e: ClientException) {
            return "Client Exception: ${e.errorCode} - ${e.errorMessage}"
        }
    }

    fun getEipInfo(allocationId: String): String {
        try {
            val request = DescribeEipAddressesRequest()
            request.allocationId = allocationId
            val response = client?.getAcsResponse(request)
            
            if (response?.eipAddresses?.eipAddress?.isNotEmpty() == true) {
                val eipInfo = response.eipAddresses.eipAddress[0]
                return "EIP: ${eipInfo.ipAddress}, Status: ${eipInfo.status}, Instance: ${eipInfo.instanceId}"
            } else {
                return "EIP with AllocationId $allocationId not found"
            }
        } catch (e: ServerException) {
            return "Server Exception: ${e.errorCode} - ${e.errorMessage}"
        } catch (e: ClientException) {
            return "Client Exception: ${e.errorCode} - ${e.errorMessage}"
        }
    }
}