package com.example.test5012

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import org.w3c.dom.Text


class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var mDBOpenHelperProject: DBOpenHelperProject? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val username :String= intent.getStringExtra("user").toString()
        val pos :String= intent.getStringExtra("position").toString()
        setContentView(R.layout.activity_main)
        initView(username,pos)
        mDBOpenHelperProject = DBOpenHelperProject(this)
    }
    class FirebaseUtils {
        val fireStoreDatabase = FirebaseFirestore.getInstance()
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
           //var mlist = mDBOpenHelperProject?.getinfoByName(user)

           //var currentProj = mDBOpenHelperProject?.getProjByName(user)
           //var currentTask = mDBOpenHelperProject?.getTaskByName(user)
           //var currentDDL = mDBOpenHelperProject?.getDDLByName(user)
           //var currentState = mDBOpenHelperProject?.getStateByName(user)

           var b = Bundle()
           /////////////////////////////////////////////////////////////////////////////////////
           FirebaseUtils().fireStoreDatabase.collection("projects")
               .get()
               .addOnSuccessListener { querySnapshot ->
                   querySnapshot.forEach { document ->
                       Log.d(TAG, "Read document with ID ${document.id}")

                       if (document.data["worker"] == user) {
                           println(document.data)
                           //println(document.data["city"])
                           val projectNameView = findViewById<TextView>(R.id.ProjectName_mainView)
                           val teamMemberView = findViewById<TextView>(R.id.TeamMembers_mainView)
                           val stateView = findViewById<TextView>(R.id.status_mainView)
                           val taskView = findViewById<TextView>(R.id.TaskName_mainView)
                           var currentProj = document.data["projectName"]
                           var currentTask = document.data["task"]
                           var currentDDL = document.data["deadline"]
                           var currentState = document.data["state"]
                           var currentWorker = document.data["worker"]
                           println(currentProj)
                           //b.putString("ProjectName", currentProj.toString())
                           //b.putString("TeamMembers", user)
                           //b.putString("Status", currentState.toString())
                           //b.putString("TaskName", currentTask.toString())
                           projectNameView.text = currentProj as CharSequence?
                           teamMemberView.text = currentWorker as CharSequence?
                           stateView.text = currentState as CharSequence?
                           taskView.text = currentTask as CharSequence?

                       }
                   }
               }
               .addOnFailureListener { exception ->
                   Log.w(TAG, "Error getting documents $exception")
               }
//////////////////////////////////////////////////////////////////////////////////////

           val frag = mainVeiwFrag()
           frag.arguments = b
           showFragment(frag)
        mBtMainLogout.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
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
