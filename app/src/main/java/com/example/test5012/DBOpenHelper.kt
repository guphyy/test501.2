package com.example.test5012

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DBOpenHelper(context: Context?) :
    SQLiteOpenHelper(context, "db_test", null, 1) {
    private val db: SQLiteDatabase = readableDatabase
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS newUser(" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT," +
                    "password TEXT," +
                    "position, TEXT," +
                    "Create_time, DATATIME1," +
                    "update_time, DATATIME2)"
        )
    }

    //Updating in the onUpgrade function often doesn't work, a better solution is to update the database in onOpen
    override fun onOpen(db: SQLiteDatabase) {
        db.execSQL(
            ("CREATE TABLE IF NOT EXISTS newUser(" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT," +
                    "password TEXT," +
                    "position, TEXT," +
                    "Create_time, DATATIME1," +
                    "update_time, DATATIME2)")
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS newUser")
        onCreate(db)
    }

    fun add(name: String, password: String, position: String) {
        db.execSQL(
            "INSERT INTO newUser (name,password,position) VALUES(?,?,?)",
            arrayOf<Any>(name, password, position)
        )
        db.close()
    }

    fun delete(name: String, password: String) {
        db.execSQL("DELETE FROM newUser WHERE name = AND password =$name$password")
    }

    fun updata(password: String) {
        db.execSQL("UPDATE newUser SET password = ?", arrayOf<Any>(password))
    }

    val allData: ArrayList<NewUser>
        @SuppressLint("Range")
        get() {
            val list: ArrayList<NewUser> = ArrayList<NewUser>()
            val cursor = db.query("newUser", null, null, null, null, null, "name DESC")
            while (cursor.moveToNext()) {
                val name = cursor.getString(cursor.getColumnIndex("name"))
                val password = cursor.getString(cursor.getColumnIndex("password"))
                val identity = cursor.getString(cursor.getColumnIndex("position"))
                list.add(NewUser(name, password, identity))
            }
            cursor.close()
            return list
        }

}