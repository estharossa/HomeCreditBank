package com.example.homecreditbank.shop.presentation.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.homecreditbank.databinding.ActivityBankBinding

class BankActivity : AppCompatActivity() {
    companion object {

        fun start(context: Context) {
            val intent = Intent(context, BankActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityBankBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
    }
}