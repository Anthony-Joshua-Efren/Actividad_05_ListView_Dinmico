package com.example.actividad_05_listview_dinmico

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class PlatilloAdapter(private val context: Context, private val platillos: List<Platillo>) : ArrayAdapter<Platillo>(context, 0, platillos) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_platillo, parent, false)

        val platillo = getItem(position)

        val nombreTextView = view.findViewById<TextView>(R.id.nombrePlatillo)
        val paisTextView = view.findViewById<TextView>(R.id.paisPlatillo)
        val imagenImageView = view.findViewById<ImageView>(R.id.imagenPlatillo)

        nombreTextView.text = platillo?.nombre
        paisTextView.text = platillo?.pais
        imagenImageView.setImageResource(platillo?.imagenResId ?: R.drawable.default_food_icon)

        return view
    }
}
