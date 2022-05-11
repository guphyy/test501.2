package com.example.test5012

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.iterator
import kotlin.reflect.typeOf


class RegisterActivityProject : AppCompatActivity(), View.OnClickListener {
    private var mDBOpenHelperProject: DBOpenHelperProject? = null
    private var mEtRegisterActivityProjectname: EditText? = null
    private var mEtRegisterActivityDeadline: EditText? = null
    private var mEtRegisterActivityTask: EditText? = null
    private var mEtRegisterActivityWorker: EditText? = null
    private var mEtRegisterActivityState: EditText? = null

    private var mEtRegisterActivityTaskAdd: EditText? = null
    private var mEtRegisterActivityWorkerAdd: EditText? = null
    private var mEtRegisterActivityStateAdd: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_project)
        initView()
        mDBOpenHelperProject = DBOpenHelperProject(this)
    }


    @SuppressLint("WrongViewCast")
    private fun initView() {
        val mBtRegisteractivityRegister = findViewById<Button>(R.id.bt_registeractivity_register)
        val mBtAddNewProject = findViewById<Button>(R.id.bt_registeractivity_add)
        val mIvRegisteractivityBack = findViewById<ImageView>(R.id.iv_registeractivity_back)
        mEtRegisterActivityProjectname = findViewById(R.id.et_registeractivity_projectname)
        mEtRegisterActivityDeadline = findViewById(R.id.et_registeractivity_deadline)
        mEtRegisterActivityTask = findViewById(R.id.et_registeractivity_task)
        mEtRegisterActivityWorker = findViewById(R.id.et_registeractivity_worker)
        mEtRegisterActivityState = findViewById(R.id.et_registeractivity_state)
        mEtRegisterActivityTaskAdd = findViewById(R.id.task_add)
        mEtRegisterActivityWorkerAdd = findViewById(R.id.worker_add)
        mEtRegisterActivityStateAdd = findViewById(R.id.status_add)
        mIvRegisteractivityBack.setOnClickListener(this)
        mBtRegisteractivityRegister.setOnClickListener(this)




    }

    @SuppressLint("NonConstantResourceId", "WrongViewCast")
    var temp = 0
    @OptIn(ExperimentalStdlibApi::class)
    override fun onClick(view: View) {
        when (view.id){
            R.id.iv_registeractivity_back -> {
                val intent1 = Intent(this, MainActivity::class.java)
                startActivity(intent1)
                finish()
        }
            R.id.bt_registeractivity_register -> {

                val projectName = mEtRegisterActivityProjectname!!.text.toString().trim() { it <= ' ' }
                val deadline = mEtRegisterActivityDeadline!!.text.toString().trim() { it <= ' ' }
                val task = mEtRegisterActivityTask!!.text.toString().trim() { it <= ' ' }
                val worker = mEtRegisterActivityWorker!!.text.toString().trim() { it <= ' ' }
                val state = mEtRegisterActivityState!!.text.toString().trim() { it <= ' ' }

                if (!TextUtils.isEmpty(projectName) && !TextUtils.isEmpty(deadline) && !TextUtils.isEmpty(task) && !TextUtils.isEmpty(worker) && !TextUtils.isEmpty(state)) {
                    mDBOpenHelperProject!!.add(projectName, deadline, task, worker, state)
                    val intent2 = Intent(this, MainActivity::class.java)
                    startActivity(intent2)
                    finish()
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



            }
            R.id.bt_registeractivity_add -> {
                temp++
                var myFlowLayout = findViewById<LinearLayout>(R.id.add_here)
                var registerTask = EditText(this)
                registerTask.hint = "Please describe next task$temp"
                registerTask.width = 1000
                registerTask.tag = temp
                registerTask.id = R.id.task_add

                val registerWorker = EditText(this)
                registerWorker.hint = "Please select next worker$temp"
                registerWorker.width = 1000
                registerWorker.tag = temp
                registerWorker.id = R.id.worker_add


                val registerState = EditText(this)
                //registerState.hint = "Status"
                registerState.setText("onGoing")
                registerState.width = 1000
                registerState.tag = temp
                registerState.id = R.id.status_add

                val addNewTaskBtn = Button(this)
                addNewTaskBtn.text = "confirm"
                addNewTaskBtn.width = 250

                addNewTaskBtn.setOnClickListener {
                    val projectName = mEtRegisterActivityProjectname!!.text.toString().trim() { it <= ' ' }
                    val deadline = mEtRegisterActivityDeadline!!.text.toString().trim() { it <= ' ' }
                    val taskAdd = registerTask.text.toString().trim() { it <= ' ' }
                    val workerAdd = registerWorker.text.toString().trim() { it <= ' ' }
                    val stateAdd = registerState.text.toString().trim() { it <= ' ' }
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





                myFlowLayout.addView(registerTask)
                myFlowLayout.addView(registerWorker)
                myFlowLayout.addView(registerState)
                myFlowLayout.addView(addNewTaskBtn)

                myFlowLayout.invalidate()
            }
        }


    }
}


