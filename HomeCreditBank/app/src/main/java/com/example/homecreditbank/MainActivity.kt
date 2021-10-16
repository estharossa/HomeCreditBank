package com.example.homecreditbank

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.homecreditbank.shop.presentation.activity.BankActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler().postDelayed({
            BankActivity.start(this)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }, 2000)
    }
}