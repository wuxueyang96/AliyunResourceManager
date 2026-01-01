# Cloud Resource Manager

A simple Android application to manage Cloud Resources with biometric authentication, including Alibaba Cloud and Tencent Cloud resources such as ECS instances and EIPs.

## Features

- Biometric authentication (fingerprint/face) for secure login
- Encrypted storage of cloud credentials on the device
- Support for both Alibaba Cloud and Tencent Cloud
- Start/Stop ECS instances
- Check ECS instance status
- Create/Release EIPs
- Bind/Unbind EIPs to/from instances

## Prerequisites

- Android Studio Flamingo or later
- Android device or emulator running API level 24 or higher
- Alibaba Cloud and/or Tencent Cloud account with valid Access Key ID and Access Key Secret

## Setup Instructions

1. Clone or download this project
2. Open the project in Android Studio
3. Build and run the application on your device or emulator

## Usage

1. On first launch, configure your cloud credentials:
   - Access Key ID
   - Access Key Secret
   - For both Alibaba Cloud and Tencent Cloud

2. After credential setup, authenticate using biometric authentication (fingerprint/face)

3. Once authenticated, use the app to manage your cloud resources:
   - Enter your Alibaba Cloud credentials:
      - Access Key ID
      - Access Key Secret
      - Region ID (e.g., cn-hangzhou)

   - For ECS management:
      - Enter the Instance ID to perform operations
      - Use "Start Instance" or "Stop Instance" buttons
      - Use "Refresh Status" to check the current status

   - For EIP management:
      - Enter the EIP Allocation ID or IP address for operations
      - Specify bandwidth when creating an EIP (default is 5 Mbps)
      - Enter Instance ID when binding/unbinding EIPs

## Security Features

- Biometric authentication required for app access
- Cloud credentials encrypted using Android Keystore
- Secure storage using EncryptedSharedPreferences
- Credentials never stored in plain text

## Dependencies

- Alibaba Cloud SDK for Java (Core, ECS)
- AndroidX Biometric library
- AndroidX Security Crypto
- AndroidX libraries
- Kotlin Coroutines
- Material Design Components

## Permissions

The app requires the following permissions:
- INTERNET - to communicate with Cloud APIs
- ACCESS_NETWORK_STATE - to check network connectivity
- USE_BIOMETRIC - to authenticate using biometric methods