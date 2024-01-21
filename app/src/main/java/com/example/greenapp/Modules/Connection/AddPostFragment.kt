package com.example.greenapp.Modules.Connection

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.Navigation
import com.example.greenapp.Model.Model
import com.example.greenapp.R
import com.example.greenapp.Model.Post
import com.example.greenapp.FeedFragment



class AddPostFragment : Fragment() {

    private var nameTextField: EditText? = null
   // private var idTextField: EditText? = null
   // private var messageTextView: TextView? = null
    private var saveButton: Button? = null
    private var cancelButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_post, container, false)
        setupUI(view)
        return view
    }

    private fun setupUI(view: View) {
        nameTextField = view.findViewById(R.id.etAddPostName)
        saveButton = view.findViewById(R.id.btnAddPostSave)
        cancelButton = view.findViewById(R.id.btnAddPostCancel)
       // messageTextView?.text = ""

        cancelButton?.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_addPostFragment_to_feedFragment)
        }

        saveButton?.setOnClickListener {
            val name = nameTextField?.text.toString()

            Model.instance.getCurrentUser {

                val post = Post(name,it[2] ,false)
                Model.instance.addPost(post) {
                    Navigation.findNavController(view)
                        .navigate(R.id.action_addPostFragment_to_feedFragment)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        super.onCreateOptionsMenu(menu, inflater)
    }

}