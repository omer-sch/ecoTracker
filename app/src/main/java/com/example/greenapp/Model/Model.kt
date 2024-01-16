package com.example.greenapp.Model

import android.os.Handler
import android.os.Looper
import androidx.core.os.HandlerCompat
import com.example.greenapp.dao.AppLocalDatabase
import java.util.concurrent.Executors

class Model private constructor() {

    private val database = AppLocalDatabase.db
    private var executor = Executors.newSingleThreadExecutor()
    private var mainHandler = HandlerCompat.createAsync(Looper.getMainLooper())

    companion object {
        val instance: Model = Model()
    }

    interface GetAllUsersListener {
        fun onComplete(users: List<User>)
    }

    fun getAllUsers(callback: (List<User>) -> Unit) {
        executor.execute {

            Thread.sleep(5000)

            val Users = database.userDao().getAll()
            mainHandler.post {
                // Main Thread
                callback(Users)
            }
        }
    }

    fun addUser(user: User, callback: () -> Unit) {
        executor.execute {
            database.userDao().insert(user)
            mainHandler.post {
                callback()
            }
        }
    }
}