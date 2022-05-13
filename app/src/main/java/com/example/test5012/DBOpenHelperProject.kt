package com.example.test5012

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DBOpenHelperProject (context: Context?):
        SQLiteOpenHelper(context, "db_project", null, 1) {
            private val db: SQLiteDatabase = readableDatabase
    override fun onCreate(db: SQLiteDatabase) {
                db.execSQL(
                    "CREATE TABLE IF NOT EXISTS newProject(" +
                            "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "projectName TEXT DEFAULT 'IDEL'," +
                            "deadline TEXT DEFAULT 'IDEL'," +
                            "task TEXT DEFAULT 'IDEL'," +
                            "worker TEXT DEFAULT 'IDEL'," +
                            "state TEXT DEFAULT 'IDEL')"

                )
            }

    override fun onOpen(db: SQLiteDatabase) {
        db.execSQL(
            ("CREATE TABLE IF NOT EXISTS newProject(" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "projectName TEXT DEFAULT 'IDEL'," +
                    "deadline TEXT DEFAULT 'IDEL'," +
                    "task TEXT DEFAULT 'IDEL'," +
                    "worker TEXT DEFAULT 'IDEL'," +
                    "state TEXT DEFAULT 'IDEL')")
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS newProject")
        onCreate(db)
    }

    fun add(projectName: String, deadline: String, task: String, worker: String, state: String) {
        db.execSQL(
            "INSERT INTO newProject (projectName,deadline,task,worker,state) VALUES(?,?,?,?,?)",
            arrayOf<Any>(projectName, deadline, task, worker, state)
        )
        //db.close()
    }
/*
 fun add(name: String, password: String, position: String) {
        db.execSQL(
            "INSERT INTO newUser (name,password,position) VALUES(?,?,?)",
            arrayOf<Any>(name, password, position)
        )
        db.close()
    }
 */
    fun delete(projectName: String, deadline: String, task: String, worker: String, state: String) {
        db.execSQL("DELETE FROM newProject WHERE projectName = AND deadline = AND task = AND task = AND worker = AND state = $projectName$deadline$task$worker$state")
    }

    fun updata(state: String) {
        db.execSQL("UPDATE newProject SET state = ?", arrayOf<Any>(state))
    }

    @SuppressLint("Range")
    fun getProjByName(workerName: String): String? {
        //db.execSQL("SELECT projectName FROM newProject WHERE worker = workerName")
        val cursor = db.rawQuery("SELECT projectName FROM newProject WHERE worker = workerName",null)
        return cursor.getString(0)


    }
    fun getDDLByName(workerName: String) {
        db.execSQL("SELECT deadline FROM newProject WHERE worker = workerName")
    }
    fun getTaskByName(workerName: String) {
        db.execSQL("SELECT task FROM newProject WHERE worker = workerName")
    }
    fun getStateByName(workerName: String) {
        db.execSQL("SELECT state FROM newProject WHERE worker = workerName")

    }
    @SuppressLint("Range")
    fun getinfoByName(workerName: String): ArrayList<NewProject> {
        val mlist: ArrayList<NewProject> = ArrayList<NewProject>()
        val cursor = db.rawQuery("SELECT * FROM newProject WHERE worker = workerName", null)
        if(cursor.count != 0){
            var mProjName = cursor.getString(cursor.getColumnIndex("projectName"))
            var mDeadline = cursor.getString(cursor.getColumnIndex("deadline"))
            var mTask = cursor.getString(cursor.getColumnIndex("task"))
            var mState = cursor.getString(cursor.getColumnIndex("state"))
            var mName = cursor.getString(cursor.getColumnIndex("worker"))
            mlist.add(
                NewProject(
                    mProjName, mDeadline, mTask, mState, mName
                )
            )
        }
        cursor.close()
        return mlist
    }

    val allProjectDate: ArrayList<NewProject>
        @SuppressLint("Range")
        get() {
            val list: ArrayList<NewProject> = ArrayList<NewProject>()
            val cursor = db.query("newProject", null, null, null, null, null, "project DESC")
            while (cursor.moveToNext()){
                val projectName = cursor.getString(cursor.getColumnIndex("projectName"))
                val deadline = cursor.getString(cursor.getColumnIndex("deadline"))
                val task = cursor.getString(cursor.getColumnIndex("task"))
                val worker = cursor.getString(cursor.getColumnIndex("worker"))
                val state = cursor.getString(cursor.getColumnIndex("state"))
                list.add(
                    NewProject(
                    projectName, deadline, task, worker, state
                )
                )
            }
            cursor.close()
            return list
        }

}