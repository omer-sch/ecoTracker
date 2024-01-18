package com.example.greenapp.Modules.Connection

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.Navigation
import com.example.greenapp.Model.Model
import com.example.greenapp.Model.User
import com.example.greenapp.R


class RegisterFragment : Fragment() {

    private var nameTextField: EditText? = null
    private var passwordTextField: EditText?=null
    private var rePasswordTextField: EditText? = null
    private var emailTextField: EditText? = null
    //private var messageTextField: EditText? = null
    //private var cancelButton: Button? = null
    private var registerBtn: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_register, container, false)
        setupUI(view)
        return view
    }
    private fun setupUI(view: View) {

        registerBtn = view.findViewById(R.id.signupbtn)
        nameTextField = view.findViewById(R.id.username)
        passwordTextField = view.findViewById(R.id.password)
        emailTextField = view.findViewById(R.id.email)
        rePasswordTextField = view.findViewById(R.id.repassword)
        // cancelButton = view.findViewById(R.id.btnAddStudentCancel)
        //messageTextView?.text = ""

        registerBtn?.setOnClickListener {
            val name = nameTextField?.text.toString()
            val email=emailTextField?.text.toString()
            val password = passwordTextField?.text.toString()


            val user = User(name, email,password, false)
            Model.instance.addUser(user) {
                Navigation.findNavController(it).navigate(R.id.action_registerFragment_to_feedActivity)
            }
        }
    }


}