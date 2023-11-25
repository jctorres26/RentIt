package com.example.rentit

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.util.regex.Pattern

class UserProfileActivity : AppCompatActivity() {

    lateinit var txtUsernameUserProf: EditText
    lateinit var txtPasswordUserProf: EditText
    lateinit var txtNameUserProf: TextView
    lateinit var btnNuevaPublicacion: Button
    lateinit var imgViewUserImgUserProf: ImageView
    lateinit var rvUserProf: RecyclerView

    lateinit var sharedPref: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    lateinit var jsonArrayRequest: JsonArrayRequest

    var postList = arrayListOf<Post>()

    val passwordREGEX = Pattern.compile("^" +
            "(?=.*[0-9])" +         //at least 1 digit
            "(?=.*[a-z])" +         //at least 1 lower case letter
            "(?=.*[A-Z])" +         //at least 1 upper case letter
            "(?=.*[a-zA-Z])" +      //any letter
            "(?=\\S+$)" +           //no white spaces
            ".{8,}" +               //at least 8 characters
            "$");

    val url : String = "https://rentitapp123.000webhostapp.com/includes/register_inc.php?action=update"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_profile)

        val queue = Volley.newRequestQueue(this)

        sharedPref = getSharedPreferences("myPref", MODE_PRIVATE)
        editor = sharedPref.edit()

        txtUsernameUserProf = findViewById(R.id.txtUsernameUserProf)
        txtPasswordUserProf = findViewById(R.id.txtPasswordUserProf)
        txtNameUserProf = findViewById(R.id.txtNameUserProf)
        btnNuevaPublicacion = findViewById(R.id.btnNuevaPublicacionUserProf)
        imgViewUserImgUserProf = findViewById(R.id.imgViewUserImgUserProf)
        rvUserProf = findViewById(R.id.rvUserProf)

        txtUsernameUserProf.setText(sharedPref.getString("username", null))
        txtPasswordUserProf.setText(sharedPref.getString("password", null))
        txtNameUserProf.setText((sharedPref.getString("name", null)) + " " + sharedPref.getString("lastName", null))

        val decodedString: ByteArray = Base64.decode((sharedPref.getString("img",null)), Base64.DEFAULT)
        val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        imgViewUserImgUserProf.setImageBitmap(decodedByte)

        if(sharedPref.getInt("userType", 0) == 1) {
            btnNuevaPublicacion.visibility = View.INVISIBLE
            rvUserProf.visibility = View.INVISIBLE
        }else{

            val url2 = "https://rentitapp123.000webhostapp.com/includes/rentas_inc.php?action=getUserPosts&userId=" + sharedPref.getInt("userId",0).toString()
            jsonArrayRequest = JsonArrayRequest(
                Request.Method.GET, url2 ,null,
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
                    rvUserProf.layoutManager = LinearLayoutManager(this)
                    rvUserProf.adapter = adapter
                    adapter.setOnItemClickListener(object: PostAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            //Toast.makeText(this@UserProfileActivity,postList[position].id,Toast.LENGTH_SHORT).show()
                            var intent = Intent(this@UserProfileActivity, PostEditActivity::class.java)
                            intent.putExtra("rentaId", postList[position].id)
                            startActivity(intent)
                        }
                    })

                }, { error ->
                    Toast.makeText(this,"Error", Toast.LENGTH_LONG).show()
                    Log.d("error",error.toString())
                })


            queue.add(jsonArrayRequest)

        }
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

    fun clickBtnNuevoPost(view:View){
        var intent = Intent(this, RegisterPostActivity::class.java)
        startActivity(intent)
    }

    fun clickBtnActualizarDatos(view:View){

        var intent = Intent(this, UserProfileActivity::class.java)

        val queue = Volley.newRequestQueue(this)
        var resultadoPost = object : StringRequest(Request.Method.POST, url,
            Response.Listener<String> { response ->

                val json = JSONObject(response)
                if (json.getString("error") == "true") {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    editor.apply {

                        putString("username", txtUsernameUserProf.text.toString())
                        putString("password", txtPasswordUserProf.text.toString())
                        apply()
                    }
                    startActivity(intent)

                }
            }, Response.ErrorListener { error ->
                Toast.makeText(this, "Error $error ", Toast.LENGTH_LONG).show()
            }) {
            override fun getParams(): MutableMap<String, String>? {
                val parametros = HashMap<String, String>()

                parametros.put("userId", (sharedPref.getInt("userId",0)).toString())
                parametros.put("username", txtUsernameUserProf.text.toString())
                parametros.put("password", txtPasswordUserProf.text.toString())


                return parametros

            }
        }

        if(isValidPassword(txtPasswordUserProf.text.toString()) && txtUsernameUserProf.text.isNotEmpty()){
        queue.add(resultadoPost)

        }else if(txtUsernameUserProf.text.isEmpty()){
            Toast.makeText(this,"Favor de llenar todos los campos", Toast.LENGTH_SHORT).show()
        }else if(!isValidPassword(txtPasswordUserProf.text.toString())){
            Toast.makeText(this,"Formato de contraseña incorrecto", Toast.LENGTH_SHORT).show()
        }



    }

    fun isValidPassword(password: String): Boolean {
        return passwordREGEX.matcher(password).matches()

    }

}