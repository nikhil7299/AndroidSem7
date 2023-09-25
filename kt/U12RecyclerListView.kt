package com.example.sem7all

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

//Model
class ResultModel (semesterNumber : String?,  year : String?,  cgpa : String?){

    private var semesterNumber : String
    private var year : String
    private var cgpa : String

    init{
        this.semesterNumber = semesterNumber !!
        this.cgpa = cgpa !!
        this.year = year !!
    }

//    fun setSemester(name: String?){
//        semesterNumber = name!!
//    }

//    fun setYear(year: String?){
//        this.year = year!!
//    }

    //    fun setCgpa(cgpa: String?){
//        this.cgpa = cgpa !!
//    }
    fun getCgpa(): String?{
        return cgpa
    }
    fun getSemester(): String {
        return semesterNumber
    }
    fun getYear():String?{
        return year
    }
}
//Adapter
class RecyclerAdapter (private var resultModelList : List<ResultModel>): RecyclerView.Adapter<RecyclerAdapter.ResultViewHolder>() {

    inner class ResultViewHolder( itemView : View): RecyclerView.ViewHolder(itemView){
        var semesterNumber : TextView = itemView.findViewById(R.id.semesterNumberText)
        var cgpa : TextView = itemView.findViewById(R.id.cgpaText)
        var year : TextView = itemView.findViewById(R.id.yearText)
        var deleteButton : Button = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.u12_recycler_list_item,parent,false)
        return  ResultViewHolder(layoutInflater)
        //delete button implementation
    }

    override fun getItemCount(): Int {
        return resultModelList.size
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        val result = resultModelList[position]
        holder.semesterNumber.text = result.getSemester()
        holder.cgpa.text = result.getCgpa()
        holder.year.text = result.getYear()
    }
}

// Main
class U12RecyclerListView : AppCompatActivity() {
    private val resultList = ArrayList<ResultModel>()
    private lateinit var recyclerAdapter: RecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.u12_recycler_list_view)

        val recyclerView : RecyclerView = findViewById(R.id.recyclerView)
        recyclerAdapter = RecyclerAdapter(resultList)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = recyclerAdapter
        addData()
    }
    private fun addData(){
        val resultData = ResultModel("Semester 1","2018","CGPA 7.76")
        resultList.add(resultData)
        resultList.add(ResultModel("Semester 2","2018","CGPA 8.76"))
        resultList.add(ResultModel("Semester 3","2019","CGPA 8.06"))
        resultList.add(ResultModel("Semester 4","2019","CGPA 8.30"))
        resultList.add(ResultModel("Semester 5","2020","CGPA 9.06"))
        resultList.add(ResultModel("Semester 6","2020","CGPA 7.86"))
        resultList.add(ResultModel("Semester 7","2021","CGPA 8.60"))
        resultList.add(ResultModel("Semester 8","2021","CGPA 8.40"))
    }
}