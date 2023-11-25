package com.example.rentit

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PostAdapter(private val postList: ArrayList<Post>): RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{

        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){

        mListener = listener
    }

    class ViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView){
        val img = itemView.findViewById<ImageView>(R.id.imgViewPostItem)
        val titulo =  itemView.findViewById<TextView>(R.id.txtTituloPostItem)
        val ciudad =  itemView.findViewById<TextView>(R.id.txtCiudadPostItem)
        val precio =  itemView.findViewById<TextView>(R.id.txtPrecioPostItem)

        init {
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_items, parent, false)
        return ViewHolder(view, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = postList[position]

        val decodedString: ByteArray = Base64.decode(post.img, Base64.DEFAULT)
        val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        holder.img.setImageBitmap(decodedByte)
        holder.titulo.text = post.titulo
        holder.ciudad.text = "Ubicacion: " + post.ciudad
        holder.precio.text = "Precio al mes: $" + post.precio
    }

    override fun getItemCount(): Int {
        return postList.size
    }

}