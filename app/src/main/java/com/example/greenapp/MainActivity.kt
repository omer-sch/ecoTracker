package com.example.greenapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.greenapp.Model.Model

class MainActivity : AppCompatActivity() {


    private var navController: NavController? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment: NavHostFragment? = supportFragmentManager.findFragmentById(R.id.navHostMain) as? NavHostFragment
        navController = navHostFragment?.navController
        navController?.let { NavigationUI.setupActionBarWithNavController(this, it) }

//        val bottomNavigationView: BottomNavigationView = findViewById(R.id.profileActivityBottomNavigationView)
//        navController?.let { NavigationUI.setupWithNavController(bottomNavigationView, it) }
//        hideBottomNavigationView()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                navController?.navigateUp()
                true
            }
            R.id.btnProfileViewFragment->{

                navController?.navigate(R.id.action_feedFragment_to_profileViewFragment)
                true
            }
            R.id.btnLogout_menu->{
                Model.instance.signOut()
                navController?.navigate(R.id.action_global_startFragment)
                true
            }
            R.id.btnLogoutProfile->{
                Model.instance.signOut()
                navController?.navigate(R.id.action_global_startFragment)
                true
            }

            R.id.btnMyTips->{
                 navController?.navigate(R.id.action_profileViewFragment_to_myTipsFragment)
                true
            }
            R.id.btnMyGoals->{
                 navController?.navigate(R.id.action_profileViewFragment_to_myGoalsFragment)
                true
            }
            R.id.btnMyPosts->{
                navController?.navigate(R.id.action_profileViewFragment_to_myPostsFragment)
                true
            }

            else -> navController?.let { NavigationUI.onNavDestinationSelected(item, it) } ?: super.onOptionsItemSelected(item)
        }
    }

}

