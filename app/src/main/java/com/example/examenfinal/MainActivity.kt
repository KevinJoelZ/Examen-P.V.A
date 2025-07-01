package com.example.examenfinal

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.examenfinal.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val productos = mutableListOf<Producto>()
    private lateinit var adapter: ProductoAdapter
    private var idCounter = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecycler()
        binding.btnAgregar.setOnClickListener {
           agregarProducto()
        }
    }
    private fun initRecycler() {
        adapter = ProductoAdapter(productos) { productoAEliminar ->
            eliminarProducto(productoAEliminar)
        }
        binding.rvProductos.layoutManager = LinearLayoutManager(this)
        binding.rvProductos.adapter = adapter
    }

    private fun agregarProducto(){
        /*
        val nombre = binding.etNombre.text.toString().trim()
        val precioStr = binding.etPrecio.text.toString().trim()
        val descripcion = binding.etDescripcion.text.toString().trim()

        //crear función para validar producto

        // Validación básica
        if (nombre.isEmpty() || precioStr.isEmpty() || descripcion.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
            return@setOnClickListener
        }

        val precio = precioStr.toDoubleOrNull()
        if (precio == null || precio <= 0) {
            Toast.makeText(this, "Precio inválido", Toast.LENGTH_SHORT).show()
            return@setOnClickListener
        }

        val producto = Producto(idCounter++, nombre, precio, descripcion)
        productos.add(producto)
        adapter.notifyItemInserted(productos.size - 1)

        // Limpiar campos
        binding.etNombre.text.clear()
        binding.etPrecio.text.clear()
        binding.etDescripcion.text.clear()
        */
        private fun eliminarProducto(producto: Producto) {
            val index = productos.indexOf(producto)
            if (index != -1) {
                productos.removeAt(index)
                adapter.notifyItemRemoved(index)
                Toast.makeText(this, "Producto eliminado", Toast.LENGTH_SHORT).show()

    }
}