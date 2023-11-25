package com.example.rentit

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class PostDetailActivity : AppCompatActivity() {

    lateinit var txtNamePostDet: TextView
    lateinit var txtTituloPostDet: TextView
    lateinit var txtPrecioPostDet: TextView
    lateinit var txtCuartosPostDet: TextView
    lateinit var txtCiudadPostDet: TextView
    lateinit var txtCallePostDet: TextView
    lateinit var imgViewPostDet : ImageView

    val url = "https://rentitapp123.000webhostapp.com/includes/rentas_inc.php?action=getDetailsByRentaId"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_detail)

        val rentaId:String? = intent.getStringExtra("rentaId").toString()

        txtNamePostDet = findViewById(R.id.txtNamePostDet)
        txtTituloPostDet = findViewById(R.id.txtTituloPostDet)
        txtPrecioPostDet = findViewById(R.id.txtPrecioPostDet)
        txtCuartosPostDet = findViewById(R.id.txtCuartosPostDet)
        txtCiudadPostDet = findViewById(R.id.txtCiudadPostDet)
        txtCallePostDet = findViewById(R.id.txtCallePostDet)
        imgViewPostDet = findViewById(R.id.imgViewPostDet)

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
                    imgViewPostDet.setImageBitmap(decodedByte)
                    txtNamePostDet.setText("Publicado por " + json.getString("name") + " " + json.getString("lastname") )
                    txtTituloPostDet.setText(json.getString("titulo"))
                    txtPrecioPostDet.setText( "$" + json.getString("precio") + " al mes")
                    txtCuartosPostDet.setText(json.getString("numCuartos") + " cuarto(s) y " + json.getString("numBanios") + " baño(s)" )
                    txtCiudadPostDet.setText("Ubicado en " + json.getString("ciudad") + ", Colonia " + json.getString("colonia") )
                    txtCallePostDet.setText("Calle " + json.getString("calle") + " " + json.getString("numero") )

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



    fun clickBtnRentar(view: View){
        var intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
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