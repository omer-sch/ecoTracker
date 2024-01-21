package com.example.greenapp.Model

import android.app.Activity
import android.os.Looper
import androidx.core.os.HandlerCompat
import com.example.greenapp.dao.AppLocalDatabase
import java.util.concurrent.Executors

class Model private constructor() {

    private val database = AppLocalDatabase.db
    private var executor = Executors.newSingleThreadExecutor()
    private var mainHandler = HandlerCompat.createAsync(Looper.getMainLooper())
    private val firebaseModel = FirebaseModel()


    companion object {
        val instance: Model = Model()
    }

    interface GetAllUsersListener {
        fun onComplete(users: List<User>)
    }
    interface GetAllPostsListener{
        fun onComplete(posts:List<Post>)
    }

    fun getAllUsers(callback: (List<User>) -> Unit) {
        firebaseModel.getAllUsers(callback)
//        executor.execute {
//
//            Thread.sleep(5000)
//
//            val Users = database.userDao().getAll()
//            mainHandler.post {
//                // Main Thread
//                callback(Users)
//            }
//        }
    }

    fun addUser(user:User,activity: Activity ,callback: (Boolean) -> Unit) {

        firebaseModel.addUser(user,activity ,callback)
//        executor.execute {
//            database.userDao().insert(user)
//            mainHandler.post {
//                callback()
//            }
//        }
    }
    fun login(email:String,password:String,activity: Activity ,callback: (Boolean) -> Unit){
        firebaseModel.login(email,password,activity ,callback)
    }
    fun signOut(){
        firebaseModel.signOut()
    }

//     fun login(name:String,password:String){
//        auth.createUserWithEmailAndPassword(name, password)
//            .addOnCompleteListener(executor
//            ) { task ->
//                if (task.isSuccessful) {
//                    // Sign in success, update UI with the signed-in user's information
//
//                } else {
//                    // If sign in fails, display a message to the user.
//
//                }
//            }
//
//    }






    fun getUserByName(name:String,callback: (List<User>?) -> Unit){
        firebaseModel.getUserByName(name,callback)
    }
    fun getAllPosts(callback: ( List<Post>?) -> Unit){
        firebaseModel.getAllPosts(callback)
    }
    fun getMyPosts(name:String,callback: (List<Post>?) -> Unit){
        firebaseModel.getMyPosts(name,callback)
    }
    fun addPost(post: Post, callback: () -> Unit){
        firebaseModel.addPost(post, callback)
    }
}