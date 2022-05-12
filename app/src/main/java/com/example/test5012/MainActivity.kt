package com.example.test5012

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val username :String= intent.getStringExtra("user").toString()
        val pos :String= intent.getStringExtra("position").toString()
        setContentView(R.layout.activity_main)
        initView(username,pos)
    }
       private fun initView(user: String, pos: String){
            val mBtMainLogout = findViewById<Button>(R.id.bt_main_logout)
            val newProj = findViewById<Button>(R.id.bt_main_create_new_project)
            //val workerList = findViewById<TextView>(R.id.workerList)
            //workerList.text = user
            newProj.isEnabled = false
            if (pos == "manager"){
                newProj.isEnabled = true
            }       // get the layout and event
           var b = Bundle()
           b.putString("ProjectName","Name")
           b.putString("TeamMembers",user)
           b.putString("Status","On going")
           b.putString("TaskName","Do your job")
           val frag = mainVeiwFrag()
           frag.arguments = b
           showFragment(frag)
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
    fun showFragment(fragment:  mainVeiwFrag){
        val fram = supportFragmentManager.beginTransaction()
        fram.replace(R.id.fragmentContainerView,fragment)
        fram.commit()
    }

}
