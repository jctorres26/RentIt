package com.example.rentit

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
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
import java.io.ByteArrayOutputStream

class RegisterPostActivity : AppCompatActivity() {

    lateinit var imgViewRegPost: ImageView
    lateinit var txtTituloRegPost: EditText
    lateinit var txtPrecioRegPost: EditText
    lateinit var txtCuartosRegPost: EditText
    lateinit var txtBaniosRegPost: EditText
    lateinit var txtCiudadRegPost: TextView
    lateinit var txtColoniaRegPost: TextView
    lateinit var txtCalleRegPost: TextView
    lateinit var txtNumeroRegPost: EditText
    lateinit var btnPublicarRegPost: Button

    companion object {
        val IMAGE_REQUEST_CODE = 100
    }

    lateinit var base64string: String
    var imageSelected: Boolean = false

    lateinit var sharedPref: SharedPreferences

    val url: String = "https://rentitapp123.000webhostapp.com/includes/rentas_inc.php?action=create"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_post)

        sharedPref = getSharedPreferences("myPref", MODE_PRIVATE)

        imgViewRegPost = findViewById(R.id.imgViewRegPost)
        txtTituloRegPost = findViewById(R.id.txtTituloRegPost)
        txtPrecioRegPost = findViewById(R.id.txtPrecioRegPost)
        txtCuartosRegPost = findViewById(R.id.txtCuartosRegPost)
        txtBaniosRegPost = findViewById(R.id.txtBaniosRegPost)
        txtCiudadRegPost = findViewById(R.id.txtCiudadRegPost)
        txtColoniaRegPost = findViewById(R.id.txtColoniaRegPost)
        txtCalleRegPost = findViewById(R.id.txtCalleRegPost)
        txtNumeroRegPost = findViewById(R.id.txtNumeroRegPost)
        btnPublicarRegPost = findViewById(R.id.btnPublicarRegPost)


    }



    fun clickBtnPublicar(view: View){


        val queue = Volley.newRequestQueue(this)
        var resultadoPost = object : StringRequest(Request.Method.POST, url,
            Response.Listener<String> { response ->

                val json = JSONObject(response)
                if(json.getString("error") == "true") {
                    Toast.makeText(this, "El correo ya ha sido registrado", Toast.LENGTH_LONG).show()
                }else{
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                }


            }, Response.ErrorListener { error ->
                Toast.makeText(this,"Error $error ", Toast.LENGTH_LONG).show()
            }){
            override fun getParams(): MutableMap<String, String>? {
                val parametros=HashMap<String,String>()
                parametros.put("titulo",txtTituloRegPost.text.toString())
                parametros.put("calificacion", "5")
                parametros.put("precio",txtPrecioRegPost.text.toString())
                parametros.put("numCuartos",txtCuartosRegPost.text.toString())
                parametros.put("numBanios",txtBaniosRegPost.text.toString())
                parametros.put("ciudad", txtCiudadRegPost.text.toString())
                parametros.put("colonia", txtColoniaRegPost.text.toString())
                parametros.put("calle", txtCalleRegPost.text.toString())
                parametros.put("numero", txtNumeroRegPost.text.toString())
                parametros.put("img", base64string)
                parametros.put("isActive", "1")
                parametros.put("userId", sharedPref.getInt("userId", 0).toString() )

                return parametros

            }
        }



        if( imageSelected && txtTituloRegPost?.text.toString().isNotEmpty()
            && txtPrecioRegPost?.text.toString().isNotEmpty() && txtCuartosRegPost?.text.toString().isNotEmpty()
            && txtBaniosRegPost?.text.toString().isNotEmpty() && txtCiudadRegPost?.text.toString().isNotEmpty()
            && txtColoniaRegPost?.text.toString().isNotEmpty() && txtCalleRegPost?.text.toString().isNotEmpty()
            && txtNumeroRegPost?.text.toString().isNotEmpty()){

            val bitmap = convertImageToBitmap()
            base64string = convertBitmapToBase64(bitmap)
            queue.add(resultadoPost)

        }else if(!imageSelected || txtTituloRegPost?.text.toString().isEmpty() || txtPrecioRegPost?.text.toString().isEmpty() || txtCuartosRegPost?.text.toString().isEmpty()
            || txtBaniosRegPost?.text.toString().isEmpty() || txtCiudadRegPost?.text.toString().isEmpty() || txtColoniaRegPost?.text.toString().isEmpty()
            || txtCalleRegPost?.text.toString().isEmpty() || txtNumeroRegPost?.text.toString().isEmpty()){

            Toast.makeText(this,"Favor de llenar todos los campos", Toast.LENGTH_SHORT).show()
        }
    }

    fun convertImageToBitmap(): Bitmap {
        val drawable = imgViewRegPost.drawable as BitmapDrawable
        return drawable.bitmap
    }

    fun convertBitmapToBase64(bitmap: Bitmap) :String{
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,stream)
        val image = stream.toByteArray()
        return Base64.encodeToString(image, Base64.DEFAULT)
    }

    fun pickImageGallery(view: View){ //CLICK DE IMAGEN PARA ABRIR GALERIA
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, RegisterActivity.IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RegisterActivity.IMAGE_REQUEST_CODE && resultCode == RESULT_OK){
            imageSelected = true
            imgViewRegPost.setImageURI(data?.data)


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

    fun clickBtnLogout(view: View){
        var intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

}