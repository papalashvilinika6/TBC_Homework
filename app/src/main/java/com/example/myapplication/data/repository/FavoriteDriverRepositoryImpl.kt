package com.example.myapplication.data.repository

import com.example.myapplication.domain.repository.FavoriteDriverRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FavoriteDriverRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : FavoriteDriverRepository {

    companion object {
        private const val COLLECTION_USERS = "users"
        private const val FIELD_FAVORITE_DRIVER_ID = "favoriteDriverId"
    }

    override suspend fun saveFavoriteDriver(userId: String, driverId: Int): Result<Unit> {
        return try {
            firestore.collection(COLLECTION_USERS)
                .document(userId)
                .set(mapOf(FIELD_FAVORITE_DRIVER_ID to driverId), com.google.firebase.firestore.SetOptions.merge())
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getFavoriteDriverId(): Int? {
        val uid = auth.currentUser?.uid ?: return null

        val snapshot = firestore
            .collection("users")
            .document(uid)
            .get()
            .await()

        return snapshot.getLong("favoriteDriverId")?.toInt()
    }

    override suspend fun removeFavoriteDriver(userId: String): Result<Unit> {
        return try {
            firestore.collection(COLLECTION_USERS)
                .document(userId)
                .update(FIELD_FAVORITE_DRIVER_ID, FieldValue.delete())
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}

