package com.example.myapplication.data.profile

import com.example.myapplication.data.model.ProfileData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : ProfileRepository {

    override suspend fun loadProfile(): Result<ProfileData> =
        runCatching {
            val user = auth.currentUser ?: error("User not logged in")

            val doc = firestore
                .collection("users")
                .document(user.uid)
                .get()
                .await()

            ProfileData(
                name = doc.getString("name").orEmpty(),
                email = user.email.orEmpty()
            )
        }

    override suspend fun logout() {
        auth.signOut()
    }

    override suspend fun deleteAccount(): Result<Unit> =
        runCatching {
            val user = auth.currentUser ?: error("User not logged in")

            firestore.collection("users")
                .document(user.uid)
                .delete()
                .await()

            user.delete().await()
        }
}
