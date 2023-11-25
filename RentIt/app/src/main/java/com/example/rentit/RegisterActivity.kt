package com.example.rentit

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Switch
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {

    var txtNameReg:EditText? = null
    var txtLastNameReg:EditText? = null
    var txtUsernameReg:EditText? = null
    var txtEmailReg:EditText? = null
    var txtPasswordReg:EditText? = null
    lateinit var btnRegister: Button
    lateinit var imgViewReg: ImageView
    lateinit var switchTypeReg: Switch
    val passwordREGEX = Pattern.compile("^" +
            "(?=.*[0-9])" +         //at least 1 digit
            "(?=.*[a-z])" +         //at least 1 lower case letter
            "(?=.*[A-Z])" +         //at least 1 upper case letter
            "(?=.*[a-zA-Z])" +      //any letter
            "(?=\\S+$)" +           //no white spaces
            ".{8,}" +               //at least 8 characters
            "$");
    lateinit var base64string: String
    val url: String = "https://rentitapp123.000webhostapp.com/includes/register_inc.php?action=create"
    var imageSelected: Boolean = false
    var userType: String = "1"

    companion object {
        val IMAGE_REQUEST_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        txtNameReg = findViewById(R.id.txtNameReg)
        txtLastNameReg = findViewById(R.id.txtLastNameReg)
        txtUsernameReg = findViewById(R.id.txtUsernameReg)
        txtEmailReg = findViewById(R.id.txtEmailReg)
        txtPasswordReg = findViewById(R.id.txtPasswordReg)
        btnRegister = findViewById(R.id.btnRegister)
        imgViewReg = findViewById(R.id.imgViewReg)
        switchTypeReg = findViewById(R.id.switchTypeReg)


        switchTypeReg.setOnCheckedChangeListener { _, isChecked -> //CHECAR QUE TIPO DE USUARIO ES
            if (isChecked) {
                userType = "2"
            } else {
                userType = "1"
            }
        }

    }

    fun clickBtnRegister(view: View){ //CLICK BOTON REGISTRAR


        val queue = Volley.newRequestQueue(this)
        var resultadoPost = object : StringRequest(Request.Method.POST, url,
            Response.Listener<String> { response ->

                val json = JSONObject(response)
                if(json.getString("error") == "true") {
                    Toast.makeText(this, "El correo ya ha sido registrado", Toast.LENGTH_LONG).show()
                }else{
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }


            }, Response.ErrorListener { error ->
                Toast.makeText(this,"Error $error ", Toast.LENGTH_LONG).show()
            }){
            override fun getParams(): MutableMap<String, String>? {
                val parametros=HashMap<String,String>()
                parametros.put("name",txtNameReg?.text.toString())
                parametros.put("lastName",txtLastNameReg?.text.toString())
                parametros.put("username",txtUsernameReg?.text.toString())
                parametros.put("email",txtEmailReg?.text.toString())
                parametros.put("password",txtPasswordReg?.text.toString())
                parametros.put("userType", userType)
                parametros.put("img", base64string)

                return parametros

            }
        }

        if(isValidEmail(txtEmailReg?.text.toString()) && isValidPassword(txtPasswordReg?.text.toString()) && imageSelected && txtNameReg?.text.toString().isNotEmpty()
            && txtLastNameReg?.text.toString().isNotEmpty() && txtUsernameReg?.text.toString().isNotEmpty()
            && txtEmailReg?.text.toString().isNotEmpty() && txtPasswordReg?.text.toString().isNotEmpty()){
            val bitmap = convertImageToBitmap()
            base64string = convertBitmapToBase64(bitmap)


            queue.add(resultadoPost)

        }else if(txtNameReg?.text.toString().isEmpty() || txtLastNameReg?.text.toString().isEmpty() || txtUsernameReg?.text.toString().isEmpty()
            || txtEmailReg?.text.toString().isEmpty() || txtPasswordReg?.text.toString().isEmpty()){
            Toast.makeText(this,"Favor de llenar todos los campos", Toast.LENGTH_SHORT).show()
        }else if(!isValidEmail(txtEmailReg?.text.toString())) {
            Toast.makeText(this,"Email no valido", Toast.LENGTH_SHORT).show()
        }else if(!imageSelected){
            Toast.makeText(this,"Seleccione imagen", Toast.LENGTH_SHORT).show()
        }else if(!isValidPassword(txtPasswordReg?.text.toString())){
            Toast.makeText(this,"Formato de contraseña incorrecto", Toast.LENGTH_SHORT).show()
        }

    }

    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPassword(password: String): Boolean {
        return passwordREGEX.matcher(password).matches()

    }


    fun clickLogin(view: View){ //CLICK DE TEXTO PARA IR A PANTALLA LOGIN
        var intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    fun convertImageToBitmap(): Bitmap {
        val drawable = imgViewReg.drawable as BitmapDrawable
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
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK){
            imageSelected = true
            imgViewReg.setImageURI(data?.data)


        }
    }

}