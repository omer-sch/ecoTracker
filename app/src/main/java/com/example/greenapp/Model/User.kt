package com.example.greenapp.Model

import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val name: String,
    val id:String,
    val email: String,
    val password: String,
    var isChecked: Boolean) {


    companion object {
        const val NAME_KEY = "name"
        const val ID_KEY="id"
        const val EMAIL_KEY = "email"
        const val PASSWORD_KEY = "password"
        const val IS_CHECKED_KEY = "isChecked"

        fun fromJSON(json: Map<String, Any>): User {

            val name = json[NAME_KEY] as? String ?: ""
            val id=json[ID_KEY] as? String ?: ""
            val email = json[EMAIL_KEY] as? String ?: ""
            val password = json[PASSWORD_KEY] as? String ?: ""
            val isChecked = json[IS_CHECKED_KEY] as? Boolean ?: false
            return User(id,name, email, password, isChecked)
        }
    }

    val json: Map<String, Any>
        get() {
            return hashMapOf(
                NAME_KEY to name,
                ID_KEY to id,
                EMAIL_KEY to email,
                PASSWORD_KEY to password,
                IS_CHECKED_KEY to isChecked
            )
        }
}