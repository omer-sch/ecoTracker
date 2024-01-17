package com.example.greenapp.Model

import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.memoryCacheSettings
import com.google.firebase.firestore.persistentCacheSettings
import com.google.firebase.ktx.Firebase

class FirebaseModel {

    private val db = Firebase.firestore

    companion object {
        const val USERS_COLLECTION_PATH = "users"
    }

    init {
        val settings = firestoreSettings {
            setLocalCacheSettings(memoryCacheSettings {  })
//            setLocalCacheSettings(persistentCacheSettings {  })
        }
        db.firestoreSettings = settings
    }


    fun getAllUsers(callback: (List<User>) -> Unit) {
        db.collection(USERS_COLLECTION_PATH).get().addOnCompleteListener {
            when (it.isSuccessful) {
                true -> {
                    val users: MutableList<User> = mutableListOf()
                    for (json in it.result) {
                        val user = User.fromJSON(json.data)
                        users.add(user)
                    }
                    callback(users)
                }
                false -> callback(listOf())
            }
        }
    }

    fun addUser(user: User, callback: () -> Unit) {
        db.collection(USERS_COLLECTION_PATH).document(user.name).set(user.json).addOnSuccessListener {
            callback()
        }
    }
//    fun getUserByName(name: String, callback: (User?) -> Unit) {
//        db.collection(USERS_COLLECTION_PATH)
//            .whereEqualTo("name", name)  // Query for documents where name matches
//            .get()
//            .addOnCompleteListener {
//                if (it.isSuccessful) {
//                    val user = it.result?.documents?.firstOrNull()?.toObject(User::class.java)
//                    callback(user)
//                } else {
//                    callback(null)  // Handle errors gracefully
//                }
//            }
//    }
}




