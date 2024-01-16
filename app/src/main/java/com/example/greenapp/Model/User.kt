package com.example.greenapp.Model

import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val name: String,
    val password: String,
    val email: String,
    var isChecked: Boolean)
