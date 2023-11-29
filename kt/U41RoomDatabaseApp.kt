package com.example.roomdatabase

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.lifecycle.LiveData
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Update
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// Dependency
    val roomVersion = "2.5.2"
    implementation("androidx.room:room-runtime:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")
    //kapt("androidx.room:room-compiler:$roomVersion")
    //  kapt ("org.jetbrains.kotlinx:kotlinx-metadata-jvm:0.5.0")

    ksp("androidx.room:room-compiler:$roomVersion")

    implementation("androidx.room:room-ktx:$roomVersion")


@Entity(tableName = "contact")
class Contact (
    @PrimaryKey(autoGenerate = true) val id:Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "phone") val phone: Long,

    )

@Dao
interface ContactDao  {
    @Insert
    suspend fun insert(contact: Contact) : Void

    @Update
    suspend fun update(contact: Contact) : Void

    @Delete
    suspend fun delete(contact: Contact) : Void

    @Query("SELECT * FROM contact")
    fun getContact(): LiveData<List<Contact>>
}

@Database(entities = [Contact::class], version = 1)
abstract class ContactDatabase : RoomDatabase() {
    abstract fun ContactDao() :ContactDao
}

class ListViewAdapter (private var appContext: Context, private var resourceLayout : Int, private var contactList : List<Contact>):
    ArrayAdapter<Contact>(appContext,resourceLayout,contactList){

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val layoutInflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = layoutInflater.inflate(resourceLayout,null)

        val idTextView : TextView = view.findViewById(R.id.idTextView)
        val nameTextView : TextView = view.findViewById(R.id.nameTextView)
        val phoneTextView : TextView = view.findViewById(R.id.phoneTextView)

        val contact : Contact = contactList[position]

        idTextView.text = contact.id.toString()
        nameTextView.text = contact.name
        phoneTextView.text = contact.phone.toString()

        return  view

    }

}


class RoomDatabase : AppCompatActivity() {
    lateinit var  listView : ListView
    lateinit var  contactDatabase: ContactDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_database)

        contactDatabase = Room.databaseBuilder(applicationContext,ContactDatabase::class.java,"contactDB").build()
        val displayBtn = findViewById<Button>(R.id.displayButton)
        listView = findViewById(R.id.listView)

        listView.setOnItemLongClickListener { parent, view, position, id ->
            val view = parent[position]
            val idText = view.findViewById<TextView>(R.id.idTextView).text.toString()
            val nameText = view.findViewById<TextView>(R.id.nameTextView).text.toString()
            val phoneText = view.findViewById<TextView>(R.id.phoneTextView).text.toString()

            val alertBuilder = AlertDialog.Builder(this)
            alertBuilder.setTitle("Edit")
            val linearLayout = LinearLayout(this)
            linearLayout.orientation = LinearLayout.VERTICAL

            val idEditText = EditText(this)
            idEditText.setText(idText)
            linearLayout.addView(idEditText)

            val nameEditText = EditText(this)
            nameEditText.text.clear()
            nameEditText.setText(nameText)
            linearLayout.addView(nameEditText)

            val phoneEditText = EditText(this)
            phoneEditText.text.clear()
            phoneEditText.setText(phoneText)
            linearLayout.addView(phoneEditText)

            alertBuilder.setView(linearLayout)
                .setPositiveButton("Edit",
                    DialogInterface.OnClickListener{
                            dialog, which ->
                        val updatedName = nameEditText.text.toString()
                        val updatedPhone = phoneEditText.text.toString()
                        GlobalScope.launch {
                            contactDatabase.ContactDao().update(Contact(idText.toLong(),updatedName,updatedPhone.toLong()))

                        }
                        Toast.makeText(this,"Update $updatedName $updatedPhone", Toast.LENGTH_LONG).show()
                    })
                .setNegativeButton("Cancle", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

            alertBuilder.show()
            return@setOnItemLongClickListener true
        }
        displayBtn.setOnClickListener {
            getData(it)
        }

        var addBtn = findViewById<Button>(R.id.addButton)
        addBtn.setOnClickListener {
            var idEditText = findViewById<EditText>(R.id.idEditText)
            var nameEditText = findViewById<EditText>(R.id.nameEditText)
            var phoneEditText = findViewById<EditText>(R.id.phoneEditText)
            GlobalScope.launch {
                contactDatabase.ContactDao().insert(
                    Contact(
                        idEditText.text.toString().toLong(),
                        nameEditText.text.toString(),
                        phoneEditText.text.toString().toLong()
                    )
                )
            }
        }
    }
    fun getData(view: View){
        contactDatabase.ContactDao().getContact().observe(this){
            val arrayAdapter = ListViewAdapter(this,R.layout.custom_list_element_layout,it)
            listView.adapter = arrayAdapter
        }
    }
}