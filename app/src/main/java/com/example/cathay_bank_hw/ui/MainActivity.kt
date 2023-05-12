package com.example.cathay_bank_hw.ui

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cathay_bank_hw.R
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration : AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //find host fragment
        val host: NavHostFragment = supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment
        // Set up Action Bar
        val navController = host.navController

        appBarConfiguration = AppBarConfiguration(navController.graph)

        //find toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar).apply {
            setupWithNavController(
                navController,appBarConfiguration
            )
        }

        //設定進去，在fragment分享
        setSupportActionBar(toolbar)

        NavigationUI.setupWithNavController(toolbar, host.navController,appBarConfiguration)

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val var1 = item.onNavDestinationSelected(findNavController(R.id.navHost))
        val var2 = super.onOptionsItemSelected(item)

        return  var1 || var2
    }

}