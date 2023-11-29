package com.example.sql

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class DBHelper(context: Context,factory: SQLiteDatabase.CursorFactory?):
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {


    override fun onCreate(db: SQLiteDatabase?) {
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                NAME_COL + " TEXT," +
                AGE_COL + " TEXT" + ")")

        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }


    fun addName(name: String, age: String) {
//        below we are creating a content values variable
        val values = ContentValues()
//        we are inserting out values in the form of key-value pair
        values.put(NAME_COL, name)
        values.put(AGE_COL, age)
//        here we are creating a writable variable of our database as we want to
//        insert value in  our database
        val db = this.writableDatabase
//        all values are inserted into database
        db.insert(TABLE_NAME, null, values)
//        at last we are closing our databse
        db.close()
    }

    fun updateCourse(name: String, age: String?) {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(NAME_COL, name)
        values.put(AGE_COL, age)

        db.update(TABLE_NAME, values, "NAME=?", arrayOf(name))
        db.close()
    }

    fun deleteDetail(age: String?) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "AGE=?", arrayOf(age))
        db.close()
    }

    fun getName(): Cursor? {
//        here we are creating a readable
//        variable of out databse
//        as we want to read value from it
        val db = this.readableDatabase
//        below code returns a cursor to
//        read data from the database

        return db.rawQuery("SELECT * FROM " + TABLE_NAME ,null)
//        return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE AGE >= 50" ,null)

//       #SelfComment: Not Querying actual backend

    }

    companion object {
        private val DATABASE_NAME = "CSE_226"
        private val DATABASE_VERSION = 1
        val TABLE_NAME = "my_table1"
        val ID_COL = "id"
        val NAME_COL = "name"
        val AGE_COL = "age"
    }
}

class MainActivity : AppCompatActivity() {
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val addNameBtn: Button = findViewById(R.id.btn_name)
        val printDataBtn: Button = findViewById(R.id.btn_printname)
        val enterName: EditText = findViewById(R.id.edt_name)
        val enterAge: EditText = findViewById(R.id.edt_age)
        val nameTextView: TextView = findViewById(R.id.txt_name)
        val ageTextView: TextView = findViewById(R.id.txt_age)
        val updateBtn: Button = findViewById(R.id.btn_update)

        addNameBtn.setOnClickListener {
            val name = enterName.text.toString()
            val age = enterAge.text.toString()

            val db = DBHelper(this, null)
            db.addName(name,age)

            Toast.makeText(this@MainActivity, name + " added to database", Toast.LENGTH_SHORT)
                .show()
            enterName.text.clear()
            enterAge.text.clear()
        }

        printDataBtn.setOnClickListener {
            // creating a DBHelper class
            // and passing context to it
            val db = DBHelper(this, null)

            // below is the variable for cursor
            // we have called method to get
            // all names from our database
            // and add to name text view
            val cursor = db.getName()
            nameTextView.text = "Name\n\n"
            ageTextView.text = "Age\n\n"
            // moving the cursor to first position and
            // appending value in the text view
            cursor!!.moveToFirst()
            nameTextView.append(
                cursor.getString(cursor.getColumnIndex(DBHelper.NAME_COL))
                        + "\n\n"
            )
            ageTextView.append(
                cursor.getString(cursor.getColumnIndex(DBHelper.AGE_COL))
                        + "\n\n"
            )
            // moving our cursor to next
            // position and appending values
            while (cursor.moveToNext()) {
                nameTextView.append(
                    cursor.getString(
                        cursor.getColumnIndex
                            (DBHelper.NAME_COL)
                    ) + "\n"
                )
                ageTextView.append(
                    cursor.getString
                        (cursor.getColumnIndex(DBHelper.AGE_COL)) + "\n"
                )
            }
            // at last we close our cursor
            cursor.close()
        }
        updateBtn.setOnClickListener {
            val name = enterName.text.toString()
            val age = enterAge.text.toString()


            DBHelper(this, null).updateCourse(name, age)
            Toast.makeText(this@MainActivity, name + " added to database", Toast.LENGTH_SHORT)
                .show()
            enterName.text.clear()
            enterAge.text.clear()

        }




    }
}