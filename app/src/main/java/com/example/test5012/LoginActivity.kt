package com.example.test5012


import androidx.appcompat.app.AppCompatActivity
import com.example.test5012.DBOpenHelper
import android.os.Bundle
import android.content.Intent
import com.example.test5012.RegisterActivity
import android.text.TextUtils
import android.view.View
import android.widget.*

import android.R

import com.example.test5012.databinding.ActivityLoginBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.util.ArrayList


class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var myLoginActivity: ActivityLoginBinding

    private var mDBOpenHelper: DBOpenHelper? = null
    private var mTvLoginactivityRegister: TextView? = null
    private var mRlLoginactivityTop: RelativeLayout? = null
    private var mEtLoginactivityUsername: EditText? = null
    private var mEtLoginactivityPassword: EditText? = null
    private var mLlLoginactivityTwo: LinearLayout? = null
    private var mBtLoginactivityLogin: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myLoginActivity = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(myLoginActivity.root)
        initView()
        mDBOpenHelper = DBOpenHelper(this)

    }
    class FirebaseUtils {
        val fireStoreDatabase = FirebaseFirestore.getInstance()
    }
    private fun initView() {
        mBtLoginactivityLogin = myLoginActivity.btLoginactivityLogin
        mTvLoginactivityRegister = myLoginActivity.tvLoginactivityRegister
        mRlLoginactivityTop = myLoginActivity.rlLoginactivityTop
        mEtLoginactivityUsername = myLoginActivity.etLoginactivityUsername
        mEtLoginactivityPassword = myLoginActivity.etLoginactivityPassword
        mLlLoginactivityTwo = myLoginActivity.llLoginactivityTwo

        // get all the components
        mBtLoginactivityLogin!!.setOnClickListener(this)
        mTvLoginactivityRegister!!.setOnClickListener(this)
    }       // set events

    override fun onClick(view: View) {
        when (view.id) {
            com.example.test5012.R.id.tv_loginactivity_register -> {
                startActivity(Intent(this, RegisterActivity::class.java))
                finish()
            }
            com.example.test5012.R.id.bt_loginactivity_login -> {
                val name = mEtLoginactivityUsername!!.text.toString().trim { it <= ' ' }
                val password = mEtLoginactivityPassword!!.text.toString().trim { it <= ' ' }
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password)) {
                    val data: ArrayList<NewUser> = mDBOpenHelper!!.allData
                    var match = false
                    var i = 0
                    while (i < data.size) {
                        val user: NewUser = data[i]
                        if (name == user.getName() && password == user.getPassword()) {
                            match = true
                            break
                        } else {
                            match = false
                        }
                        i++
                    }
                    if (match) {
                        Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        val username :String = data[i].getName()!!
                        val posistion :String = data[i].getIdentity()!!
                        intent.putExtra("user",username)
                        intent.putExtra("position",posistion)
                        startActivity(intent)
                        finish() //销毁此Activity
                    } else {
                        Toast.makeText(
                            this,
                            "User name or password is incorrect, please re-enter",
                            Toast.LENGTH_SHORT
                        ).show()
                    }// error check
                } else {
                    Toast.makeText(
                        this,
                        "Please enter your username or password",
                        Toast.LENGTH_SHORT
                    ).show()
                }// error check
            }
        }
    }

}