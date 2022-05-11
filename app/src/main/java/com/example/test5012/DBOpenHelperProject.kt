package com.example.test5012

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBOpenHelperProject (context: Context?):
        SQLiteOpenHelper(context, "db_project", null, 1) {
            private val db: SQLiteDatabase = readableDatabase
    override fun onCreate(db: SQLiteDatabase) {
                db.execSQL(
                    "CREATE TABLE IF NOT EXISTS newProject(" +
                            "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "projectName TEXT," +
                            "deadline TEXT," +
                            "task, TEXT," +
                            "worker, TEXT," +
                            "state, TEXT)"

                )
            }

    override fun onOpen(db: SQLiteDatabase) {
        db.execSQL(
            ("CREATE TABLE IF NOT EXISTS newProject(" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "projectName TEXT," +
                    "deadline TEXT," +
                    "task, TEXT," +
                    "worker, TEXT," +
                    "state, TEXT)")
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

    fun delete(projectName: String, deadline: String, task: String, worker: String, state: String) {
        db.execSQL("DELETE FROM newProject WHERE projectName = AND deadline = AND task = AND task = AND worker = AND state = $projectName$deadline$task$worker$state")
    }

    fun updata(state: String) {
        db.execSQL("UPDATE newProject SET state = ?", arrayOf<Any>(state))
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