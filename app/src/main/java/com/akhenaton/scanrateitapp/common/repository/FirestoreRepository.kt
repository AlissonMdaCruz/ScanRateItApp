package com.akhenaton.scanrateitapp.common.repository

import com.akhenaton.scanrateitapp.common.repository.model.ReviewModel
import com.google.firebase.auth.FirebaseUser

interface FirestoreRepository {
    val user: FirebaseUser?
    suspend fun saveReview(review: ReviewModel): Resource<Boolean>
    suspend fun getUserReviews(): Resource<List<ReviewModel>>
}
