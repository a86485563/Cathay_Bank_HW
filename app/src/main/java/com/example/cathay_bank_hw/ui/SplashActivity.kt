package com.example.cathay_bank_hw.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.example.cathay_bank_hw.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)

         Handler().postDelayed({

                //This method will be executed once the timer is over
                // Start your app main activity
                val i =  Intent(this, MainActivity::class.java);
                startActivity(i);
                // close this activity
                finish();

        }, 3000);
    }
}
