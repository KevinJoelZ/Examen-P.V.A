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
        binding.btnAgregar.setOnClickListener { crearProducto() }
    }

    private fun initRecycler() {
        adapter = ProductoAdapter(productos)
        binding.rvProductos.layoutManager = LinearLayoutManager(this)
        binding.rvProductos.adapter = adapter
    }

    private fun crearProducto() {
        if (!validarCampos()) return
        val producto = Producto(
            id = idCounter++,
            nombre = binding.etNombre.text.toString().trim(),
            precio = binding.etPrecio.text.toString().trim().toDouble(),
            descripcion = binding.etDescripcion.text.toString().trim()
        )
        productos.add(producto)
        agregarProductoALista()
        limpiarCampos()
    }

    private fun validarCampos(): Boolean {
        val nombre = binding.etNombre.text.toString().trim()
        val precioStr = binding.etPrecio.text.toString().trim()
        val descripcion = binding.etDescripcion.text.toString().trim()
        if (nombre.isEmpty() || precioStr.isEmpty() || descripcion.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
            return false
        }
        val precio = precioStr.toDoubleOrNull()
        if (precio == null || precio <= 0) {
            Toast.makeText(this, "Precio inválido", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun agregarProductoALista() {
        adapter.notifyItemInserted(productos.size - 1)
        binding.rvProductos.scrollToPosition(productos.size - 1)
    }

    private fun limpiarCampos() {
        binding.etNombre.text.clear()
        binding.etPrecio.text.clear()
        binding.etDescripcion.text.clear()
    }
}