package com.example.sem7all

import android.app.ProgressDialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class U22AsyncImageDownload : AppCompatActivity() {

    lateinit var imageUrl : URL
    var `is`: InputStream? =null
    var bitmap : Bitmap? = null
    lateinit var asyncDownloadBtn : Button
    lateinit var asyncImageView: ImageView
    lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.u22_async_image_download)
        asyncDownloadBtn = findViewById(R.id.asyncDownloadBtn)
        asyncImageView = findViewById(R.id.asyncImageView)

        asyncDownloadBtn.setOnClickListener{
            AsyncDownloadTask().execute("https://www.cleverfiles.com/howto/wp-content/uploads/2018/03/minion.jpg")
        }
    }
    private  inner class AsyncDownloadTask: AsyncTask<String?, String?, Bitmap?>(){

        override fun onPreExecute() {
            super.onPreExecute()
            progressDialog = ProgressDialog(this@U22AsyncImageDownload)
            progressDialog.setMessage("Please wait ... Downloading")
            progressDialog.setCancelable(false)
            progressDialog.show()
        }
        override fun doInBackground(vararg params: String?): Bitmap? {
            try {
                imageUrl = URL(params[0])
                val connection : HttpURLConnection = imageUrl.openConnection() as HttpURLConnection
                connection.doInput = true
//               connection.setDoInput(true)
                connection.connect()
                `is` = connection.inputStream
                val options = BitmapFactory.Options()
                options.inPreferredConfig = Bitmap.Config.RGB_565
                bitmap = BitmapFactory.decodeStream(`is`,null,options)

            }catch (e : IOException){
                e.printStackTrace()
            }
            return bitmap
        }

        override fun onPostExecute(result: Bitmap?) {
            super.onPostExecute(result)
            if (asyncImageView !=null){
                progressDialog.hide()
                asyncImageView.setImageBitmap(bitmap)

            }
            else {
                progressDialog.show()
            }
        }
    }

}