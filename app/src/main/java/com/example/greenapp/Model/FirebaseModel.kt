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
        const val POSTS_COLLECTION_PATH = "posts"
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
    fun getAllPosts(callback: (List<Post>) -> Unit){
        db.collection(POSTS_COLLECTION_PATH).get().addOnCompleteListener {
            when (it.isSuccessful) {
                true -> {
                    val posts: MutableList<Post> = mutableListOf()
                    for (json in it.result) {
                        val post = Post.fromJSON(json.data)
                        posts.add(post)
                    }
                    callback(posts)
                }
                false -> callback(listOf())
            }
        }
    }
    fun addPost(post: Post, callback: () -> Unit) {
        db.collection(POSTS_COLLECTION_PATH).document(post.name).set(post.json).addOnSuccessListener {
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




