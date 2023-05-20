package com.example.cathay_bank_hw.ui

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.cathay_bank_hw.R

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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }


}