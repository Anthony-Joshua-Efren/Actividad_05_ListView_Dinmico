package com.example.actividad_05_listview_dinmico

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.ListView
import android.widget.SearchView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var platilloAdapter: PlatilloAdapter
    private lateinit var platillos: List<Platillo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val listView: ListView = findViewById(R.id.listView)
        val searchView: SearchView = findViewById(R.id.searchView)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Comidas Tradicionales"

        platillos = listOf(
            Platillo("Tacos", "México", "Tortillas de maíz rellenas de carne, acompañadas de cilantro, cebolla, salsas y otros ingredientes al gusto.\n" +
                    "\n", R.drawable.tacos),
            Platillo("Sushi", "Japón", "Pequeños bocados de arroz avinagrado acompañados de pescado crudo, mariscos y vegetales.", R.drawable.sushi),
            Platillo("Paella ", "España", "Plato de arroz cocido con mariscos, pollo y verduras, sazonado con azafrán y otros condimentos.\n" +
                    "\n", R.drawable.paella),
            Platillo("Ceviche", "Perú", "Pescado crudo marinado en jugo de limón, mezclado con cebolla, cilantro, ají y otros ingredientes frescos.\n" +
                    "\n", R.drawable.ceviche),
            Platillo("Pizza", "Italia", "Masa horneada con una base de salsa de tomate y queso, cubierta con una variedad de ingredientes como peperoni, champiñones o vegetales.\n" +
                    "\n", R.drawable.pizza),
            Platillo("Poutine", "Canadá", "Papas fritas cubiertas con queso en grano y salsa de carne, una combinación sabrosa y muy popular en Canadá.\n" +
                    "\n", R.drawable.poutine),
            Platillo("Borscht", "Ucrania", "Sopa a base de remolacha, que le da su color rojo característico, a menudo acompañada de crema agria.\n" +
                    "\n", R.drawable.borscht),
            Platillo("Feijoada", "Brasil", "Estofado de frijoles negros con carne de cerdo y ternera, tradicionalmente servido con arroz y naranjas.\n" +
                    "\n", R.drawable.feijoada),
            Platillo("Bratwurst", "Alemania", "Salchicha alemana de cerdo, ternera o ternera, asada o frita, comúnmente servida en un panecillo con mostaza.\n" +
                    "\n", R.drawable.bratwurst),
            Platillo("Biryani", "India", "Plato de arroz basmati cocido con especias, carnes (pollo, cordero, res) y a veces huevo o vegetales.\n" +
                    "\n", R.drawable.biryani),
        )

        platilloAdapter = PlatilloAdapter(this, platillos)
        listView.adapter = platilloAdapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredPlatillos = platillos.filter {
                    it.nombre.contains(newText ?: "", ignoreCase = true)
                }
                platilloAdapter.clear()
                platilloAdapter.addAll(filteredPlatillos)
                platilloAdapter.notifyDataSetChanged()
                return true
            }
        })

        listView.setOnItemClickListener { _, _, position, _ ->
            val platilloSeleccionado = platilloAdapter.getItem(position)
            platilloSeleccionado?.let { mostrarDialogo(it) }
        }
    }

    private fun mostrarDialogo(platillo: Platillo) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_platillo, null)

        val imagenPlatillo = dialogView.findViewById<ImageView>(R.id.imagenPlatilloDialog)
        val descripcionPlatillo = dialogView.findViewById<TextView>(R.id.descripcionPlatilloDialog)

        imagenPlatillo.setImageResource(platillo.imagenResId)
        descripcionPlatillo.text = platillo.descripcion

        val dialog = AlertDialog.Builder(this)
            .setTitle(platillo.nombre)
            .setView(dialogView)
            .setPositiveButton("Compartir") { _, _ ->
                compartirPlatillo(platillo)
            }
            .setNegativeButton("Cerrar", null)
            .create()

        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.show()
    }

    private fun compartirPlatillo(platillo: Platillo) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "${platillo.nombre} de ${platillo.pais}: ${platillo.descripcion}")
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

}