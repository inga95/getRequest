package com.example.getrequest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.StringRequestListener
import com.example.getrequest.adapter.ImagesAdapter
import com.example.getrequest.model.ImagesApi
import org.json.JSONObject
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    val imageList = ArrayList<ImagesApi>()

    private lateinit var imagesRV: RecyclerView
    private lateinit var searchBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //init views
        imagesRV = findViewById(R.id.imagesRecyclerView)
        searchBtn = findViewById(R.id.searchBtn)

        //Setting up Staggered Layout manager
        imagesRV.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)

        searchBtn.setOnClickListener{

            //call search Dog function
            searchImages()
        }

        /*imageList.clear()

        AndroidNetworking.initialize(this)
        val tvGetOutPut: TextView = findViewById(R.id.textView)

        AndroidNetworking.get("http://api-edu.gtl.ai/api/v1/imagesearch/bing")
            .addQueryParameter("url", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSuf1V1TmXJDxJN1kfbdB01vqpnK3_O2lIKVQ&usqp=CAU")
            .setTag("test")
            .setPriority(Priority.LOW)
            .build()
            .getAsJSONArray(object : JSONArrayRequestListener{
                override fun onResponse(response: JSONArray) {
                    tvGetOutPut.text = "GET response: " + response.toString()
                }

                override fun onError(error: ANError?) {
                    tvGetOutPut.text = "GET response: " + error.toString()
                }
            })*/
        }

    private fun searchImages() {
        imageList.clear()

        thread {
            AndroidNetworking.initialize(this)

            AndroidNetworking.get("http://api-edu.gtl.ai/api/v1/imagesearch/bing")
                .addQueryParameter(
                    "url",
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSuf1V1TmXJDxJN1kfbdB01vqpnK3_O2lIKVQ&usqp=CAU"
                )
                .setPriority(Priority.LOW)
                .build()
                .getAsString(object : StringRequestListener {
                    override fun onResponse(response: String?) {
                        val result = JSONObject(response)
                        val image = result.getJSONArray("thumbnail_link")

                        for (i in 0 until image.length()) {
                            val list = image.get(i)
                            imageList.add(
                                ImagesApi(list.toString())
                            )
                        }

                        imagesRV.adapter = ImagesAdapter(this@MainActivity, imageList)
                    }

                    override fun onError(error: ANError?) {
                        Log.i("error", "GET response: " + error.toString())
                    }
                })
        }
    }
}
