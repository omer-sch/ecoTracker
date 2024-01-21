package com.example.greenapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.greenapp.Model.Model
import com.example.greenapp.Model.User

class ProfileViewFragment : Fragment() {

    private var messageTextView: TextView? = null
    private var emailTextView: TextView? = null
    private var nameTextView: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view= inflater.inflate(R.layout.fragment_profile_view, container, false)
        setupUI(view)
        return view
    }
    private fun setupUI(view: View) {
        var user: User?=null

         Model.instance.getCurrentUser{
             Toast.makeText(context, " we got here.", Toast.LENGTH_SHORT).show()
             var email= it[1]
             var name= it[0]
             nameTextView=view.findViewById(R.id.name)
             emailTextView=view.findViewById(R.id.email)
             nameTextView?.text = name
             emailTextView?.text = email
        }



    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.profile_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }




}