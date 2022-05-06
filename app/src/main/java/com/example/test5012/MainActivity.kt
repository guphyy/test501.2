package com.example.test5012

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    private fun initView() {
        val mBtMainLogout = findViewById<Button>(R.id.bt_main_logout)
        // get the layout and event
        mBtMainLogout.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id){
            R.id.bt_main_logout -> {
                val intent = Intent(this, LoginActivity::class.java)
                // when touch the btn, get back
                startActivity(intent)
                finish()
            }
            R.id.bt_main_create_new_project -> {
                val intent2 = Intent(this, RegisterActivityProject::class.java)
                // when touch the btn, go to create new project page
                startActivity(intent2)
                finish()
            }



        }
    }
}