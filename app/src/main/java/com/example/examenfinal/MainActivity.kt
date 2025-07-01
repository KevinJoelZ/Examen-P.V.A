package com.example.examenfinal

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.examenfinal.databinding.ActivityMainBinding
// Clase
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val productos = mutableListOf<Producto>()
    private lateinit var adapter: ProductoAdapter
    private var idCounter = 1
    private var editando = false
    private var indiceEditando = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecycler()
        binding.btnAgregar.setOnClickListener { crearProducto() }
    }
// funciones privadas y sus metodos
    private fun initRecycler() {
        adapter = ProductoAdapter(
            productos,
            onEditar = { producto, index -> cargarProductoParaEditar(producto, index) },
            onEliminar = { index -> eliminarProducto(index) }
        )
        binding.rvProductos.layoutManager = LinearLayoutManager(this)
        binding.rvProductos.adapter = adapter
    }
// Agregar producto
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
// Editar producto
    private fun cargarProductoParaEditar(producto: Producto, index: Int) {
        binding.etNombre.setText(producto.nombre)
        binding.etPrecio.setText(producto.precio.toString())
        binding.etDescripcion.setText(producto.descripcion)
        binding.btnAgregar.text = "Actualizar"
        editando = true
        indiceEditando = index
        binding.btnAgregar.setOnClickListener { actualizarProducto() }
    }
// Actualizar producto
    private fun actualizarProducto() {
        if (!validarCampos()) return
        val productoActualizado = Producto(
            id = productos[indiceEditando].id,
            nombre = binding.etNombre.text.toString().trim(),
            precio = binding.etPrecio.text.toString().trim().toDouble(),
            descripcion = binding.etDescripcion.text.toString().trim()
        )
        productos[indiceEditando] = productoActualizado
        adapter.notifyItemChanged(indiceEditando)
        limpiarCampos()
        binding.btnAgregar.text = "Agregar Producto"
        editando = false
        indiceEditando = -1
        binding.btnAgregar.setOnClickListener { crearProducto() }
    }
// Eliminar producto
    private fun eliminarProducto(index: Int) {
        productos.removeAt(index)
        adapter.notifyItemRemoved(index)
        if (editando && indiceEditando == index) {
            limpiarCampos()
            binding.btnAgregar.text = "Eliminar Producto"
            editando = false
            indiceEditando = -1
            binding.btnAgregar.setOnClickListener { crearProducto() }
        }
    }
// Validar campos
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
// Agregar producto a la lista
    private fun agregarProductoALista() {
        adapter.notifyItemInserted(productos.size - 1)
        binding.rvProductos.scrollToPosition(productos.size - 1)
    }
// Limpiar campos
    private fun limpiarCampos() {
        binding.etNombre.text.clear()
        binding.etPrecio.text.clear()
        binding.etDescripcion.text.clear()
    }
}