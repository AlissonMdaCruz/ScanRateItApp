package com.akhenaton.scanrateitapp.common.repository

import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    val currentUser: FirebaseUser?
    suspend fun login(email: String, password: String): Resource<FirebaseUser>
    suspend fun signup(name: String, email: String, password: String): Resource<FirebaseUser>
    suspend fun recoverAccess(email: String): Resource<Boolean>
    suspend fun changeData(password: String, newPassword: String): Resource<Boolean>
    fun logout()
}