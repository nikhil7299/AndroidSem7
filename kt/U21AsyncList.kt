package com.example.sem7all

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.Toast


class U21AsyncList : AppCompatActivity() {
    lateinit var progressBar: ProgressBar
    private lateinit var listView : ListView
    var array = arrayOf<String>("1","2","3","4","5","6","7","8","9","10")
    lateinit var arrayAdapter: ArrayAdapter<String>
    lateinit var arrayList: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.u21_async_list)

        progressBar = findViewById(R.id.asyncProgressBar)
        listView = findViewById(R.id.asyncListView)
        arrayList = ArrayList()
        arrayAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList)
        listView.adapter = arrayAdapter
        AsyncListTask().execute()
    }
    internal inner class AsyncListTask: AsyncTask<Void, Int?, String?>() {
        var count =0
        override fun onPreExecute() {
            super.onPreExecute()
            progressBar.max =10
            progressBar.progress =0
            progressBar.visibility = View.VISIBLE
            count =0
        }
        override fun doInBackground(vararg params: Void?): String? {
            for (i in 1..10){
                count += 1
                publishProgress(i)
                try {

                    Thread.sleep(1000)

                }
                catch (e:java.lang.Exception){
                    e.printStackTrace()
                }
            }
            return  "Task Completed"
        }

        override fun onProgressUpdate(vararg values: Int?) {
//            super.onProgressUpdate(*values)
            progressBar.progress = values[0]!!
            arrayList.add(array[count-1])
            arrayAdapter.notifyDataSetChanged()
        }

        override fun onPostExecute(result: String?) {
//            super.onPostExecute(result)
            Toast.makeText(this@U21AsyncList,result, Toast.LENGTH_LONG).show()
            progressBar.visibility = View.INVISIBLE

        }


    }
}