package com.example.test5012

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    private var mDBOpenHelper: DBOpenHelper? = null
    private var mEtRegisteractivityUsername: EditText? = null
    private var mEtRegisteractivityPassword1: EditText? = null
    private var mEtRegisteractivityPassword2: EditText? = null
    private var mEtRegisteractivityEmail: EditText? = null
    private var mPositionChoose_worker: RadioButton? = null
    private var mPositionChoose_manager: RadioButton? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        mDBOpenHelper = DBOpenHelper(this) //MRK
        initView()
    }

    private fun initView() {
        val mBtRegisteractivityRegister = findViewById<Button>(R.id.bt_registeractivity_register)
        val mIvRegisteractivityBack = findViewById<ImageView>(R.id.iv_registeractivity_back)
        mEtRegisteractivityUsername = findViewById(R.id.et_registeractivity_username)
        mEtRegisteractivityPassword1 = findViewById(R.id.et_registeractivity_password1)
        mEtRegisteractivityPassword2 = findViewById(R.id.et_registeractivity_password2)
        mEtRegisteractivityEmail = findViewById(R.id.et_registeractivity_email)
        mPositionChoose_worker = findViewById(R.id.radioButton_worker)
        mPositionChoose_manager = findViewById(R.id.radioButton_manager)
        mIvRegisteractivityBack.setOnClickListener(this)
        mBtRegisteractivityRegister.setOnClickListener(this)
    }

    @SuppressLint("NonConstantResourceId")
    override fun onClick(view: View) {
        when (view.id) {
            R.id.iv_registeractivity_back -> {
                val intent1 = Intent(this, LoginActivity::class.java)
                startActivity(intent1)
                finish()
            }
            R.id.bt_registeractivity_register -> {
                //get info
                val username = mEtRegisteractivityUsername!!.text.toString().trim { it <= ' ' }
                val password = mEtRegisteractivityPassword1!!.text.toString().trim { it <= ' ' }
                val password2 = mEtRegisteractivityPassword2!!.text.toString().trim { it <= ' ' }
                val email = mEtRegisteractivityEmail!!.text.toString().trim { it <= ' ' }
                var identity: String? = null
                if (mPositionChoose_manager!!.isChecked){
                    identity = "manager"
                }else if (mPositionChoose_worker!!.isChecked){
                    identity = "worker"
                }
                if(password == password2) {
                    if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(email)) {
                        if (!username.contains(",")) {
                            //sent info to database
                            if (identity != null) {
                                mDBOpenHelper!!.add(username, password, identity, email)
                            }
                            val intent2 = Intent(this, LoginActivity::class.java)
                            startActivity(intent2)
                            finish()
                            Toast.makeText(this, "Verification passed, registration successful", Toast.LENGTH_SHORT).show()
                            val hashMap = hashMapOf<String, Any?>(
                                "username" to username,
                                "password" to password,
                                "identity" to identity,
                                "email" to email

                            ) //make hasmap of data
                            RegisterActivityProject.FirebaseUtils().fireStoreDatabase.collection("user")
                                .document("User: $username")
                                .set(hashMap)
                                .addOnSuccessListener {
                                    Log.d(TAG, "Added document with ID $username")
                                }
                                .addOnFailureListener { exception ->
                                    Log.w(TAG, "Error adding document $exception")
                                }
                        } else {
                            Toast.makeText(
                                this,
                                "user names can not contain commas",
                                Toast.LENGTH_SHORT
                            ).show()
                        }//error check
                    } else {
                        Toast.makeText(
                            this,
                            "Not perfect information, registration failed",
                            Toast.LENGTH_SHORT
                        ).show()
                    }//error check
                }else{
                    Toast.makeText(
                        this,
                        "Passwords do not match",
                        Toast.LENGTH_SHORT
                    ).show()
                }//error check

            }
        }
    }
}