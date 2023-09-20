package com.akhenaton.scanrateitapp.common.repository

import com.akhenaton.scanrateitapp.common.repository.model.ReviewModel
import com.akhenaton.scanrateitapp.common.utils.await
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
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

    override suspend fun getUserReviews(): Resource<List<ReviewModel>> {
        return try {
            val result =
                firestoreDB.collection(REVIEWS_COLLECTION).whereEqualTo(USER_ID, user?.uid).get()
                    .await()
            val list = mapDocumentsToListReview(result.documents)
            Resource.Success(list)
        } catch (ex: Exception) {
            ex.printStackTrace()
            Resource.Failure(ex)
        }
    }

    private fun mapDocumentsToListReview(documents: MutableList<DocumentSnapshot>): List<ReviewModel> {
        val reviewList = mutableListOf<ReviewModel>()

        for (document in documents) {
            val dados = document.data
            dados?.apply {
                val ean = dados["ean"] as String
                val product = dados["product"] as String
                val userId = dados["userId"] as String
                val review = dados["review"] as String
                val rating = (dados["rating"] as Double).toFloat()

                val model = ReviewModel(ean, product, userId, review, rating)
                reviewList.add(model)
            }
        }
        return reviewList
    }

    companion object {
        private const val REVIEWS_COLLECTION = "reviews"
        private const val USER_ID = "userId"
    }
}
