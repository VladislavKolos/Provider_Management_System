package org.example.util

import java.util.UUID

object ProviderConstantUtil {

    val USER_STATUS_ACTIVE: UUID = UUID.fromString("60d632e7-1830-465b-8399-b53e51981cbc")
    val USER_STATUS_INACTIVE: UUID = UUID.fromString("a9d0b5e0-9f79-442e-a638-1a58cf09b199")
    val USER_STATUS_BANNED: UUID = UUID.fromString("3a1b1320-375f-4310-aaa5-093fd3067000")
    val ROLE_CLIENT: UUID = UUID.fromString("a7e1b9bb-70ee-464c-8e73-7fc3bd1ca65b")
    const val ADDITIONAL_MILLIS = 1000 * 60 * 24
    const val ADDITIONAL_MINUTES = 30
    const val TIME_OUT = 1
    const val SUBSCRIPTION_STATUS_SIGNED = "signed"
    const val SUBSCRIPTION_STATUS_NOT_SIGNED = "not signed"
}