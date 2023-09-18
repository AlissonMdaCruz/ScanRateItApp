package com.akhenaton.scanrateitapp.common.repository

import com.akhenaton.scanrateitapp.common.repository.model.ReviewModel
import com.akhenaton.scanrateitapp.common.utils.await
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirestoreRepositoryImpl : FirestoreRepository {

    var firestoreDB = Firebase.firestore

    override val user: FirebaseUser?
        get() = FirebaseAuth.getInstance().currentUser

    override suspend fun saveReview(review: ReviewModel): Resource<Boolean> {
        return try {
            firestoreDB.collection(REVIEWS_COLLECTION)
                .add(review).await()
            Resource.Success(true)
        } catch (ex: Exception) {
            ex.printStackTrace()
            Resource.Failure(ex)
        }
    }

    companion object {
        private const val REVIEWS_COLLECTION = "reviews"
    }
}
