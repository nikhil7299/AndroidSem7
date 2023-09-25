package com.example.sem7all

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class GridRecyclerViewModel(var textView: String, var imageView: Int) {

}
class GridRecyclerViewAdapter (var context: Context, var arrayList: ArrayList<GridRecyclerViewModel>): RecyclerView.Adapter<GridRecyclerViewAdapter.GridRecyclerViewHolder>(){

    class GridRecyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        lateinit var textView : TextView
        lateinit var imageView : ImageView

        init {
            textView = itemView.findViewById(R.id.textView)
            imageView = itemView.findViewById(R.id.imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridRecyclerViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.u13_recycler_grid_item,parent, false)
        return GridRecyclerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: GridRecyclerViewHolder, position: Int) {
        holder.textView.text = arrayList[position].textView
        holder.imageView.setImageResource(arrayList[position].imageView)
    }
}
class U13RecyclerGridView : AppCompatActivity() {
    lateinit var arrayList: ArrayList<GridRecyclerViewModel>
    lateinit var recyclerView: RecyclerView
    var gridRecyclerViewAdapter: GridRecyclerViewAdapter? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.u13_recycler_grid_view)

        gridListData()
        recyclerView = findViewById(R.id.recyclerGridView)
        recyclerView.setHasFixedSize(true)
        val gridLayoutManager = GridLayoutManager(this, 3)
        gridLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerView.layoutManager = gridLayoutManager
//        recyclerView.setLayoutManager(gridLayoutManager)
        gridRecyclerViewAdapter = GridRecyclerViewAdapter(this, arrayList)
        recyclerView.setAdapter(gridRecyclerViewAdapter)
    }
    private fun gridListData() {
        arrayList = ArrayList<GridRecyclerViewModel>()
        arrayList.add(GridRecyclerViewModel("Mobile", R.drawable.ic_smartphone))
        arrayList.add(GridRecyclerViewModel("Internet", R.drawable.ic_smartphone))
        arrayList.add(GridRecyclerViewModel("WiFi", R.drawable.ic_smartphone))
        arrayList.add(GridRecyclerViewModel("Database", R.drawable.ic_smartphone))
        arrayList.add(GridRecyclerViewModel("Battery", R.drawable.ic_smartphone))
        arrayList.add(GridRecyclerViewModel("Alarm", R.drawable.ic_smartphone))
        arrayList.add(GridRecyclerViewModel("Signal", R.drawable.ic_smartphone))
        arrayList.add(GridRecyclerViewModel("Bluetooth", R.drawable.ic_smartphone))
        arrayList.add(GridRecyclerViewModel("Monitor", R.drawable.ic_smartphone))
        arrayList.add(GridRecyclerViewModel("Wallpaper", R.drawable.ic_smartphone))
    }
}