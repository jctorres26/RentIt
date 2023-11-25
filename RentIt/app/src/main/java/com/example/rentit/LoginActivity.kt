package com.example.rentit

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.datastore.core.DataStore
import androidx.datastore.createDataStore
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.util.prefs.Preferences

class LoginActivity : AppCompatActivity() {

    var txtEmailLog: EditText? = null
    var txtPasswordLog: EditText? = null
    lateinit var btnLogin: Button
    lateinit var sharedPref: SharedPreferences
    lateinit var editor: SharedPreferences.Editor



    val url: String = "https://rentitapp123.000webhostapp.com/includes/login_inc.php?action=login"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        txtEmailLog = findViewById(R.id.txtEmailLog)
        txtPasswordLog = findViewById(R.id.txtPasswordLog)
        btnLogin = findViewById(R.id.btnLogin)


        sharedPref = getSharedPreferences("myPref", MODE_PRIVATE)
        editor = sharedPref.edit()


    }

    fun clickBtnLogin(view: View){

        var intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("email", txtEmailLog?.text.toString())
        intent.putExtra("password", txtPasswordLog?.text.toString())



        val queue = Volley.newRequestQueue(this)

        var resultadoPost = object : StringRequest(Request.Method.POST, url,
            Response.Listener<String> { response ->

                val json = JSONObject(response)
                if (json.getString("error") == "true") {
                    Toast.makeText(this, "Email o contraseña incorrectos", Toast.LENGTH_SHORT)
                        .show()
                } else {

                    startActivity(intent)

                    editor.apply {
                        putString("name", json.getString("name"))
                        putString("lastName", json.getString("lastName"))
                        putString("username", json.getString("username"))
                        putString("password", json.getString("password"))
                        putString("img", json.getString("img"))
                        putInt("userId", json.getInt("userId"))
                        putInt("userType", json.getInt("userType"))
                        apply()
                    }

                }
            }, Response.ErrorListener { error ->
                Toast.makeText(this, "Error $error ", Toast.LENGTH_LONG).show()
            }) {
            override fun getParams(): MutableMap<String, String>? {
                val parametros = HashMap<String, String>()

                parametros.put("email", txtEmailLog?.text.toString())
                parametros.put("password", txtPasswordLog?.text.toString())


                return parametros

            }
        }

        queue.add(resultadoPost)

    }

    fun clickRegister(view: View){ //CLICK TEXTO DE IR A REGISTRO
        var intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

}

