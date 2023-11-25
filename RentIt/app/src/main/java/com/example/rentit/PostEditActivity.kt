package com.example.rentit

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class PostEditActivity : AppCompatActivity() {

    lateinit var imgViewEditPost: ImageView
    lateinit var txtTituloEditPost: EditText
    lateinit var txtPrecioEditPost: EditText
    lateinit var txtCuartosEditPost: EditText
    lateinit var txtBaniosEditPost: EditText
    lateinit var txtCiudadEditPost: TextView
    lateinit var txtColoniaEditPost: TextView
    lateinit var txtCalleEditPost: TextView
    lateinit var txtNumeroEditPost: EditText

    lateinit var rentaId: String;

    val url = "https://rentitapp123.000webhostapp.com/includes/rentas_inc.php?action=getByRentaId"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_edit)

         rentaId = intent.getStringExtra("rentaId").toString()

        imgViewEditPost = findViewById(R.id.imgViewEditPost)
        txtTituloEditPost = findViewById(R.id.txtTituloEditPost)
        txtPrecioEditPost = findViewById(R.id.txtPrecioEditPost)
        txtCuartosEditPost = findViewById(R.id.txtCuartosEditPost)
        txtBaniosEditPost = findViewById(R.id.txtBaniosEditPost)
        txtCiudadEditPost = findViewById(R.id.txtCiudadEditPost)
        txtColoniaEditPost = findViewById(R.id.txtColoniaEditPost)
        txtCalleEditPost = findViewById(R.id.txtCalleEditPost)
        txtNumeroEditPost = findViewById(R.id.txtNumeroEditPost)

        val queue = Volley.newRequestQueue(this)

        var resultadoPost = object : StringRequest(Request.Method.POST, url,
            Response.Listener<String> { response ->

                val json = JSONObject(response)
                if (json.getString("error") == "true") {
                    Toast.makeText(this, "Email o contraseña incorrectos", Toast.LENGTH_SHORT)
                        .show()
                } else {

                    val decodedString: ByteArray = Base64.decode(json.getString("img"), Base64.DEFAULT)
                    val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                    imgViewEditPost.setImageBitmap(decodedByte)

                    txtTituloEditPost.setText(json.getString("titulo"))
                    txtPrecioEditPost.setText(json.getString("precio"))
                    txtCuartosEditPost.setText(json.getString("numCuartos"))
                    txtBaniosEditPost.setText(json.getString("numBanios"))
                    txtCiudadEditPost.setText(json.getString("ciudad"))
                    txtColoniaEditPost.setText(json.getString("colonia"))
                    txtCalleEditPost.setText(json.getString("calle"))
                    txtNumeroEditPost.setText(json.getString("numero"))


                }
            }, Response.ErrorListener { error ->
                Toast.makeText(this, "Error $error ", Toast.LENGTH_LONG).show()
            }) {
            override fun getParams(): MutableMap<String, String>? {
                val parametros = HashMap<String, String>()

                parametros.put("rentaId", rentaId.toString())


                return parametros

            }
        }

        queue.add(resultadoPost)

    }


    fun clickBtnActualizar(view: View){
        var intent = Intent(this, UserProfileActivity::class.java)
        val url = "https://rentitapp123.000webhostapp.com/includes/rentas_inc.php?action=update"
        val queue = Volley.newRequestQueue(this)
        var resultadoPost = object : StringRequest(Request.Method.POST, url,
            Response.Listener<String> { response ->

                val json = JSONObject(response)
                if (json.getString("error") == "true") {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT)
                        .show()
                } else {

                    startActivity(intent)

                }
            }, Response.ErrorListener { error ->
                Toast.makeText(this, "Error $error ", Toast.LENGTH_LONG).show()
            }) {
            override fun getParams(): MutableMap<String, String>? {
                val parametros = HashMap<String, String>()

                parametros.put("rentaId", rentaId.toString())
                parametros.put("titulo",txtTituloEditPost.text.toString())
                parametros.put("calificacion", "5")
                parametros.put("precio",txtPrecioEditPost.text.toString())
                parametros.put("numCuartos",txtCuartosEditPost.text.toString())
                parametros.put("numBanios",txtBaniosEditPost.text.toString())
                parametros.put("ciudad", txtCiudadEditPost.text.toString())
                parametros.put("colonia", txtColoniaEditPost.text.toString())
                parametros.put("calle", txtCalleEditPost.text.toString())
                parametros.put("numero", txtNumeroEditPost.text.toString())
                parametros.put("isActive", "1")


                return parametros

            }
        }

        if( txtTituloEditPost?.text.toString().isNotEmpty()
            && txtPrecioEditPost?.text.toString().isNotEmpty() && txtCuartosEditPost?.text.toString().isNotEmpty()
            && txtBaniosEditPost?.text.toString().isNotEmpty() && txtCiudadEditPost?.text.toString().isNotEmpty()
            && txtColoniaEditPost?.text.toString().isNotEmpty() && txtCalleEditPost?.text.toString().isNotEmpty()
            && txtNumeroEditPost?.text.toString().isNotEmpty()){


            queue.add(resultadoPost)

        }else if( txtTituloEditPost?.text.toString().isEmpty() || txtPrecioEditPost?.text.toString().isEmpty() || txtCuartosEditPost?.text.toString().isEmpty()
            || txtBaniosEditPost?.text.toString().isEmpty() || txtCiudadEditPost?.text.toString().isEmpty() || txtColoniaEditPost?.text.toString().isEmpty()
            || txtCalleEditPost?.text.toString().isEmpty() || txtNumeroEditPost?.text.toString().isEmpty()){

            Toast.makeText(this,"Favor de llenar todos los campos", Toast.LENGTH_SHORT).show()
        }



    }

    fun clickBtnEliminar(view: View){
        var intent = Intent(this, UserProfileActivity::class.java)
        val url = "https://rentitapp123.000webhostapp.com/includes/rentas_inc.php?action=update"
        val queue = Volley.newRequestQueue(this)
        var resultadoPost = object : StringRequest(Request.Method.POST, url,
            Response.Listener<String> { response ->

                val json = JSONObject(response)
                if (json.getString("error") == "true") {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT)
                        .show()
                } else {

                    startActivity(intent)

                }
            }, Response.ErrorListener { error ->
                Toast.makeText(this, "Error $error ", Toast.LENGTH_LONG).show()
            }) {
            override fun getParams(): MutableMap<String, String>? {
                val parametros = HashMap<String, String>()

                parametros.put("rentaId", rentaId.toString())
                parametros.put("titulo",txtTituloEditPost.text.toString())
                parametros.put("calificacion", "5")
                parametros.put("precio",txtPrecioEditPost.text.toString())
                parametros.put("numCuartos",txtCuartosEditPost.text.toString())
                parametros.put("numBanios",txtBaniosEditPost.text.toString())
                parametros.put("ciudad", txtCiudadEditPost.text.toString())
                parametros.put("colonia", txtColoniaEditPost.text.toString())
                parametros.put("calle", txtCalleEditPost.text.toString())
                parametros.put("numero", txtNumeroEditPost.text.toString())
                parametros.put("isActive", "0")


                return parametros

            }
        }


            queue.add(resultadoPost)



    }

    fun clickBtnProfile(view: View){
        var intent = Intent(this, UserProfileActivity::class.java)
        startActivity(intent)
    }

    fun clickBtnHome(view: View){
        var intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    fun clickBtnLogout(view: View){
        var intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}