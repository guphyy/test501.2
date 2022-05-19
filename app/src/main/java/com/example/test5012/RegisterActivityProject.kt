package com.example.test5012

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.iterator
import kotlin.reflect.typeOf
import com.google.firebase.firestore.FirebaseFirestore
import com.google.type.Date
import java.util.*


const val TAG = "FIRESTORE"
var workerList = arrayListOf<String>()
var taskList = arrayListOf<String>()
var stateList = arrayListOf<String>()
var ddlList = arrayListOf<String>()
var emailList = arrayListOf<String>()
var valueList = arrayListOf<Int>()


class RegisterActivityProject : AppCompatActivity(), View.OnClickListener {
    private var mDBOpenHelperProject: DBOpenHelperProject? = null
    private var mEtRegisterActivityProjectname: EditText? = null
    private var mEtRegisterActivityDeadline: EditText? = null
    private var mEtRegisterActivityTask: EditText? = null
    private var mEtRegisterActivityWorker: EditText? = null
    private var mEtRegisterActivityWorkerEmail: EditText? = null
    private var mEtRegisterActivityTaskDeadline: EditText? = null
    private var mEtRegisterActivityState: EditText? = null

    private var mEtRegisterActivityTaskAdd: EditText? = null
    private var mEtRegisterActivityWorkerAdd: EditText? = null
    private var mEtRegisterActivityWorkerEmailAdd: EditText? = null
    private var mEtRegisterActivityStateAdd: EditText? = null
    private var managername_m: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_project)

        mDBOpenHelperProject = DBOpenHelperProject(this)


        initView()
    }
    class FirebaseUtils {
        val fireStoreDatabase = FirebaseFirestore.getInstance()
    }


    @SuppressLint("WrongViewCast")
    private fun initView() {
        val mBtRegisteractivityRegister = findViewById<Button>(R.id.bt_registeractivity_register)
        val mBtAddNewProject = findViewById<Button>(R.id.bt_registeractivity_add)
        val mIvRegisteractivityBack = findViewById<ImageView>(R.id.iv_registeractivity_back)
        mEtRegisterActivityProjectname = findViewById(R.id.et_registeractivity_projectname)
        mEtRegisterActivityDeadline = findViewById(R.id.et_registeractivity_deadline)
        mEtRegisterActivityTask = findViewById(R.id.et_registeractivity_task)
        mEtRegisterActivityTaskDeadline = findViewById(R.id.et_registeractivity_taskDdl)
        mEtRegisterActivityWorker = findViewById(R.id.et_registeractivity_worker)
        mEtRegisterActivityWorkerEmail = findViewById(R.id.et_registeractivity_worker_email)
        mEtRegisterActivityTaskAdd = findViewById(R.id.task_add)
        mEtRegisterActivityWorkerAdd = findViewById(R.id.worker_add)
        mEtRegisterActivityStateAdd = findViewById(R.id.status_add)
        mIvRegisteractivityBack.setOnClickListener(this)
        mBtRegisteractivityRegister.setOnClickListener(this)




    }
    private fun dateSort(dateList: ArrayList<String?>, newDate: String){
        var newPair = newDate.split("/")
        var newD = newPair[0].toInt()
        var newM = newPair[1].toInt()
        var value = 31*newM + newD
        valueList.add(value)
        valueList.sort()
    }

    @SuppressLint("NonConstantResourceId", "WrongViewCast")
    var temp = 1
    @OptIn(ExperimentalStdlibApi::class)
    override fun onClick(view: View) {
        when (view.id){
            R.id.iv_registeractivity_back -> {
                val intent4 = Intent(this, MainActivity::class.java)

                val managerName = intent.getStringExtra("managername").toString()
                println("Going back from activity $managerName")
                intent4.putExtra("user",managerName)
                intent4.putExtra("position","manager")
                startActivity(intent4)
                finish()
            }
            R.id.bt_registeractivity_register -> {

                val projectName = mEtRegisterActivityProjectname!!.text.toString().trim() { it <= ' ' }
                val projectDeadline = mEtRegisterActivityDeadline!!.text.toString().trim() { it <= ' ' }
                val task = mEtRegisterActivityTask!!.text.toString().trim() { it <= ' ' }
                val worker = mEtRegisterActivityWorker!!.text.toString().trim() { it <= ' ' }
                val email = mEtRegisterActivityWorkerEmail!!.text.toString().trim() {it <= ' '}
                val taskDeadline = mEtRegisterActivityTaskDeadline!!.text.toString().trim() { it <= ' ' }
                val state = "incomplete"
                val managerName = intent.getStringExtra("managername").toString()
                println(managerName)
                val projectState = "registered"
                workerList.add(worker)
                taskList.add(task)
                stateList.add(state)
                ddlList.add(taskDeadline)
                emailList.add(email)

                if (!TextUtils.isEmpty(projectName) && !TextUtils.isEmpty(projectDeadline) && !TextUtils.isEmpty(task) && !TextUtils.isEmpty(worker) && !TextUtils.isEmpty(state)) {
                    mDBOpenHelperProject!!.add(projectName, projectDeadline, task, worker, state)
                    //val intent5 = Intent(this, MainActivity::class.java)
                    //intent2.putExtra("user",)
                    val pos :String= intent.getStringExtra("managerPos").toString()
                    var name:String = intent.getStringExtra("managername").toString()
                    //startActivity(Intent(this, MainActivity::class.java).putExtra("username_back",name).putExtra("pos_back",pos))

                    //finish()
                    Toast.makeText(
                        this,
                        "Complete information and successful registration",
                        Toast.LENGTH_SHORT
                    ).show()
                }else{
                    Toast.makeText(
                        this,
                        "Not perfect information, registration failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                val hashMap = hashMapOf<String, Any>(
                    "projectName" to projectName,
                    "deadline" to projectDeadline,
                    "project_state" to projectState,
                    "task" to taskList,
                    "taskDdl" to ddlList,
                    "worker" to workerList,
                    "emails" to emailList,
                    "state" to stateList,
                    "submit_by" to managerName
                )
                FirebaseUtils().fireStoreDatabase.collection("projects")
                    .document("project: $projectName")
                    .set(hashMap)
                    .addOnSuccessListener {
                        Log.d(TAG, "Added document with ID $projectName")
                    }
                    .addOnFailureListener { exception ->
                        Log.w(TAG, "Error adding document $exception")
                    }
                val intent4 = Intent(this, MainActivity::class.java)
                println("Going back from activity $managerName")
                intent4.putExtra("user",managerName)
                intent4.putExtra("position","manager")
                startActivity(intent4)
                finish()
            }



            R.id.bt_registeractivity_add -> {
                temp++
                var myFlowLayout = findViewById<LinearLayout>(R.id.add_here)
                var title = TextView(this)
                title.text = "Task 2"

                var registerTask = EditText(this)
                registerTask.hint = "Please describe next task$temp"
                //registerTask.setText("idle")
                registerTask.width = 1000
                registerTask.tag = temp
                registerTask.id = R.id.task_add

                val registerWorker = EditText(this)
                registerWorker.hint = "Please select next worker$temp"
                //registerWorker.setText("idle")
                registerWorker.width = 1000
                registerWorker.tag = temp
                registerWorker.id = R.id.worker_add

                val registerWorkerEmail = EditText(this)
                registerWorkerEmail.hint = "Please enter email of worker$temp"
                //registerWorkerEmail.setText("idle")
                registerWorkerEmail.width = 1000
                registerWorkerEmail.tag = temp
                registerWorkerEmail.id = R.id.worker_email_add

                val registerDDL = EditText(this)
                registerDDL.hint = "Please input deadline of task$temp"
                //registerDDL.setText("idle")
                registerDDL.width = 1000
                registerDDL.tag = temp
                registerDDL.id = R.id.taskDdl_add

                /*
                val registerState = EditText(this)
                //registerState.hint = "Status"
                registerState.setText("onGoing")
                registerState.width = 1000
                registerState.tag = temp
                registerState.id = R.id.status_add
                */
                val addNewTaskBtn = Button(this)
                addNewTaskBtn.text = "confirm task"
                addNewTaskBtn.width = 100

                addNewTaskBtn.setOnClickListener {
                    val projectName = mEtRegisterActivityProjectname!!.text.toString().trim() { it <= ' ' }
                    val deadline = mEtRegisterActivityDeadline!!.text.toString().trim() { it <= ' ' }
                    val taskAdd = registerTask.text.toString().trim() { it <= ' ' }
                    val workerAdd = registerWorker.text.toString().trim() { it <= ' ' }
                    val emailAdd = registerWorkerEmail.text.toString().trim() { it <= ' ' }
                    val stateAdd = "incomplete"
                    val ddlOftasks = registerDDL.text.toString().trim() {it <= ' '}
                    workerList.add(workerAdd)
                    emailList.add(emailAdd)
                    taskList.add(taskAdd)
                    stateList.add(stateAdd)
                    ddlList.add(ddlOftasks)
                    //sentEmailTo(emailAdd, workerAdd)
                    if (!TextUtils.isEmpty(projectName) && !TextUtils.isEmpty(deadline) && !TextUtils.isEmpty(
                            taskAdd
                        ) && !TextUtils.isEmpty(workerAdd) && !TextUtils.isEmpty(stateAdd)
                    ) {
                        mDBOpenHelperProject!!.add(
                            projectName,
                            deadline,
                            taskAdd,
                            workerAdd,
                            stateAdd
                        )
                    }
                }




                myFlowLayout.addView(title)
                myFlowLayout.addView(registerTask)
                myFlowLayout.addView(registerWorker)
                myFlowLayout.addView(registerWorkerEmail)
                myFlowLayout.addView(registerDDL)
                myFlowLayout.addView(addNewTaskBtn)


                myFlowLayout.invalidate()
            }
        }


    }
}
