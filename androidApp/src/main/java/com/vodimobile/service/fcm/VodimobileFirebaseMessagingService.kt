package com.vodimobile.service.fcm

import com.google.firebase.messaging.RemoteMessage

class VodimobileFirebaseMessagingService : com.google.firebase.messaging.FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

    }
}