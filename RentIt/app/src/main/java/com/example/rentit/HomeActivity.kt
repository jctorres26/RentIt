package com.example.rentit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley

class HomeActivity : AppCompatActivity() {

    lateinit var rvPostsHome: RecyclerView

    var postList = arrayListOf<Post>()
    val url = "https://rentitapp123.000webhostapp.com/includes/rentas_inc.php?action=getActivePosts"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        rvPostsHome = findViewById(R.id.rvPostsHome)

        val queue = Volley.newRequestQueue(this)


        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url,null,
            { response ->


                for (i in 0..response.length()-1){
                    val jsonObj = response.getJSONObject(i)
                    Log.d("name",jsonObj.toString())

                    val post = Post(
                        jsonObj.getString("rentaId"),
                        jsonObj.getString("titulo"),
                        jsonObj.getString("ciudad"),
                        jsonObj.getString("precio"),
                        jsonObj.getString("img")
                    )

                    postList.add(post)


                }

                var adapter = PostAdapter(postList)
                rvPostsHome.layoutManager = LinearLayoutManager(this)
                rvPostsHome.adapter = adapter
                adapter.setOnItemClickListener(object: PostAdapter.onItemClickListener{
                    override fun onItemClick(position: Int) {
                        //Toast.makeText(this@HomeActivity,postList[position].id,Toast.LENGTH_SHORT).show()
                        var intent = Intent(this@HomeActivity, PostDetailActivity::class.java)
                        intent.putExtra("rentaId", postList[position].id)
                        startActivity(intent)

                    }
                })





            }, { error ->
                Toast.makeText(this,"Error", Toast.LENGTH_LONG).show()
            })

        queue.add(jsonArrayRequest)

    }

    fun clickBtnProfile(view: View){
        var intent = Intent(this, UserProfileActivity::class.java)
        startActivity(intent)
    }

    fun clickBtnHome(view: View){
        var intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    fun clickBtnLogout(view:View){
        var intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

}