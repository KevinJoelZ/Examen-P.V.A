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

        adapter = ProductoAdapter(productos)
        binding.rvProductos.layoutManager = LinearLayoutManager(this)
        binding.rvProductos.adapter = adapter

        binding.btnAgregar.setOnClickListener {
            val nombre = binding.etNombre.text.toString().trim()
            val precioStr = binding.etPrecio.text.toString().trim()
            val descripcion = binding.etDescripcion.text.toString().trim()

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
        }
    }
}