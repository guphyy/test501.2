package com.example.test5012

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class RegisterActivityProject : AppCompatActivity(), View.OnClickListener {
    private var mDBOpenHelperProject: DBOpenHelperProject? = null
    private var mEtRegisterActivityProjectname: EditText? = null
    private var mEtRegisterActivityDeadline: EditText? = null
    private var mEtRegisterActivityTask: EditText? = null
    private var mEtRegisterActivityWorker: EditText? = null
    private var mEtRegisterActivityState: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_project)
        initView()
        mDBOpenHelperProject = DBOpenHelperProject(this)
    }

    private fun initView() {
        val mBtRegisteractivityRegister = findViewById<Button>(R.id.bt_registeractivity_register)
        val mIvRegisteractivityBack = findViewById<ImageView>(R.id.iv_registeractivity_back)
        mEtRegisterActivityProjectname = findViewById(R.id.et_registeractivity_projectname)
        mEtRegisterActivityDeadline = findViewById(R.id.et_registeractivity_deadline)
        mEtRegisterActivityTask = findViewById(R.id.et_registeractivity_task)
        mEtRegisterActivityWorker = findViewById(R.id.et_registeractivity_worker)
        mEtRegisterActivityState = findViewById(R.id.et_registeractivity_state)
        mIvRegisteractivityBack.setOnClickListener(this)
        mBtRegisteractivityRegister.setOnClickListener(this)
    }

    @SuppressLint("NonConstantResourceId")
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
        }

    }
}