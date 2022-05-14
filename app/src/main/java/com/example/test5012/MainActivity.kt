package com.example.test5012

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.LinearLayout.VERTICAL
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.firebase.firestore.FirebaseFirestore


data class Project(
    val worker: List<String> ,
    val task: List<String>,
    val state: List<String>,
    val deadline:String = "",
    val projectName:String = ""
)

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
    private fun testWorker(map: Map<*,*>,user: String): Boolean {
        val tester = map.get("worker").toString()
        var booltest = false
        if (tester.contains(",")) {
            val worker_list = map.get("worker") as ArrayList<*>
            for (i in 0 until worker_list.size) {
                if (worker_list.get(i).toString().contains(user)) {
                    booltest = true
                    break
                }
            }
        } else {
            booltest = tester.contains(user)
        }
        return booltest
    }
    private fun createCard(workertester: String, projectlistall: Map<*,*>, user: String,list:LinearLayout,bt_id: Int, manager:Boolean): CardView {
        if (workertester.contains(",")){
            val workerlist = projectlistall.get("worker") as ArrayList<*>
            val statelist = projectlistall.get("state") as ArrayList<*>
            val tasklist = projectlistall.get("task") as ArrayList<*>
            for (j in 0..workerlist.size-1) {
                if (user.contains(workerlist[j].toString())&&(tasklist[j].toString().contains("onGoing"))||manager) {
                    val hlistV = LinearLayout(this)
                    val TaskV = TextView(this)
                    val StatusV = TextView(this)
                    val WorkerV = TextView(this)
                    TaskV.text = tasklist[j].toString()
                    var test = tasklist[j]
                    StatusV.text = statelist[j].toString()
                    WorkerV.text = user
                    val Button = Button(this)
                    Button.id = bt_id;
                    println("button id is : ${Button.id}")
                    Button.setOnClickListener(this)
                    hlistV.addView(TaskV)
                    hlistV.addView(StatusV)
                    hlistV.addView(WorkerV)
                    hlistV.addView(Button)
                    list.addView(hlistV)
                }

            }
        }else{
            val hlistV = LinearLayout(this)
            val TaskV = TextView(this)
            val StatusV = TextView(this)
            val WorkerV = TextView(this)
            TaskV.text = projectlistall["task"].toString()
            StatusV.text =projectlistall["state"].toString()
            WorkerV.text = user
            val Button = Button(this)
            Button.id = bt_id;
            println("button id is : ${Button.id}")
            Button.setOnClickListener(this)
            TaskV.id = 100+bt_id
            hlistV.addView(TaskV)
            hlistV.addView(StatusV)
            hlistV.addView(WorkerV)
            hlistV.addView(Button)
            list.addView(hlistV)
        }
        val cvCard = CardView(this)
        cvCard.radius = 15f
        cvCard.setCardBackgroundColor(Color.parseColor("#009688"))
        cvCard.setContentPadding(36,36,36,36)
        cvCard.cardElevation = 30f



    private fun initView(user: String, pos: String){
            val mBtMainLogout = findViewById<Button>(R.id.bt_main_logout)
            val newProj = findViewById<Button>(R.id.bt_main_create_new_project)
            newProj.isEnabled = false
            var manager = false
            if (pos == "manager"){
                newProj.isEnabled = true
                manager = true
            }       // get the layout and event

        var linear = findViewById<LinearLayout>(R.id.fragment_bucket)
        var bt_id = 0
           //////////////  ///////////////////////////////////////////////////////////////////////
           var fb = FirebaseUtils().fireStoreDatabase.collection("projects")
           fb.get().addOnSuccessListener { querySnapshot ->
                   querySnapshot.forEach { document ->
                       Log.d(TAG, "Read document with ID ${document.id}")
                       var projectlistall = document.getData() as Map<*,*>

                       val workertester = projectlistall.get("worker").toString()
                       val tasklist = projectlistall.get("task")
                       val statelist = projectlistall.get("state")
                       val workerlist = projectlistall.get("worker")
                       var booltest = false
                       if (workertester.contains(",")){
                           val workerlist = projectlistall.get("worker") as ArrayList<*>
                           for (i in 0..workerlist.size-1){
                               if (workerlist.get(i).toString().contains(user)) {
                                   booltest = true
                                   break
                               }
                           }
                       }else{
                           booltest=workertester.contains(user)
                       }

                        if (booltest) {
                           println(document.data)
                           var currentProj = document.data["projectName"]
                           var currentTask = document.data["task"]
                           var currentDDL = document.data["deadline"]
                           var currentState = document.data["state"]
                           var currentWorker = document.data["worker"]
                           println(currentProj)
                           val tvProjectName = TextView(this)
                           tvProjectName.text =  currentProj.toString()
                           val tvDeadline = TextView(this)
                           tvDeadline.text =  currentDDL.toString()
                           //NEED CODE FOR NUMBER OF WORKERS IN AN ARRAY
                           val tvWorkers = TextView(this)
                           tvWorkers.text =  currentWorker.toString()
                           val tvTasks = TextView(this)
                           tvTasks.text =  currentTask.toString()
                           val cvCard = CardView(this)
                           val list = LinearLayout(this)
                           list.orientation = VERTICAL
                           list.addView(tvProjectName)
                           list.addView(tvDeadline)
                            if (workertester.contains(",")){
                              val workerlist = projectlistall.get("worker") as ArrayList<*>
                              val statelist = projectlistall.get("state") as ArrayList<*>
                              val tasklist = projectlistall.get("task") as ArrayList<*>
                                for (j in 0..workerlist.size-1) {
                                    if (user.contains(workerlist[j].toString())&&tasklist[j].toString().contains("onGoing")) {
                                        val hlistV = LinearLayout(this)
                                        val TaskV = TextView(this)
                                        val StatusV = TextView(this)
                                        val WorkerV = TextView(this)
                                        TaskV.text = tasklist[j].toString()
                                        StatusV.text = statelist[j].toString()
                                        WorkerV.text = user
                                        val Button = Button(this)
                                        Button.id = bt_id;
                                        println("button id is : ${Button.id}")
                                        Button.setOnClickListener(this)
                                        hlistV.addView(TaskV)
                                        hlistV.addView(StatusV)
                                        hlistV.addView(WorkerV)
                                        hlistV.addView(Button)
                                        list.addView(hlistV)
                                    }

                                }
                           }else{
                                val hlistV = LinearLayout(this)
                                val TaskV = TextView(this)
                                val StatusV = TextView(this)
                                val WorkerV = TextView(this)
                                TaskV.text = tasklist.toString()
                                StatusV.text = statelist.toString()
                                WorkerV.text = user
                                val Button = Button(this)
                                Button.id = bt_id;
                                println("button id is : ${Button.id}")
                                Button.setOnClickListener(this)
                                TaskV.id = 100+bt_id
                                hlistV.addView(TaskV)
                                hlistV.addView(StatusV)
                                hlistV.addView(WorkerV)
                                hlistV.addView(Button)
                                list.addView(hlistV)
                            }


                           cvCard.radius = 15f
                           cvCard.setCardBackgroundColor(Color.parseColor("#009688"))
                           cvCard.setContentPadding(36,36,36,36)
                           cvCard.cardElevation = 30f

                           cvCard.addView(list)
                           linear.addView(cvCard)
                            cvCard.id=bt_id+1000
                            bt_id++

                        }else{


                           println(document.data["workers"])
                       }
                   }
               }
               .addOnFailureListener { exception ->
                   Log.w(TAG, "Error getting documents $exception")
               }
//////////////////////////////////////////////////////////////////////////////////////


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
                intent2.putExtra("managername","test")
                finish()
            }

            0->{
                println("Button 0")
            }
            1->{
                println("Button 1")
            }
            2->{
                println("Button 2")
            }
            3->{
                println("Button 3")
            }

        }

    }

}

