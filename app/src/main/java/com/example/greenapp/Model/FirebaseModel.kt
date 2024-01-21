package com.example.greenapp.Model

import android.app.Activity
import android.widget.Toast
import com.example.greenapp.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.memoryCacheSettings
import com.google.firebase.firestore.persistentCacheSettings
import com.google.firebase.ktx.Firebase
import java.util.concurrent.Executors
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.greenapp.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult

class FirebaseModel {

    private val db = Firebase.firestore
    private var auth: FirebaseAuth

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
        auth = Firebase.auth
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

    fun addUser(user:User,activity:Activity, callback: (Boolean) -> Unit) {
        auth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    db.collection(USERS_COLLECTION_PATH).document(user.name).set(user.json).addOnSuccessListener {
                        callback(true)
                    }


                } else {
                    callback(false)

                }
            }
    }
    fun login(email:String,password:String,activity:Activity, callback: (Boolean) -> Unit){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    callback(true)

                    // Sign in success, update UI with the signed-in user's information

                } else {
                    callback(false)
                   // Toast.makeText(context, "Failed to login user.", Toast.LENGTH_SHORT).show()
                }
            }

    }
    fun signOut(){
        auth.signOut()
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

    fun getMyPosts(name: String, callback: (List<Post>?) -> Unit) {
        // Build a query to filter posts by username
        val query = db.collection(POSTS_COLLECTION_PATH)
            .whereEqualTo("name", name) // Assuming "username" is the field containing the name

        query.get().addOnCompleteListener {
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
    fun getUserByName(name: String, callback: (List<User>?) -> Unit) {
        // Build a query to filter posts by username
        val query = db.collection(USERS_COLLECTION_PATH)
            .whereEqualTo("name", name) // Assuming "username" is the field containing the name

        query.get().addOnCompleteListener {
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





