# Aliyun Resource Manager

A simple Android application to manage Alibaba Cloud ECS instances and EIPs.

## Features

- Start/Stop ECS instances
- Check ECS instance status
- Create/Release EIPs
- Bind/Unbind EIPs to/from instances

## Prerequisites

- Android Studio Flamingo or later
- Android device or emulator running API level 24 or higher
- Alibaba Cloud account with valid Access Key ID and Access Key Secret

## Setup Instructions

1. Clone or download this project
2. Open the project in Android Studio
3. Build and run the application on your device or emulator

## Usage

1. Enter your Alibaba Cloud credentials:
   - Access Key ID
   - Access Key Secret
   - Region ID (e.g., cn-hangzhou)

2. For ECS management:
   - Enter the Instance ID to perform operations
   - Use "Start Instance" or "Stop Instance" buttons
   - Use "Refresh Status" to check the current status

3. For EIP management:
   - Enter the EIP Allocation ID or IP address for operations
   - Specify bandwidth when creating an EIP (default is 5 Mbps)
   - Enter Instance ID when binding/unbinding EIPs

## Important Security Notice

Never hardcode your Access Key credentials in the application. This app stores credentials in memory only during the session. For production use, implement secure credential management.

## Dependencies

- Alibaba Cloud SDK for Java (Core, ECS, VPC)
- AndroidX libraries
- Kotlin Coroutines
- Material Design Components

## Permissions

The app requires the following permissions:
- INTERNET - to communicate with Alibaba Cloud APIs
- ACCESS_NETWORK_STATE - to check network connectivity
