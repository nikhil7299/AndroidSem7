package com.example.sem7all

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast

class ContactDetails(val title : String, val description: String, val image : Int, var checked : Boolean){}

class CustomListView (context: Context, private var resource:Int, var objects: MutableList<ContactDetails>):
    ArrayAdapter<ContactDetails>(context,resource,objects){

    lateinit var checkBox: CheckBox
    override fun getCount(): Int {
        return objects.size
    }

    private fun isChecked(position: Int):Boolean{
        return objects.get(position).checked
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = layoutInflater.inflate(resource,null)
        val imageView: ImageView = view.findViewById(R.id.image)
        val titleText : TextView = view.findViewById(R.id.titleText)
        val descriptionText : TextView = view.findViewById(R.id.descriptionText)
        val deleteButton : Button = view.findViewById(R.id.deleteButton)
        checkBox = view.findViewById(R.id.checkbox)

        val mItem : ContactDetails = objects[position]
//        imageView.setImageDrawable(context.resources.getDrawable(mItem.image))
        imageView.setImageDrawable(context.resources.getDrawable(mItem.image))
        titleText.text = mItem.title
        descriptionText.text = mItem.description

        deleteButton.setOnClickListener{
//            val itemSelected = objects.get(it.tag as Int)
            val itemSelected = objects.get(position)
            objects.remove(itemSelected)
            notifyDataSetChanged()
        }

        checkBox.setChecked(objects.get(position).checked)
        checkBox.setTag(position)
        val itemStr =  objects.get(position).title

        checkBox.setOnClickListener {
            val newState: Boolean = !objects[position].checked
            objects[position].checked = newState
            Toast.makeText(
                context,
                itemStr + "Selected with state : " + newState,
                Toast.LENGTH_LONG
            ).show()
        }
        checkBox.setChecked(isChecked(position))

        return  view
    }
}

class U11CustomListView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.u11_custom_list_view)
        var listView = findViewById<ListView>(R.id.listView)
        var cartButton = findViewById<Button>(R.id.cartButton)

        val people = mutableListOf<ContactDetails>(
            ContactDetails("Nikhil","12001345",R.drawable.ic_android_black_24dp,false),
            ContactDetails("Abhishek","12001455",R.drawable.ic_android_black_24dp,false),
            ContactDetails("Avinash","12001455",R.drawable.ic_android_black_24dp,false),
            ContactDetails("Arnav","12001635",R.drawable.ic_android_black_24dp,false),
            ContactDetails("Rishabh","12006455",R.drawable.ic_android_black_24dp,false),
            ContactDetails("Ashish","12007955",R.drawable.ic_android_black_24dp,false),
        )

        listView.adapter = CustomListView(this,R.layout.u11_custom_list_item, objects = people)

        cartButton.setOnClickListener{

        }
    }
}