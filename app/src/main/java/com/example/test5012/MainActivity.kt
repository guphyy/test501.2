package com.example.test5012

import android.app.NotificationChannel
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
//import android.support.v4.app.NotificationCompat
//import android.support.v4.app.NotificationManagerCompat

import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.*
import android.widget.LinearLayout.VERTICAL
import android.widget.LinearLayout.Z
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.isVisible
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import android.app.NotificationManager as notificationManager


data class Project(
    val worker: List<String> ,
    val task: List<String>,
    val state: List<String>,
    val deadline:String = "",
    val projectName:String = ""
)
var CHANNEL_ID = "msg"
class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var mDBOpenHelperProject: DBOpenHelperProject? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var username: String = intent.getStringExtra("user").toString()
        var pos: String = intent.getStringExtra("position").toString()
        //println("user $username , $pos")

        setContentView(R.layout.activity_main)
        initView(username,pos)
        createNotificationChannel()
        mDBOpenHelperProject = DBOpenHelperProject(this)
    }


    class FirebaseUtils {
        val fireStoreDatabase = FirebaseFirestore.getInstance()
    }

    private fun testWorker(map: Map<*, *>, user: String): Boolean {
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

    private fun createCard(
        workertester: String,
        projectlistall: Map<*, *>,
        user: String,
        list: LinearLayout,
        bt_id: Int,
        manager: Boolean,
        projName: String
    ): Pair<CardView, Int> {
        var bt_id_runner = bt_id
        if (workertester.contains(",")) {
            val workerlist = projectlistall.get("worker") as ArrayList<*>
            val statelist = projectlistall.get("state") as ArrayList<*>
            val tasklist = projectlistall.get("task") as ArrayList<*>
            val projectman = projectlistall.get("submit_by").toString()
            var taskDdlList = projectlistall["taskDdl"] as ArrayList<*>
            for (j in 0..workerlist.size - 1) {
                var b1 = workerlist[j].toString().contains(user)
                var b2 = statelist[j].toString().contains("onGoing")
                var b3 = projectman.contains(user)
                if ((!manager &&(b1 && b2)) || (manager)) {
                    val hlistV = LinearLayout(this)
                    val TaskV = TextView(this)
                    val StatusV = TextView(this)
                    val WorkerV = TextView(this)
                    val ProjNameV = TextView(this)
                    val deadlineV = TextView(this)


                    var TaskVText = tasklist[j].toString()
                    var StateText = statelist[j].toString()
                    var WorkerVText = workerlist[j].toString()

                    TaskV.text = "   Task: $TaskVText"
                    StatusV.text = "   State: $StateText"

                    if (StateText == "onGoing"){
                        StatusV.setTextColor(Color.parseColor("#ff0000"))
                    }


                    WorkerV.text = "   Worker: $WorkerVText"

                    var taskDdl = taskDdlList[j].toString()
                    deadlineV.text = "   Due by: $taskDdl"

                    ProjNameV.text = projName
                    ProjNameV.isVisible = false
                    val Button = Button(this)
                    Button.id = bt_id_runner;
                    //println("button id is : ${Button.id}")
                    Button.setOnClickListener(this)
                    TaskV.id = 100 + bt_id_runner
                    WorkerV.id = 1000 + bt_id_runner
                    ProjNameV.id = 2000 + bt_id_runner
                    bt_id_runner = bt_id_runner + 1
                    hlistV.addView(TaskV)

                    hlistV.addView(WorkerV)

                    hlistV.addView(WorkerV)
                    hlistV.addView(deadlineV)

                    hlistV.addView(ProjNameV)
                    if (!manager) {
                        hlistV.addView(Button)
                    }
                    list.addView(hlistV)
                }

            }
        } else {
            if (projectlistall["state"].toString().contains("onGoing")||manager) {
                val hlistV = LinearLayout(this)
                val TaskV = TextView(this)
                val StatusV = TextView(this)
                val WorkerV = TextView(this)
                val deadlineV = TextView(this)

                val TaskVText = (projectlistall["task"].toString()).drop(1).dropLast(1)
                val StateText = (projectlistall["state"].toString())

                TaskV.text = "   Task: $TaskVText"
                StatusV.text = "   State: $StateText"
                if (StateText == "onGoing"){
                    StatusV.setTextColor(Color.parseColor("#ff0000"))
                }

                var WorkerVText = (projectlistall["worker"].toString()).drop(1).dropLast(1)
                WorkerV.text = "   Worker: $WorkerVText"

                var taskDdl = projectlistall["taskDdl"].toString().drop(1).dropLast(1)
                deadlineV.text = "   Due by: $taskDdl"
                val Button = Button(this)
                Button.id = bt_id_runner;
                //println("button id is : ${Button.id}")
                Button.setOnClickListener(this)
                val ProjNameV = TextView(this)
                ProjNameV.text = projName
                ProjNameV.isVisible = false
                TaskV.id = 100 + bt_id_runner
                WorkerV.id = 1000 + bt_id_runner
                ProjNameV.id = bt_id_runner + 2000

                hlistV.addView(TaskV)
                hlistV.addView(StatusV)
                hlistV.addView(WorkerV)
                list.addView(hlistV)
                val hlistV2 = LinearLayout(this)
                hlistV2.addView(deadlineV)
                hlistV2.addView(ProjNameV)
                if (!manager) {
                    Button.text = "Press when task is complete"
                    hlistV2.addView(Button)

                }

                list.addView(hlistV2)
            }
        }
        val cvCard = CardView(this)
        cvCard.radius = 45f

        cvCard.setCardBackgroundColor(Color.parseColor("#FFEFD5"))
        cvCard.setContentPadding(36,36,36,50)
        cvCard.cardElevation = 30f

        cvCard.addView(list)
        return Pair(cvCard, bt_id_runner)
    }

    private fun initView(user: String, pos: String){

        val mBtMainLogout = findViewById<Button>(R.id.bt_main_logout)
        val newProj = findViewById<Button>(R.id.bt_main_create_new_project)
        newProj.isEnabled = false
        newProj.isVisible = false
        var manager = false
        if (pos == "manager"){
            newProj.isEnabled = true
            newProj.isVisible = true
            manager = true
        }else{
            showNotification(this)
        }
        // get the layout and event

        var linear = findViewById<LinearLayout>(R.id.fragment_bucket)

        var bt_id = 0

        //////////////  ///////////////////////////////////////////////////////////////////////

        var fb = FirebaseUtils().fireStoreDatabase.collection("projects")
        fb.get().addOnSuccessListener { querySnapshot ->
            querySnapshot.forEach { document ->
                Log.d(TAG, "Read document with ID ${document.id}")
                var projectlistall = document.getData() as Map<*,*>
                val workertester = projectlistall["worker"].toString().drop(1).dropLast(1)
                val name = projectlistall["projectName"].toString()
                val projdeadline = projectlistall["deadline"].toString()
                val projstate = projectlistall["project_state"].toString()
                val mang = projectlistall["submit_by"].toString()

                if (testWorker(projectlistall,user)||manager&&mang.contains(user)) {
                    println(document.data)
                    val tvTitleProjectName = TextView(this)
                    tvTitleProjectName.text = "Project:"
                    tvTitleProjectName.textSize =22f

                    val tvProjectName = TextView(this)
                    tvProjectName.text =  "    $name"
                    tvProjectName.textSize = 22f


                    val tvProjectStateTitle = TextView(this)
                    tvProjectStateTitle.text =  "Project State"
                    tvProjectStateTitle.textSize = 18f

                    val tvProjectState = TextView(this)
                    tvProjectState.text =  "    $projstate"
                    tvProjectState.textSize = 18f

                    val tvTitleDeadline = TextView(this)
                    tvTitleDeadline.text = "Deadline:"
                    tvTitleDeadline.textSize = 18f

                    //tvTitleDeadline.textSize = 18f
                    val tvDeadline = TextView(this)
                    tvDeadline.text =  "     $projdeadline"
                    tvDeadline.textSize = 18f

                    val tvTeamTitle = TextView(this)
                    tvTeamTitle.text =  "Team members"
                    tvTeamTitle.textSize = 18f

                    val tvProjectTeam = TextView(this)
                    tvProjectTeam.text =  "    $workertester"
                    tvProjectTeam.textSize = 18f

                    //NEED CODE FOR NUMBER OF WORKERS IN AN ARRAY
                    val tvMangTitle = TextView(this)
                    tvMangTitle.text =  "manager: " //REPLACE WITH MANAGER
                    tvMangTitle.textSize = 18f

                    val tvMang = TextView(this)
                    tvMang.text =  "    $mang" //REPLACE WITH MANAGER
                    tvMang.textSize = 18f

                    val listTitle = TextView(this)
                    listTitle.text = "Task list:"
                    listTitle.textSize = 18f


                    val list = LinearLayout(this)
                    list.orientation = VERTICAL
                    list.addView(tvTitleProjectName)
                    list.addView(tvProjectName)

                    list.addView(tvProjectStateTitle)
                    list.addView(tvProjectState)

                    list.addView(tvMangTitle)
                    list.addView(tvMang)

                    list.addView(tvTitleDeadline)
                    list.addView(tvDeadline)

                    list.addView(tvTeamTitle)
                    list.addView(tvProjectTeam)
                    if((!manager)&&(!projstate.contains("complete"))) {
                        list.addView(listTitle)
                    }
                    var (cvCard,bt_id) = createCard(workertester,projectlistall,user,list,bt_id,manager, name)

                    val blank = TextView(this)
                    blank.width = 15
                    //cvCard.
                    linear.gravity = Gravity.CENTER
                    linear.addView(cvCard)

                    linear.addView(blank)


                        } else {

                            println(document.data["workers"])
                        }
                    }
                }
//////////////////////////////////////////////////////////////////////////////////////


        mBtMainLogout.setOnClickListener(this)
    }
    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = notificationManager.IMPORTANCE_DEFAULT
            val CHANNEL_ID = "msg"
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as notificationManager
            notificationManager.createNotificationChannel(channel)
        }

    }

    private fun showNotification(view: MainActivity)
    {
        // CHANNEL_ID：通道ID，可在类 MainActivity 外自定义。如：val CHANNEL_ID = 'msg_1'

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("You have been assigned a task")
            .setContentText("Please have a check!")
            // 通知优先级，可以设置为int型，范围-2至2
            .setPriority(NotificationCompat.PRIORITY_MAX )
            .build()

        // 显示通知

        with(NotificationManagerCompat.from(this)) {
            notify(1, builder)

        }

    }
    override fun onStart() {
        super.onStart()
    }


    override fun onClick(view: View) {
        when (view.id){
            R.id.bt_main_logout -> {
                val intent2 = Intent(this, LoginActivity::class.java)
                // when touch the btn, get back
                startActivity(intent2)
                finish()
            }
            R.id.bt_main_create_new_project -> {
                //val intent3 = Intent(this, RegisterActivityProject::class.java)
                // when touch the btn, go to create new project page
                var pos :String= intent.getStringExtra("position").toString()
                var username :String= intent.getStringExtra("user").toString()
                startActivity(Intent(this, RegisterActivityProject::class.java).putExtra("managername", username).putExtra("managerPos", pos))

               // startActivity(Intent(this, RegisterActivityProject::class.java).putExtra("managerpos", pos).putExtra("managerPos", pos))

                //here to sent out the userName
                finish()
            }

            else ->{
                var i:Int = view.id
                val tv: TextView = findViewById(100+i)
                val uv: TextView = findViewById(1000+i)
                val pv: TextView = findViewById(2000+i)


                var name = pv.text.toString()
                var fb = FirebaseUtils().fireStoreDatabase.collection("projects").document("project: $name")
                    fb.get().addOnSuccessListener { document ->
                        if (document != null){
                            var completeCheck = true
                            val tasklist = document.get("task") as ArrayList<*>
                            var statelist = document.get("state") as ArrayList<*>
                            if (tasklist.size==1){
                                fb.update("state", "complete")
                                fb.update("project_state", "complete")


                            }else {
                                var myList = arrayListOf<String>()

                                for (j in 0..tasklist.size - 1) {

                                    if (tasklist[j].toString().contains(tv.text.toString())) {
                                        myList.add("complete")
                                    }else{
                                        myList.add(statelist[j].toString())
                                        if(!statelist[j].toString().contains("complete")){
                                            completeCheck = false
                                        }
                                    }
                                }
                                fb.update("state", myList)
                            }
                            if(completeCheck){
                                fb.update("project_state", "complete")
                            }
                        }else{
                            println("document does not exist?")
                        }
                        var pos :String= intent.getStringExtra("position").toString()
                        var username :String= intent.getStringExtra("user").toString()
                        startActivity(Intent(this, MainActivity::class.java).putExtra("user", username).putExtra("position", pos))



                    }

            }


        }
    }

}

