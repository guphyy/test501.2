package com.example.test5012

import android.annotation.SuppressLint
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
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.isVisible
import com.google.firebase.firestore.FirebaseFirestore
import android.app.NotificationManager as notificationManager

var CHANNEL_ID = "msg"

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var mDBOpenHelperProject: DBOpenHelperProject? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val username: String = intent.getStringExtra("user").toString()
        val  pos: String = intent.getStringExtra("position").toString()
        //println("user $username , $pos")
        setContentView(R.layout.activity_main)
        createNotificationChannel()

        initView(username,pos)
        mDBOpenHelperProject = DBOpenHelperProject(this)
    }


    class FirebaseUtils {
        val fireStoreDatabase = FirebaseFirestore.getInstance()
    }

    private fun testWorker(map: Map<*, *>, user: String): Boolean {//simple checker if worker exists in map
        val tester = map["worker"].toString()
        var boolTest = false
        if (tester.contains(",")) {
            val workerList = map["worker"] as ArrayList<*>
            for (i in 0 until workerList.size) {
                if (workerList[i].toString().contains(user)) {
                    boolTest = true
                    break
                }
            }
        } else {
            boolTest = tester.contains(user)
        }
        return boolTest
    }

    @SuppressLint("SetTextI18n")
    private fun createCard(
        workerTester: String,
        projectListAll: Map<*, *>,
        user: String,
        list: LinearLayout,
        bt_id: Int,
        manager: Boolean,
        projName: String
    ): Pair<CardView, Int> {
        var btIdRunner = bt_id
        if (workerTester.contains(",")) {
            val workerList = projectListAll["worker"] as ArrayList<*>
            val stateList = projectListAll["state"] as ArrayList<*>
            val taskList = projectListAll["task"] as ArrayList<*>
            val projectManager = projectListAll["submit_by"].toString()
            val taskDdlList = projectListAll["taskDdl"] as ArrayList<*>
            for (j in 0 until workerList.size) {
                val b1 = workerList[j].toString().contains(user)
                val b2 = projectManager.contains(user)
                if ((!manager && b1) || (manager && b2)) {
                    //set up views
                    val horizontalListView = LinearLayout(this)
                    val horizontalListView2 = LinearLayout(this)
                    val taskView = TextView(this)
                    val statusView = TextView(this)
                    val workerView = TextView(this)
                    val deadlineV = TextView(this)
                    val button = Button(this)
                    val projNameView = TextView(this)
                    //running data
                    val taskViewText = taskList[j].toString()
                    val taskDeadline = taskDdlList[j].toString()
                    val stateText = stateList[j].toString()
                    val workerViewText = workerList[j].toString()
                    //make text
                    taskView.text = "   Task: $taskViewText"
                    statusView.text = "   State: $stateText"
                    if (stateText == "onGoing"){
                        statusView.setTextColor(Color.parseColor("#ff0000"))
                    }
                    if (stateText == "complete"){
                        statusView.setTextColor(Color.parseColor("#7CFC00"))
                    }
                    workerView.text = "   Worker: $workerViewText"
                    deadlineV.text = "   Due by: $taskDeadline"
                    projNameView.text = projName
                    projNameView.isVisible = false // for simplicity in onclick

                    button.id = btIdRunner
                    taskView.id = 100 + btIdRunner
                    workerView.id = 1000 + btIdRunner
                    projNameView.id = 2000 + btIdRunner
                    btIdRunner += 1
                    //add views
                    button.setOnClickListener(this)
                    horizontalListView.addView(taskView)
                    horizontalListView.addView(workerView)
                    list.addView(horizontalListView)

                    horizontalListView2.addView(statusView)
                    horizontalListView2.addView(deadlineV)
                    horizontalListView2.addView(projNameView)
                    if (((!manager) && (stateText=="onGoing"))) {
                        button.text = "Press when task is complete"
                        horizontalListView2.addView(button)
                    }
                    list.addView(horizontalListView2)
                }

            }
        } else {
            if (projectListAll["state"] != null ||manager) {
                //make views
                val horizontalListView = LinearLayout(this)
                val horizontalListView2 = LinearLayout(this)
                val taskView = TextView(this)
                val statusView = TextView(this)
                val workerView = TextView(this)
                val deadlineV = TextView(this)
                //get data once
                val taskViewText = (projectListAll["task"].toString()).drop(1).dropLast(1) //takes the [] off, weirdness from one string array
                val stateText = (projectListAll["state"].toString()).drop(1).dropLast(1)
                val workerViewText = (projectListAll["worker"].toString()).drop(1).dropLast(1)
                val taskDeadline = projectListAll["taskDdl"].toString().drop(1).dropLast(1)
                val button = Button(this)
                val projNameView = TextView(this)

                // text
                taskView.text = "   Task: $taskViewText"
                statusView.text = "   State: $stateText"
                workerView.text = "   Worker: $workerViewText"
                deadlineV.text = "   Due by: $taskDeadline"
                projNameView.text = projName //invisible to make onClick simpler
                projNameView.isVisible = false
                if (stateText == "onGoing"){
                    statusView.setTextColor(Color.parseColor("#ff0000"))
                }
                if (stateText == "complete"){
                    statusView.setTextColor(Color.parseColor("#7CFC00"))
                }
                button.setOnClickListener(this)
                //setting ids for callback
                button.id = btIdRunner
                taskView.id = 100 + btIdRunner
                workerView.id = 1000 + btIdRunner
                projNameView.id = btIdRunner + 2000
                //adding views
                horizontalListView.addView(taskView)

                horizontalListView.addView(workerView)
                list.addView(horizontalListView)
                horizontalListView2.addView(statusView)
                horizontalListView2.addView(deadlineV)
                horizontalListView2.addView(projNameView)
                if (!manager && stateText == "onGoing" ) {
                    button.text = "Press when task is complete"
                    horizontalListView2.addView(button)
                }
                list.addView(horizontalListView2)
            }
        }
        val cvCard = CardView(this)
        cvCard.radius = 45f

        cvCard.setCardBackgroundColor(Color.parseColor("#FFEFD5"))
        cvCard.setContentPadding(36,36,36,50)
        cvCard.cardElevation = 30f

        cvCard.addView(list)
        return Pair(cvCard, btIdRunner)
    }

    @SuppressLint("SetTextI18n")
    private fun initView(user: String, pos: String){

        val mBtMainLogout = findViewById<Button>(R.id.bt_main_logout)
        mBtMainLogout.setOnClickListener(this)

        val newProj = findViewById<Button>(R.id.bt_main_create_new_project)
        val searchUser = findViewById<Button>(R.id.search_button)
        val searchText = findViewById<TextView>(R.id.SearchText)

        //user dependant setup
        newProj.isEnabled = false
        newProj.isVisible = false
        var manager = false
        searchUser.isVisible = false
        searchText.isVisible = false
        if (pos == "manager"){
            newProj.isEnabled = true
            newProj.isVisible = true
            searchUser.isVisible = true
            searchText.isVisible = true
            searchUser.setOnClickListener(this)
            manager = true
        }else{
            showNotification(this) // need to find solution to notification
        }

        val linear = findViewById<LinearLayout>(R.id.fragment_bucket) // get main list (scrollable)
        var btId = 0 // setup for id
        //Starting the read from the database
        val fb = FirebaseUtils().fireStoreDatabase.collection("projects")
        fb.get().addOnSuccessListener { querySnapshot ->
            querySnapshot.forEach { document ->
                if(document.exists()) {
                    Log.d(TAG, "Read document with ID ${document.id}")
                    //Document has been read get data as MAP of String String
                    //note if there is only one string weirdness occurs

                    val projectListAll =
                        document.data as Map<*, *>//cant be String,String because of weirdness
                    val workerTester = projectListAll["worker"].toString().drop(1)
                        .dropLast(1)//Gets the list of Workers without []
                    val name = projectListAll["projectName"].toString()
                    val projectDeadline = projectListAll["deadline"].toString()
                    val projectState = projectListAll["project_state"].toString()
                    val managerName = projectListAll["submit_by"].toString()

                    if (testWorker(projectListAll, user) || manager && managerName.contains(user)) {
                        println(document.data)
                        //Title for the Project, appears first
                        val tvTitleProjectName = TextView(this)
                        tvTitleProjectName.text = "Project:"
                        tvTitleProjectName.textSize = 22f
                        //The actual name of the project
                        val tvProjectName = TextView(this)
                        tvProjectName.text = "    $name"
                        tvProjectName.textSize = 22f
                        //Title for the State of the project
                        val tvProjectStateTitle = TextView(this)
                        tvProjectStateTitle.text = "Project State"
                        tvProjectStateTitle.textSize = 18f
                        //you get the point
                        val tvProjectState = TextView(this)
                        tvProjectState.text = "    $projectState"
                        tvProjectState.textSize = 18f

                        val tvTitleDeadline = TextView(this)
                        tvTitleDeadline.text = "Deadline:"
                        tvTitleDeadline.textSize = 18f

                        //tvTitleDeadline.textSize = 18f
                        val tvDeadline = TextView(this)
                        tvDeadline.text = "     $projectDeadline"
                        tvDeadline.textSize = 18f

                        val tvTeamTitle = TextView(this)
                        tvTeamTitle.text = "Team members"
                        tvTeamTitle.textSize = 18f

                        val tvProjectTeam = TextView(this)
                        tvProjectTeam.text = "    $workerTester"
                        tvProjectTeam.textSize = 18f

                        val tvManagerTitle = TextView(this)
                        tvManagerTitle.text = "manager: "
                        tvManagerTitle.textSize = 18f

                        val tvManager = TextView(this)
                        tvManager.text = "    $managerName"
                        tvManager.textSize = 18f

                        val listTitle = TextView(this)
                        listTitle.text = "Task list:"
                        listTitle.textSize = 18f

                        //Create List for the card, this has no loops so kept outside of card
                        val list = LinearLayout(this)
                        list.orientation = VERTICAL
                        list.addView(tvTitleProjectName)
                        list.addView(tvProjectName)

                        list.addView(tvProjectStateTitle)
                        list.addView(tvProjectState)

                        list.addView(tvManagerTitle)
                        list.addView(tvManager)

                        list.addView(tvTitleDeadline)
                        list.addView(tvDeadline)

                        list.addView(tvTeamTitle)
                        list.addView(tvProjectTeam)

                        list.addView(listTitle)

                        val (cvCard, bt_id_out) = createCard(workerTester,
                            projectListAll,
                            user,
                            list,
                            btId,
                            manager,
                            name) // very important
                        btId = bt_id_out
                        //linear is main list
                        val blank = TextView(this)
                        blank.width = 15 //spacing
                        linear.gravity = Gravity.CENTER
                        linear.addView(cvCard)
                        linear.addView(blank)
                    } else {
                        println(document.data["workers"])
                    }
                }else{

                }
            }//close of database search
        }//close database
    }
    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because of compatibility issues
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
                finish()//log out ends the activity with no data leakage
            }
            R.id.search_button -> {
                val tv = findViewById<EditText>(R.id.SearchText)
                val pos :String= intent.getStringExtra("position").toString()
                val name = tv.text.toString().trim { it <= ' ' }
                startActivity(Intent(this, MainActivity::class.java).putExtra("user", name).putExtra("position", pos))
                //search button should be back able
            }
            R.id.bt_main_create_new_project -> {
                //val intent3 = Intent(this, RegisterActivityProject::class.java)
                // when touch the btn, go to create new project page

                val pos :String= intent.getStringExtra("position").toString()
                val username :String= intent.getStringExtra("user").toString()
                startActivity(Intent(this, RegisterActivityProject::class.java).putExtra("managername", username).putExtra("managerPos", pos))
                //starts the new project activity allowing for a back button
            }

            else ->{
                //somebody must have clicked a button
                val i:Int = view.id//0-99 should only be a button, from top to bottom in order
                val tv: TextView = findViewById(100+i)//100-999 is the text view with the task name
                //val uv: TextView = findViewById(1000+i)//1000-1999 is the user name, not needed unless manager gets access
                val pv: TextView = findViewById(2000+i)//2000+ is the project name, this was invisible before


                val taskName = pv.text.toString()
                val fb = FirebaseUtils().fireStoreDatabase.collection("projects").document("project: $taskName")//opens up the task
                    fb.get().addOnSuccessListener { document ->
                        if (document != null){
                            var completeCheck = true // if all tasks are complete, then this remains true
                            //lists
                            val taskList = document.get("task") as ArrayList<*>
                            val stateList = document.get("state") as ArrayList<*>
                            println("Button $i pressed, checking for task ${tv.text.toString()}")

                            if (taskList.size==1){//avoiding string weirdness of array of 1
                                fb.update("state", "[complete]")//brackets are removed because it is normally a lis
                                fb.update("project_state", "complete")//only one task so project must be complete
                            }else { //no weirdness, proceed with normal things
                                val myList = arrayListOf<String>()//this is the list that will be passed to the update function

                                for (j in 0..taskList.size - 1) {
                                    println("testing ${taskList[j].toString()}")
                                    if (tv.text.toString().contains(taskList[j].toString())) { // we found the task
                                        myList.add("complete")
                                    }else{
                                        myList.add(stateList[j].toString())
                                        if(!stateList[j].toString().contains("complete")){//make sure that all tasks are complete
                                            completeCheck = false
                                        }
                                    }
                                }
                                fb.update("state", myList) //update with
                            }
                            if(completeCheck){
                                fb.update("project_state", "complete")//task is done
                            }
                        }else{
                            println("document does not exist?")//should never happen
                        }
                        //to update the data we need to remake the view
                        val pos :String= intent.getStringExtra("position").toString()
                        val username :String= intent.getStringExtra("user").toString()
                        startActivity(Intent(this, MainActivity::class.java).putExtra("user", username).putExtra("position", pos))
                    }
            }
        }
    }
}

