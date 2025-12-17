package com.example.myapplication.data.repository

import com.example.myapplication.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import com.example.myapplication.domain.model.User
import javax.inject.Inject

class FirebaseAuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {

    override val currentUser: Flow<User?> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser?.toDomain())
        }

        firebaseAuth.addAuthStateListener(listener)
        trySend(firebaseAuth.currentUser?.toDomain())

        awaitClose {
            firebaseAuth.removeAuthStateListener(listener)
        }
    }

    override suspend fun signInWithGoogle(idToken: String): User {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        val result = firebaseAuth.signInWithCredential(credential).await()

        val user = result.user ?: throw IllegalStateException("Firebase user is null")

        // Save user if first login
        saveUserIfNotExists(user)

        return user.toDomain()
    }

    override suspend fun signUpWithEmail(
        email: String,
        password: String,
        name: String,
    ): User {
        val result = firebaseAuth
            .createUserWithEmailAndPassword(email, password)
            .await()

        val user = result.user ?: throw IllegalStateException("User is null")

        // Save displayName to FirebaseAuth
        val profile = UserProfileChangeRequest.Builder()
            .setDisplayName(name)
            .build()

        user.updateProfile(profile).await()

        // Save to Firestore (THIS IS THE IMPORTANT PART)
        val userMap = mapOf(
            "uid" to user.uid,
            "name" to name,
            "email" to email,
        )

        firestore.collection("users")
            .document(user.uid)
            .set(userMap)
            .await()

        return user.toDomain()
    }

    override suspend fun signInWithEmail(
        email: String,
        password: String
    ): User {
        val result = firebaseAuth
            .signInWithEmailAndPassword(email, password)
            .await()

        return result.user?.toDomain()
            ?: throw IllegalStateException("User is null")
    }

    override suspend fun signOut() {
        firebaseAuth.signOut()
    }

    private suspend fun saveUserIfNotExists(user: FirebaseUser) {
        val ref = firestore.collection("users").document(user.uid)
        val snapshot = ref.get().await()

        if (!snapshot.exists()) {
            val userMap = mapOf(
                "uid" to user.uid,
                "name" to (user.displayName ?: ""),
                "email" to user.email
            )
            ref.set(userMap).await()
        }
    }

    private fun FirebaseUser.toDomain(): User =
        User(
            uid = uid,
            email = email,
            displayName = displayName,
            photoUrl = photoUrl?.toString()
        )
}

