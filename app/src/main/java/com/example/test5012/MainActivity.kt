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

        cvCard.addView(list)
        return cvCard
    }
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
                       val workertester = projectlistall["worker"].toString() //Checks if there is an array or string, crashes without
                       val tasklist = projectlistall["task"]
                       val statelist = projectlistall["state"]
                       val workerlist = projectlistall["worker"]
                       val name = projectlistall["projectName"].toString()
                       val projdeadline = projectlistall["deadline"].toString()

                       if (testWorker(projectlistall,user)) {
                           println(document.data)
                           val tvProjectName = TextView(this)
                           tvProjectName.text =  name
                           val tvDeadline = TextView(this)
                           tvDeadline.text =  projdeadline
                           //NEED CODE FOR NUMBER OF WORKERS IN AN ARRAY
                           val tvMang = TextView(this)
                           tvMang.text =  user //REPLACE WITH MANAGER

                           val list = LinearLayout(this)
                           list.orientation = VERTICAL
                           list.addView(tvProjectName)
                           list.addView(tvDeadline)
                           list.addView(tvMang)
                           var cvCard = createCard(workertester,projectlistall,user,list,bt_id)


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
                intent2.putExtra("managername",manager)
                finish()
            }
            else ->{
                var i:Int = view.id
                println(i)
                val tv: TextView = findViewById(100+i)
                val cv: CardView = findViewById(1000+i)
                println(tv.text)
                //MISSING UPDATE DATABASE
            }

       }
    }

}

