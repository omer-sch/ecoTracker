package com.example.greenapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation


class StartFragment : Fragment() {

    private var ConnectBtn: Button? = null
    private var RegisterBtn: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_start, container, false)
        setupUI(view)
        return view

    }
    private fun setupUI(view: View) {

        ConnectBtn = view.findViewById(R.id.Connectbtn)
        RegisterBtn = view.findViewById(R.id.Registerbtn)
        ConnectBtn?.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_startFragment_to_loginFragment)
        }
        RegisterBtn?.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_startFragment_to_registerFragment)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        super.onCreateOptionsMenu(menu, inflater)
    }

}